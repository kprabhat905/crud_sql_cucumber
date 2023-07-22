#Feature: Bulk Data Insertion
#
#  Scenario: Insert Data into Table
#    Given I have a list of persons
#    When I perform bulk data insertion into table "person"
#    Then data should be inserted successfully
#
#
#  Scenario Outline: Insert Data into Table Bulk
#    Given Insert persons with the name "<name>" and age <age>
#    When I perform bulk data insertion into table "person"
#    Then data should be inserted successfully
#    Examples:
#      | name        | age |
#      | John Doe1   | 25  |
#      | Jane Smith2 | 30  |
