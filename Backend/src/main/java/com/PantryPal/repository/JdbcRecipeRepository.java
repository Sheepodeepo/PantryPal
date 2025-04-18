package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

// Reference of Springboot JDBC Template vs Java DatasSource/SQL Prepared Statement queries:
// https://github.com/danvega/jdbc-blog : Shows Diff Java vs. Spring JDBC Template vs. Spring Data


@Repository
public class JdbcRecipeRepository implements RecipeRepository{

    private JdbcClient jdbcClient;

    public JdbcRecipeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Recipe recipe) throws SQLException {
        String query = "INSERT INTO recipe(id, title, ) values(?,?,)";
        jdbcClient.sql(query)
                .params(List.of(recipe.getId(),recipe.getTitle(),))
                .update();
//                .query(Recipe.class)
//                .optional(); //Adds the Optinal attribute.
    }

    @Override
    public void update(Recipe recipe) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Recipe findById(long id) {
        return null;
    }

    @Override
    public List<Recipe> findAll() {
        return List.of();
    }

    @Override
    public void deleteAll() {

    }
}
