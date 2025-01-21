Feature: Get Zip Codes

Scenario Outline: Verify List Of Zip Codes Reflect (Left Navigation Bar)
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
#Then Read and Verify List of Zip Code Name and Count
Then Read and Verify first Zip Code Name
Then Read and Verify second Zip Code Name

Examples:
|email|password|
|shawnee@t.digital.testemail.fulluat|Delhi@123|