# ebank
Online money Transfer between Accounts

###1. install dependencies
execute `mvn clean install` under ebank directory.

###2.  start application
execute `tomcat7:run` under ebank directory

###3.  add accounts from console
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
2. You must add any user for money transfer
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
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"GARAN"
}'

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
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"USD",
    "defaultAccount":true,
    "bank":"GARAN"
}'

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
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"EURO",
    "defaultAccount":true,
    "bank":"GARAN"
}'

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
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS",
    "user":"VAHAP"
}'

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
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS",
    "user":"EMRE"
}'

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
    "totalAmount":1000,
    "blockedAmount":0,
    "currency":"TL",
    "defaultAccount":true,
    "bank":"IS",
    "user":"EMRE"
}'
```