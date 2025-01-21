
Feature: ADD Agent

Background: Steps common for all scenarios
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"

Scenario Outline: ADD New Agent
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
#And click on User Icon

#And click on Logout button
#Comment

Scenario: Add Agent Detail
And click on Add Agent button
And Enter First Name as "<Fname>"
And Enter Last Name as "<Lname>"
And Enter Phone Number as "<PhonNum>"
And Enter Email as "<EmailId>"
And click on Create button 
Then Verify Msg as "Data submitted for adding Agent"
And click on User Icon
And click on Logout button

Examples:
|email                           |password|Fname |Lname |PhonNum   |EmailId          |
|adventure@att.com.dealer.fulluat|UTD@2024|Monu23|Sharma|9876543211|saurabh13@t.digital|