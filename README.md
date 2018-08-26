


## Requests:

- post amount:

```
curl -X POST http://localhost:8080/sales \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d sales_amount=2.15
  
Response: 202 Accepted  
```

- get statistics:

```
curl -X GET http://localhost:8080/statistics

Response: 200 OK
{
    "total_sales_amount": "68.35",
    "average_amount_per_order": "22.78"
}
```
This will also log the current state of the stored sale amounts collection:
```
Calculating statistics for sales between 2018-08-26T17:22:03.956 and 2018-08-26T17:23:03.956. Total sales amount currently stored: 9
```


## Setup

- run tests: `mvn test` or `mvn clean install` to cleanup, run tests and compile.
- start app: `mvn spring-boot:run`
