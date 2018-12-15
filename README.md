# XML-against-XSD-validation-service

Absolutely new revolutional XML against XSD validation service. It's written in Java with use of SpringBoot framework.

## How it works

The service has simple RESTful API. It handles HTTP requests of the following form:
```
Method:          POST
Path:            /validate
Content-Type:    multipart/form-data
-------------------------------------------------------------------
Content-Disposition: form-data; name="xml"; filename="somemxl.xml"
Content-Type: text/plain

content of somefile.xml.
-------------------------------------------------------------------
Content-Disposition: form-data; name="xsd"; filename="somexsd.xsd"
Content-Type: text/plain

content of somexsd.xsd.
-------------------------------------------------------------------
```

and sends responses of the following form:
```
Status-Code:     202 (Accepted)
Content-Type:    application/json;charset=UTF-8

{
  "valid": "true"
}
```

## Tests 
[![Build Status](https://travis-ci.org/lamtev/XML-against-XSD-validation-service.svg?branch=master)](https://travis-ci.org/lamtev/XML-against-XSD-validation-service)

## Code coverage 
[![codecov](https://codecov.io/gh/lamtev/XML-against-XSD-validation-service/branch/master/graph/badge.svg)](https://codecov.io/gh/lamtev/XML-against-XSD-validation-service)

## Usage

### Building from source

You don't need to build from source to use this service, because there is complete jar in [releases](https://github.com/lamtev/XML-against-XSD-validation-service/releases), but if you want, jar can be built with gradle 
 
`$ ./gradlew bootJar`

To start running service use gradle command

`$ ./gradlew runService`

### Docker container (preffered way)

See [Dockerfile](https://github.com/lamtev/XML-against-XSD-validation-service/blob/master/Dockerfile)

Docker container is stored on [hub.docker.com](https://hub.docker.com/r/lamtev/xml-against-xsd-validation-service/), so you can pull latest version with command

`$ docker pull lamtev/xml-against-xsd-validation-service:latest`

Docker container can be run using [launch.sh](https://github.com/lamtev/XML-against-XSD-validation-service/blob/master/launch.sh) bash-script 

`$ ./launch.sh <TCP_port>`

or it can be run manually in manner described in that script

### HTTP-client to quickly play with service

You can use [Postman HTTP-client](https://www.getpostman.com/) for this case. Form a request in a way it formed in figure below:

![Request](/figs/request.png)

Response should looks like:

![Response](/figs/response.png)
