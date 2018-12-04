Feature: Accountant management

  Scenario: save accountant entry
    Given the client has a valid registry
    When the client attempt to save an accountant entry
    Then the system return a uuid of the accountant entry
    And the system return status 201

  Scenario: save an invalid accountant entry
    Given the client has a invalid registry
    When the client attempt to save an accountant entry
    Then the system return status 400