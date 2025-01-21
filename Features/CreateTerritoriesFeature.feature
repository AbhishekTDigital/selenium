Feature: Create Territories

Background: Steps common for all scenarios
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"

Scenario Outline: Create Territory 
And User enters ATNT Email as "<Dealer_email>"
And User enters ATNT Password as "<Dealer_password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
#And click on User Icon
#And click on Logout button
#Comment

#Scenario: Create Territories Details
And click on Zip Codes
And select Zip Codes as "<zipCode>"
Then click on Select Zip Codes button
And user click on Polygon Icon
Then user draw a Polygon
And click on Assign button
And enter Territory Name as "<territoryName>"
And enter Completion Date as "<CompDate>"
And select Assign Agent Dropdown as "<AssignAgent>"
And click on save button
#Then user Verify Msg as "Territory Saved Successfully!"
And click on User Icon
And click on Logout button

# Login again with Different User to Verify
#And Click on Login
Then User Login again with Agent username
And User enters ATNT Email as "<Agent_email>"
And User enters ATNT Password as "<Agent_password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
When click on Assignment
Then select Territories List checkbox as "<territoryName>"
When click on List button
Then user Verify the Table row count

Examples:
|Dealer_email                       |Dealer_password|zipCode|territoryName |CompDate|AssignAgent   |Agent_email                    |Agent_password|
|shawnee@t.digital.testemail.fulluat|Delhi@123      |01068  |TerritoryDemo3|12-27-2025|Shawnee Agent9|shawnee@t.digital.trees.fulluat|Delhi@123     |