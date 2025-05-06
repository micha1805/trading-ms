# Spring trading app demo API

A little project of a REST API in Spring.

It uses authentication via JWT, each endpoint is protected except `login` and `signup` that will respond with a JWT token.

For every other endpoint you'll need an `Authorization` header in your request, like so :

```http request
Authorization: Bearer MY.JWT.TOKEN
```

So in the following table each secured endpoint must have this header to work. 

This is an ongoing demo project, not suitable for production yet. 

## Endpoints API

| Method | Secured | Route                        | Description                                                                                  |
|--------|---------|------------------------------|----------------------------------------------------------------------------------------------|
| POST   | ❌       | /api/v1/auth/login           | Login                                                                                        |
| POST   | ❌       | /api/v1/auth/signup          | Signup                                                                                       |
| PUT    | ✅       | /api/v1/user/update          | Update user's profile (except balance)                                                       |
| GET    | ✅       | /api/v1/user/currentBalance  | Return current balance (all the money that is NOT in an open position)                       |
| GET    | ✅       | /api/v1/profile              | Fetch all the profile data, including the user's balance                                     |
| POST   | ✅       | /api/v1/wire                 | Make a wire (deposit OR withdraw money)                                                      |
| GET    | ✅       | /api/v1/trades/index         | Fetch all our trades                                                                         |
| GET    | ✅       | /api/v1/trade/:id            | Fetch one trade info                                                                         |
| GET    | ✅       | /api/v1/trade/index/open     | Fetch all our open trades                                                                    |
| GET    | ✅       | /api/v1/trade/index/closed   | Fetch all our closed trades                                                                  |
| POST   | ✅       | /api/v1/trade/openTrade/     | Open a long position (buy), the amount and the stock is specified in the body of the request |
| PATCH  | ✅       | /api/v1/trade/closeTrade/:id | Close the position                                                                           |
| GET    | ✅       | /api/v1/trade/closedPNL      | Return the total closed PNL (all closed trades)                                              |
| GET    | ✅       | /api/v1/trade/openPNL        | Return the total open PNL (all open trades)                                                  |

### Requests and responses format

#### POST /api/v1/auth/login
**Request:**
```json
{
	"email": "hello@gmail.com",
	"password": "123456"
}
```

**Response :**
```json
{
	"token": "JWT_TOKEN"
}
```
### POST /api/v1/auth/signup

**Request:**
```json
{
  "email": "john@wayne.com",
  "password" : "123456",
  "first_name": "John",
  "last_name": "Wayne",
  "address": "Far in the West",
  "phone_number": "1-111-111-111"
}
```

**Response :**
```json
{
	"token": "JWT_TOKEN"
}
``` 

### PUT /api/v1/user/update

**Request's body :**
```json
{
  "email": "john@wayne.com",
  "password" : "123456",
  "first_name": "John",
  "last_name": "Wayne",
  "address": "Far in the West",
  "phone_number": "1-111-111-111"
}
```

**Response : 200**

### GET /api/v1/user/currentBalance

**Request:**

Only the Authorisation header

**Response :**
```json
{
	"current_balance_in_cent": 12345
}
``` 


### GET /api/v1/profile

**Request:**

Only the Authorisation header


Response :

```json
{
  "email": "john@wayne.com",
  "first_name": "John",
  "last_name": "Wayne",
  "address": "Far in the West",
  "phone_number": "1-111-111-111"
}
```


### POST /api/v1/wire

Request : 

```json
{
  "amount_in_cent": 12345,
  "withdrawal" : false
}
```
Response : 201

### GET /api/v1/trade/index

**Request:**

Only the Authorisation header


Response : 

```json
{
  "trades": [
    {
      "id": 1,
      "close_date_time": "2022-04-19T22:18:19.650972",
      "close_price_in_cent": 8004,
      "open": false,
      "open_date_time": "2022-03-19T22:18:19.650787",
      "open_price_in_cent": 1318,
      "quantity": 17.0,
      "symbol": "TDW"
    },
    {
      "id": 2,
      "close_date_time": "2022-04-19T22:18:19.656258",
      "close_price_in_cent": 6061,
      "open": true,
      "open_date_time": "2022-03-19T22:18:19.656244",
      "open_price_in_cent": 5880,
      "quantity": 13.0,
      "symbol": "ARNC"
    },
    {
      "id": 3,
      "close_date_time": "2022-04-19T22:18:19.658076",
      "close_price_in_cent": 8978,
      "open": false,
      "open_date_time": "2022-03-19T22:18:19.658067",
      "open_price_in_cent": 7254,
      "quantity": 44.0,
      "symbol": "UHS"
    }
  ]
}
```
### GET /api/v1/trade/:id

