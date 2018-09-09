### This implementation uses Spring web framework.
For an implementation using Rapidoid web framework see the other branch.

# Requirements

Build a microservice that will calculate real-time statistics of item sales on a marketplace platform. This microservice will feed data to a dashboard installed in a business team’s room.
The microservice shall have a REST interface with two endpoints. The first endpoint will be called by the checkout service whenever a new payment is received and the second endpoint will provide statistics about the total order amount and average amount per order for the last 60 seconds. (Orders between ​ t ​ and ​ t ​ - 60 sec, given ​ t ​ = request time)

Specifications for the requested endpoints are as follows:

#### Transactions

```
Sales
URL:​ /sales
Method: ​ POST
Content-Type:​ application/x-www-form-urlencodedParameters:
sales_amount: Number String (e.g. “5.00”)
Return HTTP Code:​ 202 Accepted
Return Body: ​ blank
```
```
Statistics
URL:​ /statistics
Method: ​ GET
Parameters: ​ none
Return HTTP Code:​ 200 OK
Return Body:
{
  total_sales_amount: “52100.00”,
  average_amount_per_order: “32.14”
}
```

Around 250.000 items are sold each minute. So, your service must expect a high amount of transaction data per minute and on several TCP connections in parallel

You can keep and process data in main memory. You are not allowed to use persistent storage. If the service should be restarted, losing this in-memory data is not a big problem since the risk is only to lose sales statistics for a couple of seconds, then new orders will arrive and your service will again show statistics.

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

Limited `max allocated memory` is `128Mb`. Can be changed in the pom.xml file.

To stress test the application I used `JMeter`. Find the JMeter configuration in `stressTest_jmeter.jmx` file. JMeter is set to POST 1mil random Amounts per minute. And also GET statistics every second.  

The app stabilizes at a throughput of 1 300 000 requests/minute on my own system with the `128Mb` limited max HEAP allocated memory.


## Setup

Execute in order:
- `mvn clean test` to cleanup and run tests. 
- `mvn clean pakcage` to build `jar` 
- `java -Xmx128m -jar target/salesstats-cristiand-0.0.1-SNAPSHOT.jar` to start app with 128Mb max HEAP size
- `./jmeter -n -t [path-to-file]/stressTest_jmeter.jmx` to start headless JMeter and shoot millions requests to app API 


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
    "total_sales_amount":"66949695.00",
    "total_sales_included_count":"669270",
    "average_amount_per_order":"100.03"
```

A scheduler will log the count of recent stored amounts just for demo purpose:

```
Count recent sales amounts currently stored: 1536122
```

