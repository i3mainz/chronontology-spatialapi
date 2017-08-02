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
