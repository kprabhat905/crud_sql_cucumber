package com.example;

import dto.Person;
import dto.PersonMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.DatabaseOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DeleteStepDefinations {
    private List<Person> persons;

//    @Given("the database table {string} contains the following data")
//    public void theDatabaseTableContainsFollowingData(String tableName, List<Person> persons) {
//        this.persons = persons;
//        // Assuming you have implemented the DatabaseOperations class with the insertData method
//        DataMapper<Person> personMapper = new PersonMapper();
//        DatabaseOperations.insertData(persons, tableName, personMapper);
//    }

    @When("I delete the person with name {string}")
    public void iDeleteThePersonWithName(String name) {
        DatabaseOperations.deleteData("person", "name", name);
    }

    @Then("the person with name {string} should be deleted from the database")
    public void thePersonWithNameShouldBeDeletedFromTheDatabase(String name) {
        // Assuming you have implemented the DatabaseOperations class with the selectPersonByName method
        Person deletedPerson = DatabaseOperations.selectPersonByName(name);
        Assert.assertNull("Person with name " + name + " should not be found in the database.", deletedPerson);
    }
}

