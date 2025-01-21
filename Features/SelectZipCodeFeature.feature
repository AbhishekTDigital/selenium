Feature: Select Zip Code

Scenario Outline: Verify KML Load and Lead Render
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
Then Verify Page Logo
#And click on User Icon
#And click on Logout button
#Comment

#Scenario: List Of Zip Codes Reflect (Left Navigation Bar)
And click on Zip Codes
And select Zip Codes as "01010"
Then click on Select Zip Codes button
When user Zoom out on Map
Then Validate the Zip Code Boundary and Lead

Examples:
|email|password|
|shawnee@t.digital.testemail.fulluat|Delhi@123|