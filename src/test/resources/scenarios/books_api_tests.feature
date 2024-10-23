Feature: Books API

  Scenario: Get all books
    When I request all books
    Then Books list should contain books
  
  Scenario: Create book
    When I create book [name="1984", author="Oruell", publication="BOQ", category="Fantastic", pages=300, price=15.5]
    Then I request all books
    And Books list should contain book with name "1984"
  
  Scenario: Update book
    Given I create book [name="1984", author="Oruell", publication="BOQ", category="Fantastic", pages=300, price=15.5]
    When I update last created book [name="Fix", author="None", publication="QW", category="No", pages=1, price=1.0]
    Then I request all books
    And Books list should contain book with name "Fix"

  Scenario: Delete book
    Given I create book [name="1985", author="Oruell", publication="BOQ", category="Fantastic", pages=300, price=15.5]
    When I delete last book
    Then I request all books
    And Books list should not contain book with name "1985"

  @bug
  Scenario: Delete not existing book
    When I delete book by id 123454321
    Then Status code should be 404