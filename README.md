DigitalWallet
===========================================
Digital Wallet is a set of resources for a user to store ID cards, web-site logins, Bank accounts and so on.<br></br>

Technology Stack:<br></br>
REST, Scala, JSON, Jersey Framework, SpringBoot, Gradle, MongoDB.

I. Resource Model:<br></br>
![alt tag](https://github.com/goru97/Digital-Wallet/blob/master/Resource_Model.png)


II. Resource Details

User
user_id (System generated field) - {integer}
email (Required) - {string}
password (Required) - {string}
name (Optional) {string}
created_at (System generated field) - {DateTime}
updated_at (System generated field) - {DateTime}
IDCard
card_id (System generated field) - {integer}
card_name (Required) - {string}
card_number (Required) - {string}
expiration_date (Optional) - {Date}
WebLogin
login_id (System generated field) - {integer}
url (Required) - {string}
login (Required) - {string}
password (Required) - {string}
BankAccount
ba_id (System generated field) - {integer}
account_name (Optional) - {string}
routing_number (Required) - {string}
account_number (Required) - {string}

III. APIs Implemented

1. Create User
Resource: /users
Description: Add a new user to the digital wallet system.
Request: 
POST /users (with the following payload in the request body)
HTTP Headers:
Content-type: application/json
{
 “email”: “John.Smith@Gmail.com”,
 “password”: “secret”
}
Response:
HTTP Code: 201
{
 "user_id" : "u-123456",
 “email”: “John.Smith@Gmail.com”,
 “password”: “secret”,
 "created_at" : "2014-09-16T13:28:06.419Z"
}

2. View User
Resource: /users/{user_id}
Description: View an existing user of the wallet.
Request:
GET /users/u-123456
Accept: application/json
Response:
HTTP Code: 200
{
 "user_id" : "u-123456",
 “email”: “John.Smith@Gmail.com”,
 “password”: “secret”,
 "created_at" : "2014-09-16T13:28:06.419Z"
}

3. Update User
Resource: /users/{user_id}
Description: Update an existing user information.
Request: 
PUT /users/u-123456 (with the following payload in the request body)
HTTP Headers:
Content-type: application/json
{
 “email”: “John.Smith2@Gmail.com”,
 “password”: “newsecret”
}
Response:
HTTP Code: 201
{
 "user_id" : "u-123456",
 “email”: “John.Smith2@Gmail.com”,
 “password”: “newsecret”,
 "created_at" : "2014-09-16T13:28:06.419Z"
}
        
4. Create ID Card
Resource: /users/{user_id}/idcards
Description: Add a new ID card to the wallet.
Request: 
POST /users/{user_id}/idcards (with the following payload in the request body)
HTTP Headers:
Content-type: application/json
{
 “card_name”: “San Jose Public Library Card”,
 “card_number”: “11213323”,
 “expiration_date”: “12-31-2014”
}
Response:
HTTP Code: 201
{
 “card_id”: “c-123456”,
 “card_name”: “San Jose Public Library Card”,
 “card_number”: “11213323”,
 “expiration_date”: “12-31-2014”
}

5. List All ID Cards
Resource: /users/{user_id}/idcards
Description: List zero or more ID cards from the wallet.
Request: 
GET /users/{user_id}/idcards
HTTP Headers:
Accept-type: application/json
Response:
HTTP Code: 200
[
  {
     “card_id”: “c-123456”,
     “card_name”: “San Jose Public Library Card”,
     “card_number”: “11213323”,
     “expiration_date”: “12-31-2014”
  },
  {
     “card_id”: “c-123457”,
     “card_name”: “Social Security Card”,
     “card_number”: “302-123-4567”
  }
]

6. Delete ID Card
Resource: /users/{user_id}/idcards/{card_id}
Description: Delete an ID card from the wallet.
Request: 
DELETE /users/{user_id}/idcards/{card_id}
Response:
HTTP Code: 204

7. Create Web Login
Resource: /users/{user_id}/weblogins
Description: Store a new web login in the wallet.
Request: 
POST /users/{user_id}/weblogins (with the following payload in the request body)
HTTP Headers:
Content-type: application/json
{
 “url”: “https://sjsu.instructure.com/”,
 “login”: “003334567”,
 “password”: “mysjsupassword”
}
Response:
HTTP Code: 201
{
 “login_id”: “l-123456”,  # Small letter ‘l’ as in Lion.
 “url”: “https://sjsu.instructure.com/”,
 “login”: “003334567”,
 “password”: “mysjsupassword”
}

8. List All Web-site Logins
Resource: /users/{user_id}/weblogins
Description: List zero or more web-site logins from the wallet.
Request:
GET /users/{user_id}/weblogins
HTTP Headers:
Accept-type: application/json
Response:
HTTP Code: 200
[
 {
     “login_id”: “l-123456”,  # Small letter ‘l’ as in Lion.
     “url”: “https://sjsu.instructure.com/”,
     “login”: “003334567”,
     “password”: “mysjsupassword”
 },
 {
     “login_id”: “l-123457”,
     “url”: “https://mail.yahoo.com”,
     “login”: “my_yahoo_mail_login”,
     “password”: “secret”
 }
]

9. Delete Web Login
Resource: /users/{user_id}/weblogins/{login_id}
Description: Delete a web login from the wallet.
Request: 
DELETE /users/{user_id}/weblogins/{login_id}
Response:
HTTP Code: 204

10. Create Bank Account
Resource: /users/{user_id}/bankaccounts
Description: Save a bank account info in the wallet.
Request: 
POST /users/{user_id}/bankaccounts (with the following payload in the request body)
HTTP Headers:
Content-type: application/json
{
 “account_name”: “My Bank Of America Checking”,
 “routing_number”: “121000358”,
 “account_number”: “040834236”
}
Response:
HTTP Code: 201
{
 “ba_id”: “b-123456”,
 “account_name”: “My Bank Of America Checking”,
 “routing_number”: “121000358”,
 “account_number”: “040834236”
}

11. List All Bank Accounts
Resource: /users/{user_id}/bankaccounts
Description: List zero or more bank accounts from the wallet.
Request:
GET /users/{user_id}/backaccounts
HTTP Headers:
Accept-type: application/json
Response:
HTTP Code: 200
[
 {
    “ba_id”: “b-123456”,
    “account_name”: “My Bank Of America Checking”,
    “routing_number”: “121000358”,
    “account_number”: “040834236”
 },
 {
    “ba_id”: “b-123457”,
    “routing_number”: “131000359”,
    “account_number”: “0408342234”
 }
]

12. Delete Bank Account
Resource: /users/{user_id}/bankaccounts/{ba_id}
Description: Delete a bank account from the wallet.
Request: 
DELETE /users/{user_id}/bankaccounts/{ba_id}
Response:
HTTP Code: 204

IV. API Validation
If a request misses any required fields in POST and PUT calls (via @Valid), it will return with HTTP 400 code with detailed error message list.
