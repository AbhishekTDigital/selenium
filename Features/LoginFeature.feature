
Feature: ATT Login and Logout

#Scenario: Successful Login with valid Credentials and Logout
#Given User Launch Chrome browser
#When User opens URL "https://priority.projectalphabet.ai/"
#When User opens URL "https://manager.projectalphabet.ai/"
#And Click on Login
#And User enters Email as "monu@pcplusa.com"
#And Click on Next
#And User enters Password as "Welcome@5151"
#And Click on Next2
#Then Page Title should be "M&ANAGER"
#When User click on User Icon
#And User select Logout option
#And close browser

Background: Steps common for all scenarios
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"

Scenario Outline: Dealer Successful Login with valid Credentials and Logout DDT1
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
#And click on User Icon
#And click on Logout button
Then Verify Page Title as "AT&T"
Then Verify Page Logo
And click on User Icon
And click on Logout button
And close browser

Examples:
|email|password|
|adventure@att.com.dealer.fulluat|UTD@2024|
#|shawnee@t.digital.cydcorosm.fulluat|Scrumpine2024|
#|shawnee@t.digital.testdummy2.fulluat|Hello111|

