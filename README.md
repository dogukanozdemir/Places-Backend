# Nearby Search Application

![Google Maps Platform](https://developers.google.com/static/maps/images/google-maps-platform-1200x675.png)


The Nearby Search Application is a Java-based backend server that provides a RESTful API for conducting nearby location searches using the Google Places API. 
This application not only performs real-time searches but also efficiently caches and retrieves responses to minimize the usage of external APIs and enhance performance.

## Table of Contents

1. Overview
2. Prerequisites
3. Getting Started
    - Setting Up the Database
4. API Endpoints
5. Caching Strategy
6. Validation
7. Exception Handling
8. License

## Overview

This Java application was built to meet the following requirements:

1. Accepts longitude, latitude, and radius as query parameters to perform nearby location searches.
2. Caches responses from the Google Places API to improve performance and speed up response times.
3. Returns cached responses if the same request is made again, thus reducing external API calls.

## Prerequisites

Before you begin using the Nearby Search Application, please make sure you have the following prerequisites in place:

* **Java 8 or Later**: Ensure you have a compatible version of Java installed on your system.
* **Google Places API Key**: Obtain a valid Google Places API key to make requests to the Google Places API.
* **Database**: Prepare an active database (e.g., MySQL, PostgreSQL) where the application can store cached responses.

## Getting Started
### Setting Up the Database

***Due to application.properties file including sensitive information some fields are redacted, follow this section to get setup***

The application requires a database to cache and retrieve responses.
Configure your database connection by specifying your database URL, username, and password in the `application.properties` file:

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Also add your Google Places API key here:

```properties
google.maps.api.key=your_api_key
```

## API Endpoints
The application exposes a single endpoint for nearby location searches:

**GET**  `/api/nearby-locations`
This endpoint allows you to perform nearby location searches by providing longitude, latitude, and radius as request body fields.

Request Parameters:

* **latitude (double)** - The latitude of the target location.
* **longitude (double)** - The longitude of the target location.
* **radius (integer)** - The search radius in meters.

**Example Request Body**:
```json
{
    "latitude" : 41.03,
    "longitude": 29.02,
    "radius" : 100
}
```

**Example Response**:
```json
[
    {
        "latitude": 41.030355,
        "longitude": 29.019527,
        "name": "DEKİMAK ENDÜSTRİ ÜRÜNLERİ TİC.SAN.A.Ş.",
        "rating": 0.0,
        "vicinity": "Paşalimanı Caddesi Paşalimanı Apt.no:38, Üsküdar",
        "type": "point_of_interest"
    },
    {
        "latitude": 41.030606,
        "longitude": 29.019335,
        "name": "Halim Baykuş Anadolu Lisesi",
        "rating": 4.8,
        "vicinity": "Sultantepe, Paşa Limanı Caddesi",
        "type": "school"
    },
    {
        "latitude": 41.0307267,
        "longitude": 29.0199593,
        "name": "Railway Transportation Association of Railway Transport Association",
        "rating": 5.0,
        "vicinity": "Sultantepe, Paşa Limanı Caddesi No:50",
        "type": "point_of_interest"
    },
    {
        "latitude": 41.0303966,
        "longitude": 29.01948,
        "name": "Arada Blue City",
        "rating": 3.8,
        "vicinity": "Paşa Limanı Caddesi",
        "type": "restaurant"
    }
]
```

## Caching Strategy

The Nearby Search Application employs a caching strategy to improve performance. 
Responses from the Google Places API are stored in the database and retrieved when the same request is made again. 
This helps reduce the number of external API calls and speeds up response times as well as reduce cost when using google service.

## Validation

The API validates all incoming requests, whether the information is passed through request parameters or request bodies, using the *Jakarta*, Any values that are considered invalid are reported and are responded to the client with appropriate error messages.

**Example:**

Here is an example of an invalid submission:


```json
{
    "radius" : 100
}
```

**Example Response:**

When the API detects invalid data, it returns an error response to the client, including the current timestamp, an error message, and a detailed list of specific errors:

```json
{
    "time": "2023-10-22T16:03:42.40059",
    "error": "Constraint Validation Failed",
    "errors": {
        "latitude": "must not be null",
        "longitude": "must not be null"
    }
}
```

## Global Exception Handling

To ensure that the API can handle any exceptions that may occur during runtime, a global exception handling mechanism has been implemented in the codebase. 
This mechanism ensures that any unhandled exceptions are caught and dealt with in a standardized manner across the API.

Here is an example that handles all input validation exceptions:

```java
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> methodArgumentNotValidHandler(
      HttpServletRequest req, HttpServletResponse res, MethodArgumentNotValidException e) {

    Map<String, String> errorMap = new HashMap<>();
    e.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
    return new ResponseEntity<>(
        ExceptionResponse.builder()
            .time(LocalDateTime.now())
            .error("Constraint Validation Failed")
            .errors(errorMap)
            .build(),
        HttpStatus.BAD_REQUEST);
  }
```

## License

This project is licensed under the MIT License. Feel free to use, modify, and distribute it according to the terms of the license.
