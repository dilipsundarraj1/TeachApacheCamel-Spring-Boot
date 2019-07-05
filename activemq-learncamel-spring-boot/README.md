# activemq-learncamel-spring-boot
This code base has the working code to connect to the REST API

This code base is bundled with the following:

-   Lombok - application logging
-   jdbc -  Postgres DB configuration
-   Email - Email configuration
-   Application Health Check Configuration.
-   Camel Spring Boot Testing Libraries.
-   ActiveMQ Configuration

## Run the app in Java 11

Follow the steps mentioned below to run the app in **Java 11**.

### Pom.xml

-   Update the Java version to 11

```$xslt
<java.version>11</java.version>
```

-   Add the below latest **lombok** dependency.


```$xslt
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.8</version>
            <scope>provided</scope>
        </dependency>
```

-   Add the **JAXB** related dependecies.

```
        <dependency>
			<groupId>javax.xml.bind</groupId>
			<artifactId>jaxb-api</artifactId>
			<version>2.2.11</version>
		</dependency>
		<dependency>
			<groupId>com.sun.xml.bind</groupId>
			<artifactId>jaxb-core</artifactId>
			<version>2.2.11</version>
		</dependency>
		<dependency>
			<groupId>com.sun.xml.bind</groupId>
			<artifactId>jaxb-impl</artifactId>
			<version>2.2.11</version>
		</dependency>
        <dependency>
            <groupId>javax.activation</groupId>
            <artifactId>javax.activation-api</artifactId>
            <version>1.2.0</version>
        </dependency>

```