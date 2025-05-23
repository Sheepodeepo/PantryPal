package com.PantryPal.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private AppUserDetailService appUserDetailService;
    @Mock
    private HandlerExceptionResolver handlerExceptionResolver;
    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_JWT_TOKEN = "test.jwt.token";
    private UserDetails testUserDetails;

    @BeforeEach
    void setUp() {
        // Clear the SecurityContext before each test to ensure isolation
        SecurityContextHolder.clearContext();
        testUserDetails = User.withUsername(TEST_EMAIL)
                .password("dummy")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }

    @Test
    void doFilterInternal_whenNoJwtCookie_shouldClearContextAndContinueFilterChain() throws ServletException, IOException {
        //No Cookies received
        when(request.getCookies()).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that SecurityContext is empty
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        // Verify that no JWT processing methods were called
        verifyNoInteractions(jwtService);
        verifyNoInteractions(appUserDetailService);
        // Verify that the filter chain continued
        verify(filterChain).doFilter(request, response);
        // Verify no exception was resolved
        verifyNoInteractions(handlerExceptionResolver);
    }

    @Test
    void doFilterInternal_whenJwtCookiePresentButNoEmail_shouldClearContextAndContinueFilterChain() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT", TEST_JWT_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        //Simulate jwtService trying to retrieve email
        when(jwtService.extractEmail(TEST_JWT_TOKEN)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        //Verify security context clear and continue filterChain
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(jwtService).extractEmail(TEST_JWT_TOKEN);
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(appUserDetailService);
        verifyNoInteractions(handlerExceptionResolver);
    }

    @Test
    void doFilterInternal_whenValidJwtAndNotAuthenticated_shouldAuthenticateAndContinueFilterChain() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT", TEST_JWT_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtService.extractEmail(TEST_JWT_TOKEN)).thenReturn(TEST_EMAIL);
        when(appUserDetailService.loadUserByUsername(TEST_EMAIL)).thenReturn(testUserDetails);
        when(jwtService.isTokenValid(TEST_JWT_TOKEN,testUserDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        // Verify authentication was set
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(authentication.getName(),TEST_EMAIL);
        //Create into Hashset to check if the items in authorities are equal(b/c order doesn't matter)
        assertEquals(new HashSet<>(authentication.getAuthorities()), new HashSet<>(testUserDetails.getAuthorities()));
        assertTrue(authentication.isAuthenticated());

        verify(jwtService).extractEmail(TEST_JWT_TOKEN);
        verify(appUserDetailService).loadUserByUsername(TEST_EMAIL);
        verify(jwtService).isTokenValid(TEST_JWT_TOKEN,testUserDetails);
        verify(filterChain).doFilter(request,response);
    }
    @Test
    void doFilterInternal_whenValidJwtAndAlreadyAuthenticated_shouldNotReAuthenticate() throws ServletException, IOException {
        // Simulate a pre-existing authentication in the context
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alreadyAuthenticatedUser", null, Collections.emptyList()));

        Cookie jwtCookie = new Cookie("JWT", TEST_JWT_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtService.extractEmail(TEST_JWT_TOKEN)).thenReturn(TEST_EMAIL); // Still extracts email but shouldn't proceed

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the original authentication is still there
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("alreadyAuthenticatedUser");
        // Verify that appUserDetailService and isTokenValid were NOT called
        verifyNoInteractions(appUserDetailService);
        verify(jwtService, never()).isTokenValid(any(), any());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(handlerExceptionResolver);
    }

    @Test
    void doFilterInternal_whenInvalidJwt_ShouldThrowException() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT", TEST_JWT_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtService.extractEmail(TEST_JWT_TOKEN)).thenThrow(new RuntimeException("Invalid JWT Signature"));

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
//        verify(handlerExceptionResolver).resolveException(eq(request),eq(response),isNull(), any(SignatureException.class));
        verify(handlerExceptionResolver).resolveException(eq(request), eq(response), isNull(), any(RuntimeException.class));
        verify(filterChain).doFilter(request,response);
    }

    @Test
    void doFilterInternal_whenExpiredJwt_ShouldNotAuthenticate() throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("JWT", TEST_JWT_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtService.extractEmail(TEST_JWT_TOKEN)).thenReturn(TEST_EMAIL);
        when(appUserDetailService.loadUserByUsername(TEST_EMAIL)).thenReturn(testUserDetails);
        when(jwtService.isTokenValid(TEST_JWT_TOKEN,testUserDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request,response,filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(jwtService).extractEmail(TEST_JWT_TOKEN);
        verify(appUserDetailService).loadUserByUsername(TEST_EMAIL);
        verify(jwtService).isTokenValid(TEST_JWT_TOKEN,testUserDetails);
        verify(filterChain).doFilter(request,response);
        verifyNoInteractions(handlerExceptionResolver);

    }
}