**Request:**

Only the Authorisation header


Response : 

```json
{
  "trade": {
      "id": 1,
      "close_date_time": "2022-04-19T22:18:19.650972",
      "close_price_in_cent": 8004,
      "open": false,
      "open_date_time": "2022-03-19T22:18:19.650787",
      "open_price_in_cent": 1318,
      "quantity": 17.0,
      "symbol": "TDW"
    }
}
```


### GET /api/v1/trade/index/open

**Request:**

Only the Authorisation header

**Response**

```json
[
  {
    "id": 12,
    "open_date_time": "2022-04-19T22:18:19.675772",
    "open_price_in_cent": 1547,
    "open": true,
    "close_date_time": "2022-03-19T22:18:19.675765",
    "close_price_in_cent": 4230,
    "quantity": 46,
    "symbol": "TSE"
  },
  {
    "id": 14,
    "open_date_time": "2022-04-19T22:18:19.678775",
    "open_price_in_cent": 2252,
    "open": true,
    "close_date_time": "2022-03-19T22:18:19.678761",
    "close_price_in_cent": 5179,
    "quantity": 34,
    "symbol": "NEE^K"
  },
  {
    "id": 17,
    "open_date_time": "2022-04-19T22:18:19.702473",
    "open_price_in_cent": 6984,
    "open": true,
    "close_date_time": "2022-03-19T22:18:19.702464",
    "close_price_in_cent": 9135,
    "quantity": 38,
    "symbol": "GGZ"
  },
  {
    "id": 19,
    "open_date_time": "2022-04-19T22:18:19.707170",
    "open_price_in_cent": 5005,
    "open": true,
    "close_date_time": "2022-03-19T22:18:19.707162",
    "close_price_in_cent": 8731,
    "quantity": 18,
    "symbol": "UMH^A"
  }
]

```


### GET /api/v1/trade/index/closed

**Request:**

Only the Authorisation header

**Response**

```json
[
  {
    "id": 22,
    "open_date_time": "2022-04-19T22:18:19.713722",
    "open_price_in_cent": 4429,
    "open": false,
    "close_date_time": "2022-03-19T22:18:19.713712",
    "close_price_in_cent": 4434,
    "quantity": 28,
    "symbol": "ICD"
  },
  {
    "id": 23,
    "open_date_time": "2022-04-19T22:18:19.715048",
    "open_price_in_cent": 1061,
    "open": false,
    "close_date_time": "2022-03-19T22:18:19.715040",
    "close_price_in_cent": 5405,
    "quantity": 38,
    "symbol": "FGB"
  },
  {
    "id": 24,
    "open_date_time": "2022-04-19T22:18:19.716791",
    "open_price_in_cent": 2942,
    "open": false,
    "close_date_time": "2022-03-19T22:18:19.716782",
    "close_price_in_cent": 4749,
    "quantity": 20,
    "symbol": "INN^B"
  }
]

```


### POST /api/v1/trade/openTrade/

**Request:**

```json
{
  "quantity": 20,
  "symbol": "TSLA"
}
```
**Response**

Either a 201 if enough money to succeed either a 402 for payment required.

### PATCH /api/v1/trade/closeTrade/:id


**Request:**

Only the Authorisation header

**Response**

200


### GET /api/v1/trade/closedPNL	

**Request:**

Only the Authorisation header

**Response**

```json
{
  "closed_Pnl_in_cent" : 12345
}
```

### GET /api/v1/trade/openPNL	

**Request:**

Only the Authorisation header

**Response**

```json
{
  "open_Pnl_in_cent" : 12345
}
```

## Testing

Testing is currently only done using Postman, so no unit tests only integration testing, with scripting to automatically grab the token after login/signup.


## TODO next

In no particular order :

- Doc with Swagger
- Find out how to manage CORS config
- Testing with JUnit
- Real Stock data
- Do the seed once, then just connect to db.
- Env variable (jwt secret key)
- maybe refactor the files structure for http req/resp
- refactor ugly lines of code (raw SQL query for balance ? check services methods, saving twice not good etc.)
- pagination for indexes endpoints
- more realistic wires ?
- dockerize the app
- add `created_at` and `updated_at` rows for each model/table.
- Push in production
- add some CI/CD (GitHub actions)
- Use Spring AOP for JWT authorisation ? (@PreAuthorize ?)
- etc.
