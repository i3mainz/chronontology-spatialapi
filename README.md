# ChronOntology Spatial API - SPI

## Prerequisites

The following components need to be installed:

* Netbeans
* Tomcat 7.x
* Java 7.x
* Maven

## Development Hints

The project was developed in NetBeans IDE 8.2, using Java EE 7 Web, JDK 1.7 and Maven 4.0.

## Installation of Dependencies

Install development and production dependencies using Netbeans and Maven. Open the project in Netbeans.
```
Build with Dependencies
```

## Running the development server

In order to run the spatial API use the following command in Netbeans.
```
Run / Debug
```
Normally Tomcat will use port 8080, accessivle at localhost:8080.

## Deployment

Build the application using the following command in Netbeans.
```
Build
```

After building, the psatialapi lies inside the target directory as war-file.

## Tools

### Time Concept Search

http://localhost:8080/spatialapi/tools/timeconceptSearch

[demo](http://chronontology.i3mainz.hs-mainz.de/spatialapi/tools/timeconceptSearch/)

### Gazetteer Search

http://localhost:8080/spatialapi/tools/gazetteerSearch [demo](http://chronontology.i3mainz.hs-mainz.de/spatialapi/tools/gazetteerSearch/)

### Gazetteer Compare (Map)

http://localhost:8080/spatialapi/tools/gazetteerCompare

[demo](http://chronontology.i3mainz.hs-mainz.de/spatialapi/tools/gazetteerCompare/)

### Gazetteer Compare (Table)

http://localhost:8080/spatialapi/tools/gazetteerCompareTable

[demo](http://chronontology.i3mainz.hs-mainz.de/spatialapi/tools/gazetteerCompareTable/)

## API

### Geo Widget (GeoJSON)

```
GET http://localhost:8084/spatialapi/geowidget

demo: http://localhost:8084/spatialapi/geowidget?uri=http://chronontology.dainst.org/period/FD6JS3cmi2Wc

param: uri [String]

uri: ChronOntology URI (e.g. http://chronontology.dainst.org/period/FD6JS3cmi2Wc)

Content-Type: application/json
```

### Gazetter Compare (GeoJSON)

```
GET http://localhost:8084/spatialapi/gazetteercompare

demo: http://localhost:8084/spatialapi/gazetteercompare?lat=49.9987&lon=8.27399&name=Mainzer%20Dom

param: lat [Double], lon [Double], name [String]

lat: latutude of center point (e.g. 49.9987)
lon: longitute of center point (e.g. 8.27399)
name: string for comparison (e.g. Mainzer Dom)

Content-Type: application/json
```

### Gazetter Lookup (GeoJSON)

```
GET http://localhost:8084/spatialapi/gazetteerlookup

param: upperleft [String], lowerleft [String], upperright [String], lowerright [String]

demo: http://localhost:8084/spatialapi/gazetteerlookup?upperleft=50.082665;8.161050&lowerleft=50.082665;8.371850&upperright=49.903887;8.161050&lowerright=49.903887;8.371850

upperleft: lat/lon of the upper left corner (e.g. 50.082665;8.161050)
lowerleft: lat/lon of the upper left corner (e.g. 50.082665;8.371850)
upperright: lat/lon of the upper left corner (e.g. 49.903887;8.161050)
lowerright: lat/lon of the upper left corner (e.g. 49.903887;8.371850)

Content-Type: application/json
```

### World JSON (GeoJSON)

```
GET http://localhost:8084/spatialapi/world

Content-Type: application/json
```

## Credits

Florian Thiery M.Sc. (i3mainz - Institut für Raumbezogene Informations- und Messtechnik)
