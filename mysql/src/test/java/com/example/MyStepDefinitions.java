package com.example;

import dto.Person;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utility.DatabaseConnection;
import utility.DeserializerUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyStepDefinitions {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private ResultSet resultSet;

    @Given("I am connected to the database")
    public void connectToDatabase() {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }

    @When("I execute the query {string}")
    public void executeQuery(String query) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Then("get the data")
    public void getData() {
        List<Person> persons = new ArrayList<>();
        try {

            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Object value = resultSet.getObject(i);
                    System.out.println(columnName + ": " + value);
                    Person person = null;
                    person = DeserializerUtil.fromResultSet(resultSet, Person.class);
                    persons.add(person);
                }
                System.out.println("------------");
            }
            System.out.println(persons);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Then("I should get the following results:")
    public void verifyResults(List<String> expectedResults) {
        try {
            int rowNum = 0;
            while (resultSet.next()) {
                String actualResult = resultSet.getString(1);
                String expectedResult = expectedResults.get(rowNum);
                assertEquals(expectedResult, actualResult);
                rowNum++;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Given("I close the database connection")
    public void closeDatabaseConnection() {
        dbConnection.closeConnection();
    }
}
