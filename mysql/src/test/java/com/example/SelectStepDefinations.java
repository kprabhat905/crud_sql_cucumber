package com.example;

import dto.Person;
import dto.PersonMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.DatabaseConnection;
import utility.DatabaseOperations;

import java.util.*;

public class SelectStepDefinations {
    private List<Person> persons;
    private String tableName;
    private boolean insertionSuccessful;
    private List<Person> retrievedPersons;

    @Given("the database table {string} contains the following data")
    public void theDatabaseTableContainsFollowingDataInsert(String tableName, List<Person> persons) {
        this.tableName = tableName;
        insertionSuccessful = DatabaseOperations.insertData(persons, tableName, new PersonMapper());
    }

    @When("I select data from table {string}")
    public void iSelectDataFromTable(String tableName) {
        retrievedPersons = DatabaseOperations.selectData(tableName, Person.class);
    }

    @Then("I should get the following data")
    public void iShouldGetTheFollowingData(List<Person> expectedPersons) {
        // Compare the retrieved data with the expected data
        Assert.assertTrue(areAllPersonsPresent(expectedPersons, retrievedPersons));
    }

    public static boolean areAllPersonsPresent(List<Person> listA, List<Person> listB) {
        // all items of list A are present in ListB
        for (Person person : listA) {
            if (!listB.contains(person)) {
                return false;
            }
        }
        return true;
    }
}
