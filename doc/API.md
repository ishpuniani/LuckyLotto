# APIs

## Create Tickets
#### Request: POST /tickets
```shell script
curl --location --request POST 'localhost:8080/tickets' \
--form 'n=3'
```

#### Response: JSON object of the created ticket
```json
{
    "id": "1c002718-39fe-40e6-846c-05142569799d",
    "lines": [
        {
            "elements": [
                "0",
                "0",
                "2"
            ]
        },
        {
            "elements": [
                "1",
                "0",
                "2"
            ]
        },
        {
            "elements": [
                "2",
                "1",
                "0"
            ]
        }
    ],
    "totalScore": -1.0,
    "checked": false,
    "createdAt": "2020-08-16T03:19:01.431+0000",
    "updatedAt": "2020-08-16T03:19:01.431+0000"
}
```

## Get Ticket

#### Request: GET /tickets/{id}
```shell script
curl --location --request GET 'localhost:8080/tickets/1c002718-39fe-40e6-846c-05142569799d'
```

#### Response: JSON object of the ticket
```json
{
    "id": "1c002718-39fe-40e6-846c-05142569799d",
    "lines": [
        {
            "elements": [
                "0",
                "0",
                "2"
            ]
        },
        {
            "elements": [
                "1",
                "0",
                "2"
            ]
        },
        {
            "elements": [
                "2",
                "1",
                "0"
            ]
        }
    ],
    "totalScore": -1.0,
    "checked": false,
    "createdAt": "2020-08-16T03:19:01.431+0000",
    "updatedAt": "2020-08-16T03:19:01.431+0000"
}
```

## Get All Tickets
#### Request: GET /tickets
```shell script
curl --location --request GET 'localhost:8080/tickets/'
```

#### Response: JSON array of all tickets
```json
[
  {
    "id": "1c002718-39fe-40e6-846c-05142569799d",
    "lines": [
      {
        "elements": [
          "0",
          "0",
          "2"
        ]
      },
      {
        "elements": [
          "1",
          "0",
          "2"
        ]
      },
      {
        "elements": [
          "2",
          "1",
          "0"
        ]
      }
    ],
    "totalScore": -1,
    "checked": false,
    "createdAt": "2020-08-16T03:19:01.431+0000",
    "updatedAt": "2020-08-16T03:19:01.431+0000"
  },
  {
    "id": "317f978e-f23e-4990-a4f4-3553a6bc2c7a",
    "lines": [
      {
        "elements": [
          "2",
          "0",
          "0"
        ],
        "score": 10
      },
      {
        "elements": [
          "1",
          "2",
          "2"
        ],
        "score": 1
      },
      {
        "elements": [
          "0",
          "1",
          "0"
        ],
        "score": 0
      }
    ],
    "totalScore": 11,
    "checked": true,
    "createdAt": "2020-08-16T01:27:07.647+0000",
    "updatedAt": "2020-08-16T01:42:05.185+0000"
  }
]
```

## Amend Ticket
#### Request: PUT /tickets/{id} 
```shell script
curl --location --request PUT 'localhost:8080/tickets/1c002718-39fe-40e6-846c-05142569799d' \
--form 'n=2'
```

Response: 
```json
{
    "id": "1c002718-39fe-40e6-846c-05142569799d",
    "lines": [
        {
            "elements": [
                "0",
                "0",
                "2"
            ]
        },
        {
            "elements": [
                "2",
                "0",
                "0"
            ]
        },
        {
            "elements": [
                "1",
                "0",
                "2"
            ]
        },
        {
            "elements": [
                "2",
                "1",
                "0"
            ]
        },
        {
            "elements": [
                "1",
                "2",
                "2"
            ]
        }
    ],
    "totalScore": -1.0,
    "checked": false,
    "createdAt": "2020-08-16T03:19:01.431+0000",
    "updatedAt": "2020-08-16T03:19:01.431+0000"
}
```

## Check Ticket status
#### Request:PUT /tickets/status/{id} 
```shell script
curl --location --request PUT 'localhost:8080/tickets/status/1c002718-39fe-40e6-846c-05142569799d'
```

#### Response: JSON object of the checked ticket.  
```json
{
    "id": "1c002718-39fe-40e6-846c-05142569799d",
    "lines": [
        {
            "elements": [
                "0",
                "0",
                "2"
            ],
            "score": 10.0
        },
        {
            "elements": [
                "2",
                "0",
                "0"
            ],
            "score": 10.0
        },
        {
            "elements": [
                "1",
                "0",
                "2"
            ],
            "score": 1.0
        },
        {
            "elements": [
                "2",
                "1",
                "0"
            ],
            "score": 1.0
        },
        {
            "elements": [
                "1",
                "2",
                "2"
            ],
            "score": 1.0
        }
    ],
    "totalScore": 23.0,
    "checked": true,
    "createdAt": "2020-08-16T03:19:01.431+0000",
    "updatedAt": "2020-08-16T03:29:52.875+0000"
}
```