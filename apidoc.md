# Spatial API - SPI docs

## GET GetGeoJSON

` GET http://localhost:8084/spi/GetGeoJSON`

**Description**

Gibt ChronOntology Daten als GeoJSON-T zurück. Dies basiert auf [GeoJSON](http://geojson.org).

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
      "data": {chronontology object},
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

**Response Codes**

* *200 OK* — Everything worked fine.
* *500 Internal Server Error* — Some error on server side.

**Examples**

* http://localhost:8084/spi/GetGeoJSON?id=FD6JS3cmi2Wc
