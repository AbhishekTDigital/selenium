Feature: Change Disposition

Scenario Outline: Change Lead Disposition on Map appear on Table 
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


When click on Assignment
Then select Territories List checkbox as "<Territory Name>"
When user Zoom In on Map
Then user click on Lead Coordinate as "<Index>"
And user get Audience Id
And user select Lead Disposition as "<Primary Lead Disposition>"
And user select Lead Disposition secondary as "<Secondary Lead Disposition>"
When click on List button
And user enter Audience Id in search Box as "<Audience Id>"
Then user Verify Lead Disposition Table Data Primary 
Then user Verify Lead Disposition Secondary

Examples:
|email                          |password |Territory Name   |Index|Primary Lead Disposition|Secondary Lead Disposition|Audience Id|
|shawnee@t.digital.trees.fulluat|Delhi@123|Filter4          |1    |Working                 |Decision Maker Not Home   |1378088756|
#|shawnee@t.digital.trees.fulluat|Delhi@123|Rahul Territory 4|2    |Working                 |Follow up Appointment     |1601939938 |
#|shawnee@t.digital.trees.fulluat|Delhi@123|Rahul Territory 4|3    |Working                 |No Answer                 |2677913824 |
#|TerritoryDemo2|2|Working|Follow up Appointment|655565868|
#|Dec24|1|Working|No Answer|493308772|
#|Dec24|2|Working|Decision Maker Not Home - Final|516179332|
#|Dec24|3|Working|No Answer - Final|536182938|

