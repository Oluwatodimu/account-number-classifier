## NUBAN Algorithm

This repo contains the algorithm for validating 
NUBAN (Nigeria Uniform Bank Account Number) in Java. 
The algorithm is based on [the CBN specification](https://www.cbn.gov.ng/OUT/2011/CIRCULARS/BSPD/NUBAN%20PROPOSALS%20V%200%204-%2003%2009%202010.PDF)
for the 
10-digit NUBAN. 

NB: You can also use this repo as a guide to creating custom error responses that
handle exceptions in the backend, which give a uniform format of error responses
across all endpoints whenever an exception occurs during an API call. 

### Setting up
- Clone the repo.
- Ensure Java 11 is installed on your machine. To set this up, click [here for Mac users](https://www.youtube.com/watch?v=oiqKK0FOjK0), 
[here for linux users](https://www.youtube.com/watch?v=8HPi2uNDWHU) and [here for windows users](https://www.youtube.com/watch?v=RBuZHg6eyIs).
- After navigating to the root folder, run the following command
  `./mvnw spring-boot:run`.



### API Endpoints

1. **Get possible account banks**

Given any 10-digit Nigerian bank account number, 
this endpoint returns a JSON array of banks where 
that account number could belong to.

I used an application of this algorithm to cut down the 
list of banks on a USSD app from  about 23 to less than 7 
after the user enters their 10 digit bank account number 
(NUBAN). This was a great improvement, keeping in mind that 
the maximum number of characters that can be displayed at  time
on the USSD interface is 160.

_Endpoints And Response Samples_

`GET {{base_url}}/api/v1/bank-list/{accountNumber}`

_Sample request_

`GET {{base_url}}/api/v1/bank-list/3093255991`

_Sample response_

```json
{
  "code": 200,
  "message": "Successful call",
  "data": [
    {
      "bankName": "Access Bank",
      "uniqueCbnBankCode": "044"
    },
    {
      "bankName": "EcoMobile",
      "uniqueCbnBankCode": "307"
    },
    {
      "bankName": "Fidelity Mobile",
      "uniqueCbnBankCode": "318"
    },
    {
      "bankName": "First Bank",
      "uniqueCbnBankCode": "011"
    },
    {
      "bankName": "Omoluabi Mortgage Bank",
      "uniqueCbnBankCode": "990"
    },
    {
      "bankName": "PayAttitude Online",
      "uniqueCbnBankCode": "329"
    },
    {
      "bankName": "UBA",
      "uniqueCbnBankCode": "033"
    }
  ]
}
```

_Sample request_

`GET {{base_url}}/api/v1/bank-list/2209327281`

_Sample response_

```json
{
  "code": 200,
  "message": "Successful call",
  "data": [
    {
      "bankName": "FBNMobile",
      "uniqueCbnBankCode": "309"
    },
    {
      "bankName": "NPF MicroFinance Bank",
      "uniqueCbnBankCode": "552"
    },
    {
      "bankName": "Providus Bank",
      "uniqueCbnBankCode": "101"
    },
    {
      "bankName": "Standard Chartered Bank",
      "uniqueCbnBankCode": "068"
    },
    {
      "bankName": "Wema Bank",
      "uniqueCbnBankCode": "035"
    },
    {
      "bankName": "Zenith Bank",
      "uniqueCbnBankCode": "057"
    }
  ]
}
```

_Sample bad request_

`GET {{base_url}}/api/v1/bank-list/12345`

_Sample response_

This custom response was handled using the @ControllerAdvice annotation

```json
{
  "code": 101,
  "message": "internal server error",
  "data": null
}
```





