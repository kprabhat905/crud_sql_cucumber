package com.example;

import com.example.DataMapper;
import dto.Person;
import dto.PersonMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utility.DatabaseOperations;

import java.util.List;
import java.util.Map;

public class UpdateStepDefinations {
    private List<Person> persons;

    @DataTableType
    public Person convertToPerson(Map<String, String> entry) {
        String name = entry.get("name");
        int age = Integer.parseInt(entry.get("age"));
        return new Person(name, age);
    }

    @Given("the db table {string} contains the following data insert")
    public void theDbTableContainsFollowingData(String tableName, List<Person> persons) {
        this.persons = persons;
        // Assuming you have implemented the DatabaseOperations class with the insertData method
        DataMapper<Person> personMapper = new PersonMapper();
        DatabaseOperations.insertData(persons, tableName, personMapper);
    }

    @When("I update the age of {string} to {int}")
    public void iUpdateTheAgeOfPersonTo(String name, int newAge) {
        // Assuming you have implemented the DatabaseOperations class with the updateAge method
        DatabaseOperations.updateAge(name, newAge);
    }

    @Then("the age of {string} should be updated to {int}")
    public void theAgeOfPersonShouldBeUpdatedTo(String name, int newAge) {
        // Assuming you have implemented the DatabaseOperations class with the selectPersonByName method
        Person updatedPerson = DatabaseOperations.selectPersonByName(name);
        Assert.assertNotNull(updatedPerson);
        Assert.assertEquals(newAge, updatedPerson.getAge());
    }
}
