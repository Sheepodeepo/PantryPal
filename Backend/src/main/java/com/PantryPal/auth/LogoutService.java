//package com.PantryPal.auth;
//
//import com.PantryPal.repository.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//
//public class LogoutService implements LogoutHandler {
//
//    private final UserRepository userRepository;
//
//    public LogoutService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//            final String authHeader = request.getHeader("Authorization");
//            final String jwt;
//            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//                return;
//            }
//            jwt = authHeader.substring(7);
//            var storedToken = tokenRepository.findByToken(jwt)
//                    .orElse(null);
//            if (storedToken != null) {
//                storedToken.setExpired(true);
//                storedToken.setRevoked(true);
//                tokenRepository.save(storedToken);
//                SecurityContextHolder.clearContext();
//            }
//    }
//}

// .logoutUrl("/logout")
//                        .logoutSuccessHandler((request, response, authentication) -> {
//
//        try {
////Creates new cookie and invalidates it and overwrites response Header/Cookies with this invalid Cookie to effectively remove JWT Token stored in a cookie
//ResponseCookie responseCookie = ResponseCookie.from("JWT")
//        .path("/")
//        .httpOnly(true)
//        .secure(true)
//        .maxAge(0)
//        .sameSite("Lax")
//        .build();
//                                response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
//        response.setStatus(HttpServletResponse.SC_OK);
//                                response.getWriter().write("Logout Successful");
//                            }catch(Exception error){
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                                response.getWriter().write("Logout Failed");
//                            }
//                                    })