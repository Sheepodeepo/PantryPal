package com.PantryPal.repository;

import com.PantryPal.model.MealType;
import com.PantryPal.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.jdbc.JdbcTestUtils;
import reactor.core.publisher.Mono;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcRecipeRepositoryTest {


    @Mock
    private JdbcClient jdbcClient;

    @Mock
    private JdbcClient.StatementSpec statementSpec;

    @Mock
    private JdbcClient.ResultQuerySpec resultQuerySpec;

    @Mock
    private JdbcClient.MappedQuerySpec<Recipe> mappedQuerySpec;

    private JdbcRecipeRepository repository;
    private Recipe recipe;
    private final LocalDate tempLocalDate = LocalDate.of(2025,3,1);

    @BeforeEach
    void setUp(){
        repository = new JdbcRecipeRepository(jdbcClient);
        recipe = new Recipe(1L, "Test Recipe", MealType.DINNER, "Ingredient 1, Ingredient 2", "Step 1, Step 2");
    }

    @Test
    void save() throws SQLException {

        LocalDate tempDate = LocalDate.now();
        when(jdbcClient.sql(contains("INSERT INTO recipe"))).thenReturn(statementSpec);
        when(statementSpec.param(eq("name"), eq(recipe.getName()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("mealType"), eq(recipe.getMealType()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("ingredients"), eq(recipe.getIngredients()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("instructions"), eq(recipe.getInstructions()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("createdDate"), eq(tempDate))).thenReturn(statementSpec);
        when(statementSpec.update(any(GeneratedKeyHolder.class))).thenAnswer(invocation ->{
            GeneratedKeyHolder keyHolder = invocation.getArgument(0);
            keyHolder.getKeyList().add(Map.of("id",123L));
            return 1;
        });
//        when(statementSpec.param(eq("createdDate"), any(LocalDate.class))).thenReturn(statementSpec);

        // Act
        repository.save(recipe);

        // Capture the KeyHolder argument
        ArgumentCaptor<GeneratedKeyHolder> keyHolderCaptor = ArgumentCaptor.forClass(GeneratedKeyHolder.class);


        // Assert
        verify(jdbcClient).sql(contains("INSERT INTO recipe"));
        verify(statementSpec).param("name", "Test Recipe");
        verify(statementSpec).param("mealType", MealType.DINNER);
        verify(statementSpec).param("ingredients", "Ingredient 1, Ingredient 2");
        verify(statementSpec).param("instructions", "Step 1, Step 2");
        verify(statementSpec).param(eq("createdDate"), eq(tempDate));
        verify(statementSpec).update(keyHolderCaptor.capture()); // Verify update with KeyHolder

        // Assert that the KeyHolder has the generated ID
        GeneratedKeyHolder capturedKeyHolder = keyHolderCaptor.getValue();
        assertNotNull(capturedKeyHolder.getKey());
        assertEquals(123L, capturedKeyHolder.getKey().longValue());

        // Assert that the setId method of the recipe was called (if your save method returns the updated recipe)
        assertNotNull(recipe.getId());
        assertEquals(123L, recipe.getId());    }

    @Test
    void update() throws SQLException {
        LocalDate newCreatedDate = LocalDate.of(2025, 4, 1);
        LocalDate newUpdatedDate = LocalDate.now();

        // Update
        Recipe updatedRecipe = new Recipe(1L, "Updated Recipe Name", MealType.BREAKFAST, "New Ingredient", "New Steps", newCreatedDate);


        when(jdbcClient.sql(contains("UPDATE recipe SET name = :name, mealType = :mealType, ingredients = :ingredients,instructions = :instructions, createdDate = :createdDate, updatedDate = :updatedDate WHERE id = :id")))
                .thenReturn(statementSpec);
        when(statementSpec.param(eq("name"), eq(updatedRecipe.getName()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("mealType"), eq(updatedRecipe.getMealType()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("ingredients"), eq(updatedRecipe.getIngredients()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("instructions"), eq(updatedRecipe.getInstructions()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("createdDate"), any(LocalDate.class))).thenReturn(statementSpec);
        when(statementSpec.param(eq("updatedDate"), eq(newUpdatedDate))).thenReturn(statementSpec); // Use any() for LocalDate.now()
        when(statementSpec.param(eq("id"), eq(updatedRecipe.getId()))).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        repository.update(updatedRecipe);

        verify(jdbcClient).sql(contains("UPDATE recipe SET name = :name, mealType = :mealType, ingredients = :ingredients,"
                + "instructions = :instructions, createdDate = :createdDate, updatedDate = :updatedDate WHERE id = :id"));
        verify(statementSpec).param("name","Updated Recipe Name");
        verify(statementSpec).param("mealType", MealType.BREAKFAST);
        verify(statementSpec).param("ingredients", "New Ingredient");
        verify(statementSpec).param("instructions", "New Steps");
        verify(statementSpec).param(eq("createdDate"), any(LocalDate.class));
        verify(statementSpec).param(eq("updatedDate"), eq(newUpdatedDate));
        verify(statementSpec).param("id", 1L);
        verify(statementSpec).update();
    }

    @Test
    void deleteById() throws SQLException {

    }

    @Test
    void findById() throws SQLException{
        LocalDate tempDate = LocalDate.now();
        when(jdbcClient.sql(contains("INSERT INTO recipe"))).thenReturn(statementSpec);
        when(statementSpec.param(eq("name"), eq(recipe.getName()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("mealType"), eq(recipe.getMealType()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("ingredients"), eq(recipe.getIngredients()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("instructions"), eq(recipe.getInstructions()))).thenReturn(statementSpec);
        when(statementSpec.param(eq("createdDate"), eq(tempDate))).thenReturn(statementSpec);
        when(statementSpec.param(eq("id"), eq(recipe.getId()))).thenReturn(statementSpec);
//        when(statementSpec.param(eq("createdDate"), any(LocalDate.class))).thenReturn(statementSpec);

        // Act
        repository.save(recipe);

        // Assert/Verify
        verify(statementSpec).param(eq("name"), eq(recipe.getName()));
        verify(statementSpec).param(eq("mealType"), eq(recipe.getMealType()));
        verify(statementSpec).param(eq("ingredients"), eq(recipe.getIngredients()));
        verify(statementSpec).param(eq("instructions"), eq(recipe.getInstructions()));
        verify(statementSpec).param(eq("createdDate"), eq(tempDate));
        verify(statementSpec).param(eq("id"),eq(recipe.getId()));
    }

    @Test
    void findAll() {

    }

    @Test
    void deleteAll() {
    }

}