
# Solution

I used Spring MVC to expose the Rest API. The application is a hexagonal layered architecture implemented following DDD. I used a functional programming style where applicable. All domain objects are immutable.
To accept a very `high throughput`, the in memory storage is a group of multiple `ConcurrentQueues`. Adding new sales amounts will just round robin through the queue group. This will reduce locking the storage per thread.
Adding new amounts will push to the start of a queue. A scheduler will run to clean old amounts and it will pull from the ending side of the queue. This way time and space complexity is at minimum.    
The workflow I followed was TDD (plan small and red, green, refactor) and tests were written using BDD. 


```
No traversing of entire queue is needed.
                              ________________________________________
                             |                                        |
pull old Amounts    <--------|             Amount queue               |<-------- add new Amounts
until a new one is found     |________________________________________|
```



#### Stress test

Limited `max allocated memory` is `64Mb`. Can be changed in the pom.xml file.

To stress test the application I used JMeter. Find the JMeter configuration in `stressTest_jmeter.jmx` file.

The app stabilizes at a throughput of 500 000 requests/minute on my system with the limited max allocated memory.
   


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
This will also log the current state of the stored sale amounts collection:
```
Calculating statistics for sales between 2018-08-26T17:22:03.956 and 2018-08-26T17:23:03.956. Total sales amount currently stored: 9
```


## Setup

- run tests: `mvn test` or `mvn clean install` to cleanup, run tests and compile.
- start app: `mvn spring-boot:run`


## Progress

First implementation was handling around 200k sales per minute.
After optimising by adding static types, concurrent storage, cleanup scheduler, limit logging i got it to stabilize at 450k sales per minute. 


