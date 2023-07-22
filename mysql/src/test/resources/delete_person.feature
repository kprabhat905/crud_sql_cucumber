#Feature: Delete Person by Name
#
#  Scenario: Delete a person by name
#    Given the database table "person" contains the following data
#      | name        | age |
#      | John Doe1   | 25  |
#      | Jane Smith1 | 30  |
#    When I delete the person with name "John Doe1"
#    Then the person with name "John Doe1" should be deleted from the database
