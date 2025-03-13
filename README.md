Foodics Test Automation Framework

Overview

This project is an automated test framework using Selenium-Java for web automation on Amazon Egypt and RestAssured for API testing with ReqRes. It follows best practices like the Page Object Model (POM), utilizes TestNG for test execution and reporting, and uses Data Provider for parameterized testing.

1. Test Scenarios

Web Automation (Amazon Egypt)

Open the "All" menu and navigate to the "All Video Games" section.

Apply filters:

"Free Shipping"

"New Condition"

Sort products by "Price: High to Low".

Add all products priced below 15,000 EGP to the cart (navigate to the next page if needed).

Verify all selected products are successfully added to the cart.

Add an address and select Cash Payment as the payment method.

Validate the total amount, including shipping fees if applicable.

API Testing (ReqRes API)

Base URL: https://reqres.in

Create a User:

Use Data Provider to send different sets of data.

Send a POST request to  /api/users with a JSON payload.

Validate the response for successful user creation.

Retrieve a User:

Use Data Provider to test multiple user IDs.

Send a GET request to /api/users/{id}.

Validate the response matches the created user details.

Update a User:

Send a PUT request to /api/users/{id} with updated details.

Validate that the response confirms the update and that the changes are reflected.

2. Framework & Tech Stack

Selenium WebDriver (for UI automation)

RestAssured (for API testing)

TestNG (Test framework)

Maven (Dependency management)

Page Object Model (POM) (for structured UI tests)

Log4j2 (for logging)

JSON Data Provider (for externalized test data management in API testing)

Git (Version control)

3. Setup & Installation

Prerequisites

Java (JDK 17 recommended)

Maven installed

IntelliJ IDEA / Eclipse

Google Chrome & ChromeDriver

Git (for version control)

Steps to Setup

Clone the repository

git clone https://github.com/keroshafik/FoodicsTestFramework.git
cd FoodicsTestFramework


4. Best Practices Implemented

Page Object Model (POM): Improves maintainability of UI tests.

Data-Driven Testing: Uses TestNG Data Provider to run multiple test cases dynamically.

Logging & Reporting: Uses Log4j2 for better debugging and TestNG Reports for test execution details.

Error Handling: Handles invalid requests, network failures, and assertion failures gracefully.

Externalized Test Data: Uses JSON and config.properties for dynamic test execution.

Version Control (Git): Ensures tracking of changes and version history.
