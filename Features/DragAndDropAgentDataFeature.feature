Feature: Table Data Re-arrange

Background: Steps common for all scenarios
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"

Scenario Outline: Drag and Drop Agent Table Row
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"

When click on Assignment
Then select Territories List checkbox as "<territoryName>"
When click on List button
Then click on Sort Icon
Then Drag a Row of Table and Drop
And click on dropdown arrow
And Store Table Data After Sorting
And click on User Icon
And click on Logout button
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
When click on Assignment
Then select Territories List checkbox as "<territoryName>"
When click on List button
And click on dropdown arrow
And Store Table Data After Re-Login
Then Validate the Sorted Table data after Re-Login


Examples:
|email                          |password |territoryName |
|shawnee@t.digital.trees.fulluat|Delhi@123|Filter4       |