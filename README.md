
# Solution

I used Spring MVC to expose the Rest API. The application is a hexagonal layered architecture implemented following DDD. I used a functional programming style where applicable. All domain objects are immutable.

To accept a very `high throughput`, the in memory storage is a group of multiple `ConcurrentQueues`. Adding new sales amounts will just `round robin` through the queue group (see `SalesRepository`). This will reduce locking the storage per thread when adding amounts and multithreading can be used to calculate statistics.
Adding new amounts will push to the start of a queue. 

A scheduler will run to remove old amounts and it will pull from the ending side of the queue. This way time and space complexity is at minimum. No traversing of the entire queue is needed.    

The workflow I followed was TDD (plan small and red, green, refactor) and tests were written using BDD. 


```
No traversing of entire queue is needed:
                              _____________________________________
                             |                                     |
pull old Amounts    <--------|     Amounts queue valid for 60s     |<-------- add new Amounts
until a new one is found     |_____________________________________|
```



#### Stress test

Limited `max allocated memory` is `64Mb`. Can be changed in the pom.xml file.

To stress test the application I used `JMeter`. Find the JMeter configuration in `stressTest_jmeter.jmx` file. JMeter is set to POST 1mil random Amounts per minute. And also GET statistics every second.  

The app stabilizes at a throughput of 500 000 requests/minute on my own system with the `64Mb` limited max allocated memory and development environment.

Increasing the allocated memory will allow a throughput of over 2 000 000 req/min.
   


## Setup

- run tests: `mvn test` or `mvn clean install` to cleanup, run tests and compile.
- start app: `mvn spring-boot:run`


## Requests

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

A scheduler will log the count of recent stored amounts:

```
Count recent sales amounts currently stored: 532185
```

