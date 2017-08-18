# *Spatial API - apidoc*

## Inhaltsverzeichnis

* [GET status](#get-status)
* [GET place by searching](#get-place-by-searching)
* [GET gazetteer place](#get-gazetteer-place)
* [GET tools gazetteerlookup](#get-tools-gazetteercompare-will-be-replaced-by-placesbbox)
* [GET tools gazetteercompare](#get-tools-gazetteerlookup-will-be-replaced-by-placesbbox)

## GET status

` GET http://localhost:8084/spi/`

**Description**

Gibt Metadaten der API inkl. Maven-Einstellungen zurück.

**Requires authentication**

none

**Parameters**

none

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein JSON Objekt.

**Response**

```json
{
	"maven": {
		"modelVersion": ""
	},
	"project": {
		"buildRepository": "",
		"buildNumberShort": "",
		"groupId": "",
		"name": "",
		"description": "",
		"artifactId": "",
		"packaging": "",
		"encoding": "",
		"buildNumber": "",
		"version": "",
		"url": ""
	}
}
```

*Auswahl*

* **maven** *(DEFAULT)* — [JSONObject] Metadaten der Maven Konfiguration.
* **project** *(DEFAULT)* — [JSONObject] Metadaten des Projekts.

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* http://localhost:8084/spi/

## GET place by searching

` GET http://localhost:8084/spi/place`

**Description**

Gibt Ortsdaten als GeoJSON zurück. Dies basiert auf [GeoJSON](http://geojson.org).

**Requires authentication**

none

**Parameters**

* **periodid** *(optional)* — [String] Chronontology ID.
* **bbox** *(optional)* — [String] BoundingBox. *not yet implemented*
* **q** *(optional)* — [String] Buchstabenfolge. *not yet implemented*
* **type** *(dummy mandatory && type optional)* — [String] cjson / gjson. *not yet implemented*

* *BoundingBox (Beispiel):*
  * bbox=upperleft,lowerleft,upperright,lowerright
	* upperleft=50.082665;8.161050
	* lowerleft=50.082665;8.371850
	* upperright=49.903887;8.161050
	* lowerright=49.903887;8.371850.

* => bbox=50.082665;8.161050,50.082665;8.371850,49.903887;8.161050,49.903887;8.371850

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein GeoJSON Objekt.

**Response**

```json
{
	"type": "FeatureCollection",
	"features": []
}
```

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* http://localhost:8084/spi/place?periodid=FD6JS3cmi2Wc
* http://localhost:8084/spi/place?bbox=50.082665;8.161050,50.082665;8.371850,49.903887;8.161050,49.903887;8.371850
* http://localhost:8084/spi/place?q=Mainz
* http://localhost:8084/spi/place?dummy=FeatureCollection&type=cjson
* http://localhost:8084/spi/place?dummy=Feature&type=gjson

## GET gazetteer place

` GET http://localhost:8084/spi/place/:type/:id`

**Description**

Gibt Gazetterdaten als GeoJSON zurück. Dies basiert auf [GeoJSON](http://geojson.org).

**Requires authentication**

none

**Parameters**

* **type** *(mandatory)* — [String] dai/getty/geonames/pleiades. *not yet implemented*
* **id** *(mandatory)* — [String] gazetteer id. *not yet implemented*

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein GeoJSON Objekt.

**Response**

```json
{
	"type": "type",
	"id": "id"
}
```

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* http://localhost:8084/spi/place/dai/001

## GET tools gazetteercompare *will be replaced by /places?bbox=*

` GET http://localhost:8084/spi/tools/gazetteercompare`

**Description**

Gibt Gazetterdaten als GeoJSON zurück. Dies basiert auf [GeoJSON](http://geojson.org). Die Daten werden in einer definierten Boundingbox um lat/lon in den Gazetteers gesucht und zu allen Orten und Bezeichnungen die räumliche und sprachile Distanz berechnet.

**Requires authentication**

none

**Parameters**

* **lat** *(mandatory)* — [String] Latitude.
* **lon** *(mandatory)* — [String] Longitude.
* **name** *(mandatory)* — [String] Ortsbezeichnung.

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein GeoJSON Objekt.

**Response**

```json
{
	"features": [{
			"geometry": {
				"coordinates": [
					8.27399,
					49.9987
				],
				"type": "Point"
			},
			"type": "Feature",
			"properties": {
				"name": "Mainzer Dom",
				"type": "startpoint"
			}
		},
		{
			"geometry": {
				"coordinates": [
					[
						[
							8.323989999999998,
							49.9487
						],
						[
							8.323989999999998,
							50.048700000000004
						],
						[
							8.223989999999999,
							50.048700000000004
						],
						[
							8.223989999999999,
							49.9487
						],
						[
							8.323989999999998,
							49.9487
						]
					]
				],
				"type": "Polygon"
			},
			"type": "Feature",
			"properties": {
				"type": "boundingbox"
			}
		},
		{
			"geometry": {},
			"type": "Feature",
			"properties": {
				"provenance": "gettytgn",
				"distance": 1.867,
				"similarity": {
					"levenshtein": 7,
					"dameraulevenshtein": 7,
					"jarowinkler": 0.87,
					"normalizedlevenshtein": 0.64
				},
				"name": "Main",
				"uri": "http://vocab.getty.edu/tgn/7012591"
			}
		}
	],
	"elements": {
		"daiGazetteer": 1,
		"geonames": 19,
		"gettyTGN": 33
	},
	"place": {
		"name": "Mainzer Dom",
		"lon": 8.27399,
		"lat": 49.9987
	},
	"type": "FeatureCollection"
}
```

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* http://localhost:8084/spi/tools/gazetteercompare?lat=49.9987&lon=8.27399&name=Mainzer%20Dom

## GET tools gazetteerlookup *will be replaced by /places?bbox=*

` GET http://localhost:8084/spi/tools/gazetteerlookup`

**Description**

Gibt Gazetterdaten als GeoJSON zurück. Dies basiert auf [GeoJSON](http://geojson.org). Die Daten werden in einer definierten Boundingbox gesucht.

**Requires authentication**

none

**Parameters**

* **upperleft** *(mandatory)* — [String] lat/lon of the upper left corner (e.g. 50.082665;8.161050).
* **upperright** *(mandatory)* — [String] lat/lon of the upper left corner (e.g. 50.082665;8.371850).
* **upperright** *(mandatory)* — [String] lat/lon of the upper left corner (e.g. 49.903887;8.161050).
* **lowerright** *(mandatory)* — [String]lat/lon of the upper left corner (e.g. 49.903887;8.371850).

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein GeoJSON Objekt.

**Response**

```json
{
	"features": [{
			"geometry": {
				"coordinates": [
					[
						[
							8.16105,
							50.082665
						],
						[
							8.16105,
							49.903887
						],
						[
							8.37185,
							49.903887
						],
						[
							8.37185,
							50.082665
						],
						[
							8.16105,
							50.082665
						]
					]
				],
				"type": "Polygon"
			},
			"type": "Feature",
			"properties": {
				"type": "boundingbox"
			}
		},
		{
			"geometry": {
				"coordinates": [
					8.216667,
					50
				],
				"type": "Point"
			},
			"type": "Feature",
			"properties": {
				"provenance": "gettytgn",
				"name": "Gonsenheim",
				"uri": "http://vocab.getty.edu/tgn/7185618"
			}
		}
	],
	"elements": {
		"daiGazetteer": 1,
		"geonames": 64,
		"gettyTGN": 90
	},
	"type": "FeatureCollection"
}
```

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* http://localhost:8084/spi/tools/gazetteerlookup?upperleft=50.082665;8.161050&lowerleft=50.082665;8.371850&upperright=49.903887;8.161050&lowerright=49.903887;8.371850
