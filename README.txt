ROUND ROBIN LOAD BALANCING - RIDE SHARING PLATFORM
=================================================

Requirements
------------
- JDK 17
- Spring Tool Suite 4
- Maven (STS bundled Maven is fine)
- Internet for the first Maven dependency download

PROJECT STRUCTURE
-----------------
round-robin-ride-sharing
 |-- driver-service       (one app launched three times)
 |-- api-gateway          (gateway on port 8080)
 |-- pom.xml

IMPORT INTO SPRING TOOL SUITE
-----------------------------
1. Extract this ZIP.
2. Open STS.
3. File > Import > Maven > Existing Maven Projects.
4. Browse to the extracted round-robin-ride-sharing folder.
5. Select the parent pom.xml and click Finish.
6. Wait until Maven downloads dependencies and the projects finish building.

RUN THREE DRIVER INSTANCES
--------------------------
Run DriverServiceApplication three times, each with different Program Arguments.

In STS: Right-click DriverServiceApplication.java > Run As > Run Configurations...
Create three Spring Boot App configurations:

A) Name: Driver-1
   Main class: com.example.driver.DriverServiceApplication
   Program arguments: --server.port=8081 --server.instance=Driver-1

B) Name: Driver-2
   Main class: com.example.driver.DriverServiceApplication
   Program arguments: --server.port=8082 --server.instance=Driver-2

C) Name: Driver-3
   Main class: com.example.driver.DriverServiceApplication
   Program arguments: --server.port=8083 --server.instance=Driver-3

Click Apply > Run for each configuration. Keep all three running.
(Do not change application.properties; command-line arguments override its defaults.)

RUN API GATEWAY
---------------
Right-click ApiGatewayApplication.java > Run As > Spring Boot App.
Gateway starts at http://localhost:8080.

TEST
----
Open a browser or Postman and send GET requests repeatedly to:
http://localhost:8080/drivers

Expected JSON responses will rotate between:
{"service":"Driver Service","instance":"Driver-1","message":"Request handled successfully"}
{"service":"Driver Service","instance":"Driver-2","message":"Request handled successfully"}
{"service":"Driver Service","instance":"Driver-3","message":"Request handled successfully"}

You can also test:
http://localhost:8080/drivers/health

To send several requests from Windows PowerShell:
1..9 | ForEach-Object { (Invoke-RestMethod http://localhost:8080/drivers).instance }

Expected sequence:
Driver-1
Driver-2
Driver-3
Driver-1
Driver-2
Driver-3
Driver-1
Driver-2
Driver-3

NOTES
-----
- The gateway uses Spring Cloud's SimpleDiscoveryClient static instance list;
  Eureka is NOT required for this lab.
- The lb://driver-service URI asks Spring Cloud LoadBalancer to select an
  instance. Round Robin is the default selection strategy.
- If a port is already in use, stop the other application using that port
  or choose another port and update application.yml.
- The first Maven build needs internet access.
- If STS reports a Java version error, configure the project JRE as JDK 17:
  Project > Properties > Java Build Path > Libraries.
