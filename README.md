# Inventory Software
 
 JavaDoc files are located at "Mdeloss\Inventory-Software\Final Project\javadoc"
 
FUTURE ENHANCEMENT
Main class: I would add a welcome message and an option to begin a search with parts or products.
AddPart and ModifyPart class: I would show In-House and Outsource text fields simultaneously,
	but disable whichever is not selected by the radio buttons.
AddProduct and ModifyProduct class: I would add a function that removes a selected part
	from the top table when adding it to the bottom table.
InHouse and OutSource class: I would add a maintenance date or expiration date.
Inventory class: I would add a automatic resupply method for parts that are repeatedly ordered.
Product class: I would a total price method to calculate the price of all associated parts.
Part class: I would add an attribute for used or new condition of the parts.

RUNTIME ERROR
	An error I occurred was not loading the MainScreen view when calling for a new scene. I fixed this by
adding "/view/" to the resource name. @see public void start()