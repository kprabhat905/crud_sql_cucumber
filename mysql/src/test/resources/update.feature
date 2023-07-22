#Feature: Update Data in Table
#
#  Scenario Outline: Update Data in Table
#    Given the db table "person" contains the following data insert
#      | name       | age |
#      | John Doe   | 25  |
#      | Jane Smith | 30  |
#    When I update the age of "<name>" to <newAge>
#    Then the age of "<name>" should be updated to <newAge>
#
#    Examples:
#      | name       | newAge |
#      | John Doe   | 28     |
#      | Jane Smith | 35     |
