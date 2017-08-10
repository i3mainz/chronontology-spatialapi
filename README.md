# ChronOntology Spatial API - SPI

[![Build Status](https://travis-ci.org/i3mainz/chronontology-spatialapi.svg?branch=master)](https://travis-ci.org/i3mainz/chronontology-spatialapi)

## Prerequisites

The following components need to be installed:

* maven 3.x
* Netbeans 8.2
* Apache Tomcat 8.0.27.0
* JDK 1.8
* Java EE 7 Web

## Maven

The `spi` web application is build using `maven` as WAR-file.

For details have a look at [pom.xml]((https://github.com/i3mainz/chronontology-spatialapi/blob/master/spi/pom.xml).

[Download](http://maven.apache.org/download.cgi) and  [install]((https://www.mkyong.com/maven/how-to-install-maven-in-windows/) `maven` and [run](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) it.

## Setup

Run git clone https://github.com/i3mainz/chronontology-spatialapi.git to create a local copy of this repository.

Run `mvn install` to install all required dependencies.

Run `mvn clean install site generate-sources javadoc:javadoc` for cleaning, building, testing and generating the documentation files.

Run the war-file as in Tomcat using Netbeans with `Run / Debug` or deploy it to an existing Tomcat. Normally Tomcat will use port 8080.

## Unit tests

Running `mvn test` will run the unit tests with `JUnit`.

## API (deprecated)

### Geo Widget (GeoJSON-T)

```
GET http://localhost:8084/spi/GetGeoJSONT

demo: http://localhost:8084/spi/GetGeoJSONT?id=FD6JS3cmi2Wc

param: id [String]

id: ChronOntology ID (e.g. FD6JS3cmi2Wc from http://chronontology.dainst.org/period/FD6JS3cmi2Wc)

Content-Type: application/json
```

### Gazetter Compare (GeoJSON)

```
GET http://localhost:8084/spi/gazetteercompare

demo: http://localhost:8084/spi/gazetteercompare?lat=49.9987&lon=8.27399&name=Mainzer%20Dom

param: lat [Double], lon [Double], name [String]

lat: latutude of center point (e.g. 49.9987)
lon: longitute of center point (e.g. 8.27399)
name: string for comparison (e.g. Mainzer Dom)

Content-Type: application/json
```

### Gazetter Lookup (GeoJSON)

```
GET http://localhost:8084/spi/gazetteerlookup

param: upperleft [String], lowerleft [String], upperright [String], lowerright [String]

demo: http://localhost:8084/spi/gazetteerlookup?upperleft=50.082665;8.161050&lowerleft=50.082665;8.371850&upperright=49.903887;8.161050&lowerright=49.903887;8.371850

upperleft: lat/lon of the upper left corner (e.g. 50.082665;8.161050)
lowerleft: lat/lon of the upper left corner (e.g. 50.082665;8.371850)
upperright: lat/lon of the upper left corner (e.g. 49.903887;8.161050)
lowerright: lat/lon of the upper left corner (e.g. 49.903887;8.371850)

Content-Type: application/json
```

## Credits

Florian Thiery M.Sc. (i3mainz - Institut für Raumbezogene Informations- und Messtechnik)
