Feature: Accountant management

  Scenario: save accountant entry
    Given the client has a valid registry
    When the client attempts to save an accountant entry
    Then the system return a uuid of the accountant entry
    And the system returns status 201

  Scenario: save an invalid accountant entry
    Given the client has a invalid registry
    When the client attempts to save an accountant entry
    Then the system returns status 400

  Scenario: search accountant entry by id
    Given the system has at least one accountant entry registered
    And the client has a existing registry uuid
    When the client attempts to find the accountant entry
    Then the system returns status 200
    And the system returns the expected accountant entry

  Scenario: search non-existing accountant entry
    Given the client has a not registered accountant uuid
    When the client attempts to find the accountant entry
    Then the system returns status 204
    And the system returns empty body

  Scenario: search accountant entries by account number
    Given the system has at least one accountant entry registered
    And the client has a existing account number
    When the client attempts to find accountant entries by account number
    Then the system returns status 200
    And the system returns the expected accountant entries

  Scenario: search for accountant entries stats
    Given the system has a list of accountant entry register
    When the client attempt to get account stats
    Then the system returns status 200
    And the system returns expected stats numbers

  Scenario: search for accountant entries stats per account number
    Given the client has a specific account number to search
    And the system has a list of accountant entry register with account number
    When the client attempt to get account stats by account number
    Then the system returns status 200
    And the system returns expected stats numbers
