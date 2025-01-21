Feature: Change Disposition

Scenario Outline: Lead Disposition on Table appear on Map
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
#And click on User Icon
#And click on Logout button
#Comment

#Scenario: Lead Disposition on Table appear on Map
When click on Assignment
Then select Territories List checkbox as "<Territory Name>"
When click on List button
And user enter Audience Id in search Box "<Audience Id>"
When user select primary lead disposition as "<Primary Lead Disposition>"
When user select secondary lead disposition as "<Secondary Lead Disposition>"
When click on Map button
When user Zoom In on Map
Then user click on Lead Coordinate "<Index>"
Then User Verify the Primary Lead Disposition dropdown on Map
Then User Verify the Secondary Lead Disposition dropdown on Map

Examples:
|email|password|Territory Name|Index|Primary Lead Disposition|Secondary Lead Disposition|Audience Id|
|shawnee@t.digital.trees.fulluat|Delhi@123|Filter4 |3|Working|No Answer|1379674024|
#|shawnee@t.digital.trees.fulluat|Delhi@123|Rahul Territory 4|2|Working|Decision Maker Not Home|1601939938|
#|shawnee@t.digital.trees.fulluat|Delhi@123|Rahul Territory 4|3|Working|Follow up Appointment|2677913824|
