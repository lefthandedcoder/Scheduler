Title: Scheduler Application 1.0
Purpose: 
This application allows users to create, update, and delete appointments and customers with data stored and retrieved from a MySQL database.
The application converts database UTC times to system default times and checks new appointment start and end times against EST business hours.
The application also displays reports based on appointment type per month, appointments for each contact, and appointments for each customer.

Author:
Christian Dye
cdye33@wgu.edu
Version: 1.0

IDE:
Netbeans 11.1

JDK Version: Java SE 11.0.11

JavaFX Version: JavaFX-SDK-11.0.2

Directions:
A. Login:
	Enter correct username and password in the login window and click login.
	Note that language functionality for the login window is available for English, French, or Chinese.
	Login attempts will be saved to login_activity.txt.
B. Customers:
	1. To view a list of customers, click on the Customers button in the navigation bar on the left.
	2. To add a new customer, click on the Add button on the bottom right.
	3. To add a new customer, all fields are required and country must be selected before region.
	4. Click Save to save the new customer. The program will return to the Customer main menu.
	5. To update a customer, first select a customer on the Customers main menu and then click Update.
	6. After updating customer information, click Save.
	7. To delete a customer, appointments associated with the customer must be deleted first. 
	To do this, delete associated appointments in the Appointment main menu. Then return to the Customers main menu, select the customer, and click Delete.

C. Appointments:
	*** If the current user has appointments within the next 15 minutes, a pop-up alert will display after the login. 
	To view these appointments, click on the Upcoming Appointments button on the Scheduler main menu or go to the Appointments main menu and select the Upcoming Appointments radio button. ***
	
	1. To view a list of appointments, click on the Appointments button in the navigation bar on the left.
	2. To add a new appointment, click on the Add button on the bottom right.
	3. To add a new appointment, all fields are required, start and end times must be during business hours, end times must be after start times, and the selected customer must not have overlapping appointments.
	4. Click Save to save the new appointment. The program will return to the Appointments main menu.
	5. To update an appointment, first select an appointment on the Appointments main menu and then click Update.
	6. After updating appointment information, click Save.
	7. To delete an appointment, select the appointment, and click Delete.
D. Reports:
	1. To view available reports, click on the Reports button in the navigation bar on the left.
	2. To view the types and number of each type of appointments in each month, click on the All Appointments tab.
	3. To view appointments for specific contacts, click on the Appointments by Contact tab. Select the contact from the combobox at the top of the tab.
	4. To view appointments for specific customers, click on the Appointments by Customer tab. Select the customer from the combobox at the top of the tab.
	*** The additional report, the Appointments by Customer, may help assist the user when deleting customers because it collects appointments to delete within a single report. ***
