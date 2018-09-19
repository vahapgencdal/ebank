# ebank
Online money Transfer between Accounts

### 1. install dependencies
execute `mvn clean install` under ebank directory.

### 2.  start application
execute `tomcat7:run` under ebank directory

### 3. Start Transaction Executer Job Service.
```
curl -X GET \
  http://localhost:8080/api/jobs/start \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 38ab5aa8-dfa6-404f-a3df-29e294493ea5'
```
- for stop service
```
curl -X GET \
  http://localhost:8080/api/jobs/stop \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: f551fb42-f15a-46e6-b2b7-76333fd64524'
```

### 4.  add accounts from console
1. execute below scripts for bank-accounts  

```
curl -X POST \
  http://localhost:8080/api/accounts/banks \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 561704ae-e941-4160-9167-eb7923d493f2' \
  -d '{
    "type":"DRAW",
    "name":"IS BANK Drawing TL Account",
    "accountNo":"9987858",
    "iban":"9999-8888-3456-6543-89",
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS"
}'
```
----
```

curl -X POST \
  http://localhost:8080/api/accounts/banks \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 2caa9e8b-8017-4ab0-ab6b-833c37250c90' \
  -d '{
    "type":"DRAW",
    "name":"IS BANK Drawing USD Account",
    "accountNo":"9988858",
    "iban":"9999-8888-7777-6543-89",
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"USD",
    "defaultAccount":true,
    "bank":"IS"
}'
```
----
```

curl -X POST \
  http://localhost:8080/api/accounts/banks \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 29a7ae78-8c55-46d6-93ea-63bf5c014409' \
  -d '{
    "type":"DRAW",
    "name":"IS BANK Drawing EURO Account",
    "accountNo":"9988858",
    "iban":"9999-8888-7777-6543-89",
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"EURO",
    "defaultAccount":true,
    "bank":"IS"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/banks \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 3cc646e5-6f33-407c-b23c-65c609f4fafd' \
  -d '{
    "type":"DRAW",
    "name":"GARAN Drawing TL Account",
    "accountNo":"9987858",
    "iban":"1111-8888-3456-6543-89",
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"GARAN"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/banks \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: dc402014-b29f-4ad0-93fe-7370aabc9fe3' \
  -d '{
    "type":"DRAW",
    "name":"GARAN Drawing USD Account",
    "accountNo":"9988858",
    "iban":"9999-6666-7777-6543-89",
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"USD",
    "defaultAccount":true,
    "bank":"GARAN"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/banks \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a705d3d1-c7bb-4fc6-a280-802b9ede00e9' \
  -d '{
    "type":"DRAW",
    "name":"GARAN Drawing EURO Account",
    "accountNo":"9988858",
    "iban":"9999-8888-7777-3333-89",
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"EURO",
    "defaultAccount":true,
    "bank":"GARAN"
}'
```
2. execute below scripts for dummy user-accounts  
```
curl -X POST \
  http://localhost:8080/api/accounts/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5cca5fc3-dfc2-4995-a7d8-d439249512fe' \
  -d '{
    "type":"DRAW",
    "name":"Vahap Drawing TL Account",
    "accountNo":"123456",
    "iban":"1234-4321-3456-6543-12",
    "totalAmount":1000000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"GARAN"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: ead209e5-8fcb-400d-acdd-ba583dc47463' \
  -d '{
    "type":"DRAW",
    "name":"Vahap Drawing Usd Account",
    "accountNo":"123457",
    "iban":"1234-4321-3456-6543-13",
    "totalAmount":1000000,
    "blockedAmount":0,
    "currency":"USD",
    "defaultAccount":true,
    "bank":"GARAN"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 7d9ae2c7-e80f-4bf4-a29e-b2c55706d829' \
  -d '{
    "type":"DRAW",
    "name":"Vahap Drawing Euro Account",
    "accountNo":"123458",
    "iban":"1234-4321-3456-6543-14",
    "totalAmount":1000000,
    "blockedAmount":0,
    "currency":"EURO",
    "defaultAccount":true,
    "bank":"GARAN"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 070d9506-9e5b-4e9a-a057-250beeed263f' \
  -d '{
    "type":"DRAW",
    "name":"Vahap Drawing TL Account",
    "accountNo":"123876",
    "iban":"1234-2367-3456-6543-89",
    "totalAmount":1000000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS",
    "user":"VAHAP"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a2a75025-6396-4539-a9b7-61ca1bd8a426' \
  -d '{
    "type":"DRAW",
    "name":"Emre Drawing TL Account",
    "accountNo":"123458",
    "iban":"1234-4321-3456-6543-89",
    "totalAmount":1000000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS",
    "user":"EMRE"
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/accounts/users \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a491c510-6185-4916-a34c-fa0d79916d8a' \
  -d '{
    "type":"DRAW",
    "name":"Emre Drawing TL Second Account",
    "accountNo":"1234458",
    "iban":"1234-4321-3456-3433-99",
    "totalAmount":1000000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS",
    "user":"EMRE"
}'
```
3. Transfer moneys All Posibilities example
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 45dcc893-a4c2-4cb0-b554-3a695a2a221c' \
  -d '{
	 "senderAccountNo":"123458",
	 "receiverAccountNo":"1234458",
	 "senderBic":"IS",
	 "receiverBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 28f2af75-3f82-4713-bb1c-de53acedb786' \
  -d '{
	 "senderAccountNo":"123458",
	 "receiverAccountNo":"123456",
	 "senderBic":"IS",
	 "receiverBic":"GARAN",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5aaabbd6-c314-489e-9d39-86db75b69361' \
  -d '{
	 "receiverAccountNo":"123458",
	 "senderAccountNo":"123456",
	 "senderBic":"GARAN",
	 "receiverBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToIban \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: d48ae70e-02d6-4a02-9933-d5613d25b841' \
  -d '{
	 "senderAccountNo":"123458",
	 "receiverIban":"1234-4321-3456-3433-99",
	 "senderBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToIban \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 7d7ca23a-d401-4565-b896-a26cf755a327' \
  -d '{
	 "senderAccountNo":"123458",
	 "receiverIban":"1234-2367-3456-6543-89",
	 "senderBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToIban \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: fe5c89b6-3f7c-45fc-88aa-b11c4dc78465' \
  -d '{
	 "senderAccountNo":"123456",
	 "receiverIban":"1234-4321-3456-6543-89",
	 "senderBic":"GARAN",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/ibanToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: d5689b76-63df-4005-8e82-6674e541e928' \
  -d '{
	 "receiverAccountNo":"123458",
	 "senderIban":"1234-4321-3456-3433-99",
	 "receiverBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/ibanToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 27ea21fd-cb8e-47d0-a056-b0f1fb37c6ed' \
  -d '{
	 "receiverAccountNo":"123458",
	 "senderIban":"1234-2367-3456-6543-89",
	 "receiverBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/ibanToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 6caf0246-f7ca-4103-b2b1-d290c28cfc29' \
  -d '{
	"senderIban":"1234-4321-3456-6543-12",
	 "receiverAccountNo":"123458",
	 "receiverBic":"IS",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/ibanToIban \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 93b3f110-406b-42c1-b89a-406a93b0c62f' \
  -d '{
	 "senderIban":"1234-4321-3456-6543-89",
	 "receiverIban":"1234-4321-3456-3433-99",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/ibanToIban \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: e97ef2b5-22e8-4ddf-9aa3-27f439df7cd7' \
  -d '{
	 "senderIban":"1234-2367-3456-6543-89",
	 "receiverIban":"1234-4321-3456-3433-99",
	 "amount":300
}'
```
----
```
curl -X POST \
  http://localhost:8080/api/transfers/ibanToIban \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a3ceb243-1d89-470d-8966-c0f3bc7bcfb2' \
  -d '{
	 "senderIban":"1234-4321-3456-6543-12",
	 "receiverIban":"1234-4321-3456-3433-99",
	 "amount":300
}'
```
### 5. Last Step Do Some Transfer among users. 
1. Transfer between over AccountNo to AccountNo with Same User Same Bank
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 63b85069-400d-4e1f-9b13-2534c80c2b0f' \
  -d '{
	 "senderAccountNo":"123458",
	 "receiverAccountNo":"1234458",
	 "senderBic":"IS",
	 "receiverBic":"IS",
	 "amount":300
}'
```
2. Transfer between over AccountNo to AccountNo with Different User Same Bank
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 776e6bc1-e880-481a-9c6f-ece5a02a6f6e' \
  -d '{
	 "senderAccountNo":"123458",
	 "receiverAccountNo":"123456",
	 "senderBic":"IS",
	 "receiverBic":"GARAN",
	 "amount":300
}'
```
3. Transfer between over AccountNo to AccountNo with Different User Different Bank
```
curl -X POST \
  http://localhost:8080/api/transfers/accountToAccount \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: be3cd384-05b7-4e57-8c69-13dda5077174' \
  -d '{
	 "receiverAccountNo":"123458",
	 "senderAccountNo":"123456",
	 "senderBic":"GARAN",
	 "receiverBic":"IS",
	 "amount":300
}'
```
you will see transacion logs in console.
