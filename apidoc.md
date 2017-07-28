# Spatial API - SPI docs

## GET GetGeoJSON-T

` GET http://localhost:8084/spi/GetGeoJSONT`

**Description**

Gibt ChronOntology Daten als GeoJSON-T zurück. Dies basiert auf [GeoJSON](http://geojson.org) und [GeoJSON-T](https://github.com/kgeographer/geojson-t).

**Requires authentication**

none

**Parameters**

* **id** *(mendatory)* — [String] Chronontology ID.

**Headers**

`Accept: application/json;charset=UTF-8`

**Return format**

Ein GeoJSON-T Objekt.

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
      "data": {chronontology object},
      "parentGeometry": {
        "id": "",
        "name": "",
        "uri": ""
      }
    },
	  "id": "",
		"when": {coming soon}
	}]
}
```

*Auswahl*

* **type** *(DEFAULT)* — [String] hier: FeatureCollection.
* **features** *(DEFAULT)* — [JSONArray] Geo Features.
* **properties** *(DEFAULT)* — [JSONObject] standardisierte Werte zur Visualisierung.
* **when** *(DEFAULT)* — [JSONObject] Teil aus der GeoJSON-T Spezifikation.

**Response Codes**

* *200 OK* — Everything worked fine.
* *500 Internal Server Error* — Some error on server side.

**Examples**

* http://localhost:8084/spi/GetGeoJSONT?id=FD6JS3cmi2Wc
