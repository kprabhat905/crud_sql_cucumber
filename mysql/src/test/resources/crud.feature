Feature: Select Data from Table

  Scenario Outline: Select Data from Table
    Given the database table "person" contains the following data
      | name       | age |
      | John Doe   | 25  |
      | Jane Smith | 30  |
    When I select data from table "person"
    Then I should get the following data
      | name       | age |
      | John Doe   | 25  |
      | Jane Smith | 30  |
    When I update the age of "<name>" to <newAge>
    Then the age of "<name>" should be updated to <newAge>
    When I delete the person with name "John Doe"
    Then the person with name "John Doe" should be deleted from the database
    Examples:
      | name       | newAge |
      | John Doe   | 28     |
      | Jane Smith | 35     |

