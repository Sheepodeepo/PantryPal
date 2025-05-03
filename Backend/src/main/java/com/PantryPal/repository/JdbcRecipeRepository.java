package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Reference of Springboot JDBC Template vs Java DatasSource/SQL Prepared Statement queries:
// https://github.com/danvega/jdbc-blog : Shows Diff Java vs. Spring JDBC Template vs. Spring Data
// SQL Cheatsheet


//https://stackoverflow.com/questions/77722951/is-there-a-way-that-i-store-timestamp-with-time-zone-in-postgres-and-not-convert
//https://stackoverflow.com/questions/6627289/what-is-the-most-recommended-way-to-store-time-in-postgresql-using-java
@Repository
public class JdbcRecipeRepository implements RecipeRepository{

    private JdbcClient jdbcClient;

    public JdbcRecipeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // Alternatively: "INSERT INTO recipe(name, mealType, ingredients, instructions, createdDate) values(?,?,?,?,?)";
    // .params(List.of(recipe.getName(),recipe.getMealType(),recipe.getIngredients(),recipe.getInstructions(), LocalDate.now()))
    @Override
    public void save(Recipe recipe) throws SQLException {
        String query = "INSERT INTO recipe(name, mealType, ingredients, instructions, createdDate) values(:name, :mealType, :ingredients, :instructions, :createdDate) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(query)
                .param("name", recipe.getName())
                .param("mealType", recipe.getMealType())
                .param("ingredients", recipe.getIngredients())
                .param("instructions", recipe.getInstructions())
                .param("createdDate", LocalDate.now())
                .update(keyHolder);

        Number generatedId = keyHolder.getKey();
        if(generatedId != null){
            recipe.setId(generatedId.longValue());
        }
    }

    //Alternatively:  String query = "UPDATE recipe SET name = ?, mealType = ?, ingredients = ?, instructions = ?, createdDate = ?, updatedDate = ? WHERE id = ?";
    // .params(List.of(recipe.getName(),recipe.getMealType(),recipe.getIngredients(),recipe.getInstructions(),recipe.getCreatedDate(),LocalDate.now(),recipe.getId()))
    @Override
    public void update(Recipe recipe) {
        String query = "UPDATE recipe SET name = :name, mealType = :mealType, ingredients = :ingredients,"
        + "instructions = :instructions, createdDate = :createdDate, updatedDate = :updatedDate WHERE id = :id";
        jdbcClient.sql(query)
                .param("name", recipe.getName())
                .param("mealType", recipe.getMealType())
                .param("ingredients", recipe.getIngredients())
                .param("instructions", recipe.getInstructions())
                .param("createdDate", recipe.getCreatedDate())
                .param("updatedDate", LocalDate.now())
                .param("id", recipe.getId())
                .update();
    }

    // Alternatively have query = "DELETE FROM recipe WHERE id = :id" and .param("id", id), to specify the id to prevent any mishaps with order of parameters.
    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM recipe WHERE id = :id";
        jdbcClient.sql(query)
                .param("id", id)
                .update();
    }

    @Override
    public Optional<Recipe> findById(long id) {
        String query = "SELECT * FROM recipe WHERE id = :id";
        return jdbcClient.sql(query)
                .param("id", id)
                .query(Recipe.class)
                .optional();
    }

    @Override
    public List<Recipe> findAll() {
        String query = "SELECT * FROM recipe";
        return jdbcClient.sql(query)
                .query(Recipe.class)
                .list();
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM recipe";
        jdbcClient.sql(query)
                .update();
    }
}
