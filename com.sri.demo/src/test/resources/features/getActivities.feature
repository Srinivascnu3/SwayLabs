Feature: Check for Activities Details
  Scenario: Get Activities details
    Given A list of Activities are available
    When Get the Activities details of "<id>" and "<title>"
    Then Validate status code  and status line 
    
Examples:
|id|title|
|2|Activity 2|
|3|Activity 3|