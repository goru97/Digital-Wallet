DigitalWallet
===========================================
Digital Wallet is a set of resources for a user to store ID cards, web-site logins, Bank accounts and so on.<br></br>

Technology Stack:<br></br>
REST, Scala, JSON, Jersey Framework, SpringBoot, Gradle, MongoDB.<br></br>

I. Resource Model:<br></br>
![alt tag](https://github.com/goru97/Digital-Wallet/blob/master/Resource_Model.png)<br></br>


II. Resource Details<br></br>

User<br></br>
user_id (System generated field) - {integer}<br></br>
email (Required) - {string}<br></br>
password (Required) - {string}<br></br>
name (Optional) {string}<br></br>
created_at (System generated field) - {DateTime}<br></br>
updated_at (System generated field) - {DateTime}<br></br>

IDCard<br></br>
card_id (System generated field) - {integer}<br></br>
card_name (Required) - {string}<br></br>
card_number (Required) - {string}<br></br>
expiration_date (Optional) - {Date}<br></br>

WebLogin<br></br>
login_id (System generated field) - {integer}<br></br>
url (Required) - {string}<br></br>
login (Required) - {string}<br></br>
password (Required) - {string}<br></br>

BankAccount<br></br>
ba_id (System generated field) - {integer}<br></br>
account_name (Optional) - {string}<br></br>
routing_number (Required) - {string}<br></br>
account_number (Required) - {string}<br></br>

III. APIs Implemented<br></br>

1. Create User<br></br>
Resource: /users<br></br>
Description: Add a new user to the digital wallet system.<br></br>
Request: <br></br>
POST /users (with the following payload in the request body)<br></br>
HTTP Headers:<br></br>
Content-type: application/json<br></br>
{
 “email”: “John.Smith@Gmail.com”,
 “password”: “secret”
}
Response:<br></br>
HTTP Code: 201<br></br>
{
 "user_id" : "u-123456",
 “email”: “John.Smith@Gmail.com”,
 “password”: “secret”,
 "created_at" : "2014-09-16T13:28:06.419Z"
}

2. View User<br></br>
Resource: /users/{user_id}
<br></br>Description: View an existing user of the wallet.<br></br>
Request:<br></br>
GET /users/u-123456<br></br>
Accept: application/json<br></br>
Response:<br></br>
HTTP Code: 200<br></br>
{
 "user_id" : "u-123456",
 “email”: “John.Smith@Gmail.com”,
 “password”: “secret”,
 "created_at" : "2014-09-16T13:28:06.419Z"
}

3. Update User<br></br>
Resource: /users/{user_id}
<br></br>Description: Update an existing user information.<br></br>
Request: <br></br>
PUT /users/u-123456 (with the following payload in the request body)<br></br>
HTTP Headers:<br></br>
Content-type: application/json<br></br>
{
 “email”: “John.Smith2@Gmail.com”,
 “password”: “newsecret”
}
<br></br>Response:<br></br>
HTTP Code: 201<br></br>
{
 "user_id" : "u-123456",
 “email”: “John.Smith2@Gmail.com”,
 “password”: “newsecret”,
 "created_at" : "2014-09-16T13:28:06.419Z"
}
        
4. Create ID Card<br></br>
Resource: /users/{user_id}/idcards<br></br>
Description: Add a new ID card to the wallet.<br></br>
Request: <br></br>
POST /users/{user_id}/idcards (with the following payload in the request body)<br></br>
HTTP Headers:<br></br>
Content-type: application/json<br></br>
{
 “card_name”: “San Jose Public Library Card”,
 “card_number”: “11213323”,
 “expiration_date”: “12-31-2014”
}
<br></br>Response:<br></br>
HTTP Code: 201<br></br>
{
 “card_id”: “c-123456”,
 “card_name”: “San Jose Public Library Card”,
 “card_number”: “11213323”,
 “expiration_date”: “12-31-2014”
}

5. List All ID Cards<br></br>
Resource: /users/{user_id}/idcards<br></br>
Description: List zero or more ID cards from the wallet.<br></br>
Request: <br></br>
GET /users/{user_id}/idcards<br></br>
HTTP Headers:<br></br>
Accept-type: application/json<br></br>
Response:<br></br>
HTTP Code: 200<br></br>
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

6. Delete ID Card<br></br>
Resource: /users/{user_id}/idcards/{card_id}
<br></br>Description: Delete an ID card from the wallet.<br></br>
Request: <br></br>
DELETE /users/{user_id}/idcards/{card_id}
<br></br>Response:
HTTP Code: 204<br></br>

7. Create Web Login<br></br>
Resource: /users/{user_id}/weblogins<br></br>
Description: Store a new web login in the wallet.<br></br>
Request: <br></br>
POST /users/{user_id}/weblogins (with the following payload in the request body)<br></br>
HTTP Headers:<br></br>
Content-type: application/json<br></br>
{
 “url”: “https://sjsu.instructure.com/”,
 “login”: “003334567”,
 “password”: “mysjsupassword”
}
<br></br>Response:<br></br>
HTTP Code: 201<br></br>
{
 “login_id”: “l-123456”,  # Small letter ‘l’ as in Lion.
 “url”: “https://sjsu.instructure.com/”,
 “login”: “003334567”,
 “password”: “mysjsupassword”
}

8. List All Web-site Logins<br></br>
Resource: /users/{user_id}/weblogins<br></br>
Description: List zero or more web-site logins from the wallet.<br></br>
Request:<br></br>
GET /users/{user_id}/weblogins<br></br>
HTTP Headers:<br></br>
Accept-type: application/json<br></br>
Response:<br></br>
HTTP Code: 200<br></br>
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

9. Delete Web Login<br></br>
Resource: /users/{user_id}/weblogins/{login_id}
<br></br>Description: Delete a web login from the wallet.<br></br>
Request: <br></br>
DELETE /users/{user_id}/weblogins/{login_id}
<br></br>Response:<br></br>
HTTP Code: 204<br></br>

10. Create Bank Account<br></br>
Resource: /users/{user_id}/bankaccounts<br></br>
Description: Save a bank account info in the wallet.<br></br>
Request: <br></br>
POST /users/{user_id}/bankaccounts (with the following payload in the request body)<br></br>
HTTP Headers:<br></br>
Content-type: application/json<br></br>
{
 “account_name”: “My Bank Of America Checking”,
 “routing_number”: “121000358”,
 “account_number”: “040834236”
}
<br></br>Response:<br></br>
HTTP Code: 201<br></br>
{
 “ba_id”: “b-123456”,
 “account_name”: “My Bank Of America Checking”,
 “routing_number”: “121000358”,
 “account_number”: “040834236”
}

11. List All Bank Accounts<br></br>
Resource: /users/{user_id}/bankaccounts<br></br>
Description: List zero or more bank accounts from the wallet.<br></br>
Request:<br></br>
GET /users/{user_id}/backaccounts<br></br>
HTTP Headers:<br></br>
Accept-type: application/json<br></br>
Response:<br></br>
HTTP Code: 200<br></br>
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

12. Delete Bank Account<br></br>
Resource: /users/{user_id}/bankaccounts/{ba_id}
<br></br>Description: Delete a bank account from the wallet.<br></br>
Request: <br></br>
DELETE /users/{user_id}/bankaccounts/{ba_id}
<br></br>Response:<br></br>
HTTP Code: 204<br></br>

IV. API Validation<br></br>
If a request misses any required fields in POST and PUT calls (via @Valid), it will return with HTTP 400 code with detailed error message list.
