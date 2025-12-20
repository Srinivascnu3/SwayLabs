Feature: Del for Activities Details
  Scenario: Del Activities details
    Given A list of Activities are available
    When Del the "22" Activities details 
    Then Validate status code  and status line 
    
