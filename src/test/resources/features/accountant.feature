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

  Scenario: search accountant entry by id
    Given the system has at least one accountant entry registered
    And the client has a existing registry uuid
    When the client attempt to find the accountant entry
    Then the system return status 200
    And the system returns the expected accountant entry

  Scenario: search non-existing accountant entry
    Given the client has a not registered accountant uuid
    When the client attempt to find the accountant entry
    Then the system return status 204
    And the system returns empty body

  Scenario: search accountant entries by account number
    Given the system has at least one accountant entry registered
    And the client has a existing account number
    When the client attempt to find accountant entries by account number
    Then the system return status 200
    And the system returns the expected accountant entries

  Scenario: search for accountant entries stats
    Given the system has at least one accountant entry registered
    When the client attempt to get account stats
