//package com.PantryPal.repository;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.simple.JdbcClient;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * https://docs.spring.io/spring-framework/reference/testing/support-jdbc.html
// * https://www.baeldung.com/spring-jdbctemplate-testing
// * https://docs.spring.io/spring-framework/reference/testing/support-jdbc.html
// *
// */
//class JdbcRecipeH2DBTest {
//
//    @Autowired
//    private JdbcClient jdbcClient;
//    private EmbeddedDatabase db;
//
//    @BeforeEach
//    void setUp(){
//        db = new EmbeddedDatabaseBuilder()
//                .generateUniqueName(true)
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//        jdbcClient = JdbcClient.create(db);
//    }
//
//    @AfterEach
//    void tearDown(){
//        db.shutdown();
//    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void deleteById() {
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void findAll() {
//        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcClient, "Recipe"));
//    }
//
//    @Test
//    void deleteAll() {
//    }
//}