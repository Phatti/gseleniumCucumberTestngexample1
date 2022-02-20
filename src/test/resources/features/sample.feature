Feature: Page Element validation on DemoQA

  Background: user is on demoQA Home Page
    Given user is on demoQA Home Page

  Scenario: Validate text boxes on demoQA
    Given user navigates to textbox page
    When user enters full name and email
    And user enters current address and permanent address
    When user clicks on submit button
    Then validate correct name is displayed

  Scenario: Validate radio button on demoQA
    Given user navigates to radio button page
    When user click on Yes radio
    Then validate yes radio is selected