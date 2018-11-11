# Air-Flux-Capacity Application
Air-Flux-Capacity is a web service built on SpringBoot. It provides 2 REST end points building on Swagger.

# Structure

This service pre-computer all the plans right after the service is running. It has 3 parts, Input data, PlanGenerator, and REST end points.

## Input Data
The input data are from two files.

*aircrafts*
```
Boeing 737 - Berlin, registration FL-0001
Airbus A321 - Munich, registration FL-0002
Boeing 747-400 - London, registration FL-0003
Airbus A320 - Hamburg, registration FL-0004
```

*flight-schedules*
```
TXL
===================
10:00 TXL MUC 01:00
15:00 TXL MUC 01:00
16:00 TXL MUC 01:00
18:00 TXL MUC 01:00
21:00 TXL HAM 00:40
MUC
===================
10:00 MUC LHR 02:00
13:00 MUC TXL 01:00
15:00 MUC TXL 01:00
15:30 MUC LHR 02:00
17:30 MUC HAM 01:00
18:00 MUC LHR 02:30
20:00 MUC LHR 02:00
22:00 MUC TXL 01:00
LHR
===================
09:00 LHR HAM 02:30
12:00 LHR TXL 02:00
17:00 LHR TXL 02:00
20:30 LHR MUC 02:00
HAM
===================
10:00 HAM MUC 01:00
13:00 HAM MUC 01:00
20:00 HAM MUC 01:00
```

## PlanGenerator
We have 3 reasonable assumptions:

* there is only one valid plan
* airplane return home after office hours
* airplane can take off right after it arrives

## REST API
The API is documented by Swagger, (API Specification)(http://localhost:8080/swagger-ui.html). You can access the document when the service is up.

# Build and Deploy
**Prerequisite**
- java 1.8_91
- Maven 4

Go to project directory
`cd ~/<project-root>`
Run maven command
`mvn spring-boot:run`




