package com.example;

import dto.Person;
import dto.PersonMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.DatabaseConnection;
import utility.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class BulkInsertStepDefinitions {
    private List<Person> persons;
    private String tableName;
    private boolean insertionSuccessful;

    @Given("I have a list of persons")
    public void createPersonList() {
        persons = new ArrayList<>();
        persons.add(new Person("John Doe", 25));
        persons.add(new Person("Jane Smith", 30));
        persons.add(new Person("Tom Johnson", 35));
    }


    @Given("Insert persons with the name {string} and age {int}")
    public void insert_persons_with_the_name_and_age(String name, Integer age) {
        persons = new ArrayList<>();
        persons.add(new Person(name, age));
    }

    @When("I perform bulk data insertion into table {string}")
    public void performBulkInsertion(String tableName) {
        this.tableName = tableName;
        insertionSuccessful = DatabaseOperations.insertData(persons, tableName, new PersonMapper());
    }

    @Then("data should be inserted successfully")
    public void verifyInsertionSuccess() {
        Assert.assertEquals(insertionSuccessful, true);
    }

    @Then("print the list of inserted persons")
    public void printInsertedPersons() {
        if (insertionSuccessful) {
            System.out.println("Inserted Persons:");
            for (Person person : persons) {
                System.out.println(person);
            }
        } else {
            System.out.println("Data insertion failed.");
        }
    }
}
