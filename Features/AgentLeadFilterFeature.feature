Feature: Lead Filter

Background: Steps common for all scenarios
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"

Scenario Outline: Agent Lead Filter
#And User enters ATNT Email as "<Dealer_email>"
#And User enters ATNT Password as "<Dealer_password>"
#And Click on Login to sandbox
#And Click on Allow Access button
#Then Verify Page Title as "AT&T"


#And click on Zip Codes
#And select Zip Codes as "<zipCode>"
#Then click on Select Zip Codes button

#When user Zoom In on Dealer Map
#And user click on Polygon Icon
#Then user draw a Polygon on Dealer Map
#And click on Assign button
#And enter Territory Name as "<territoryName>"
#And enter Completion Date as "<CompDate>"
#And select Assign Agent Dropdown as "<AssignAgent>"
#And click on save button
#Then user Verify Msg as "Territory Saved Successfully!"
#And click on User Icon
#And click on Logout button

And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
Then Verify Page Title as "AT&T"
When click on Assignment
Then select Territories List checkbox as "<territoryName>"
And click on Filter Icon
Then select Lead Type dropdown as "<leadType>"
Then select Disposition dropdown as "<disposition>"
And click on Apply button
When user Zoom In on Agent Map
Then count Number of Leads on Map
When click on List button
Then Validate Number of Leads on List Table
And Convert the List Table In Card View
Then Validate Number of Leads for Card View


Examples:
|email                                          |password |territoryName |leadType|disposition|
|saurabh@t.digital.testingtesops.AdventureDealer|Delhi@123|Filter16Jan   |All     |All        | 

#Examples:
#|Dealer_email                       |Dealer_password|zipCode|territoryName |CompDate  |AssignAgent    |email                                          |password |territoryName |leadType|disposition|
#|adventure@att.com.dealer.fulluat   |UTD@2024       |76065  |Filter3       |12-31-2025|testops testing|saurabh@t.digital.testingtesops.AdventureDealer|Delhi@123|Filter3       |All     |All        | 