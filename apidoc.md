# Spatial API - SPI docs

## GET GetGeoJSON

` GET http://localhost:8084/spi/GetGeoJSON`

**Description**

Gibt ChronOntology Daten als GeoJSON zurück. Dies basiert auf [GeoJSON](http://geojson.org).

**Requires authentication**

none

**Parameters**

* **id** *(mendatory)* — [String] Chronontology ID.

**Headers**

`Accept: application/json;charset=UTF-8`

**Return format**

Ein GeoJSON Objekt.

**Response**

```json
{
	"type": "FeatureCollection",
	"features": [{
		"type": "Feature",
	  "geometry": {},
		"properties": {
      "id": "",
      "name": "",
      "relation": "",
      "uri": "",
      "chronontology": {chronontology object},
      "parentGeometry": {
        "id": "",
        "name": "",
        "uri": ""
      }
    }
	}]
}
```

*Auswahl*

* **type** *(DEFAULT)* — [String] hier: FeatureCollection.
* **features** *(DEFAULT)* — [JSONArray] Geo Features.
* **properties** *(DEFAULT)* — [JSONObject] standardisierte Werte zur Visualisierung.
* **parentGeometry** *(optional)* — [JSONObject] Information, wenn Geometrie vererbt wurde.

**Response Codes**

* *200 OK* — Everything worked fine.
* *500 Internal Server Error* — Some error on server side.

**Examples**

* http://localhost:8084/spi/GetGeoJSON?id=FD6JS3cmi2Wc

## GET GetGeoJSON Dummy

` GET http://localhost:8084/spi/GetDummy`

**Description**

Gibt ChronOntology Daten als GeoJSON zurück. Dies basiert auf [GeoJSON](http://geojson.org).

**Requires authentication**

none

**Parameters**

* **multi** *(optional)* — [boolean] if set, more than one geom is in output.

**Headers**

`Accept: application/json;charset=UTF-8`

**Return format**

Ein GeoJSON Objekt.

**Response**

```json
{
	"type": "FeatureCollection",
	"features": [{
		"type": "Feature",
	  "geometry": {},
		"properties": {
      "periodid": "",
      "names": { "language": ["name1", "name2"] },
			"chronontology": {chronontology object},
			"@id": "uri",
      "relation": ""
    }
	}]
}
```

**Response Codes**

* *200 OK* — Everything worked fine.
* *500 Internal Server Error* — Some error on server side.

**Examples**

* http://localhost:8084/spi/GetGeoJSON?multi=true

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
