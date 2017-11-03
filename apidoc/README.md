# *Spatial API - apidoc*

## Inhaltsverzeichnis

* [GET status](#get-status)
* [GET place by searching](#get-place-by-searching)
* [GET gazetteer place](#get-gazetteer-place)

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
* **bbox** *(optional)* — [String] BoundingBox.
* **q** *(optional)* — [String] Buchstabenfolge.
* **type** *(optional, mandatory for "q" and "bbox")* — [String] {dai;getty;geonames;pleiades}.

**Beispiel Bounding Box**

* bbox=upperleft;lowerleft;upperright;lowerright
	* upperleft=50.082665;8.161050
	* lowerleft=50.082665;8.371850
	* upperright=49.903887;8.161050
	* lowerright=49.903887;8.371850
* => bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein GeoJSON+ Objekt, siehe [Gazetteer Suche Dokumentation](https://github.com/linkedgeodesy/geojson-plus/blob/master/datamodel.md#gazetteer-suche).

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* periodid
 * http://localhost:8084/spi/place?periodid=EfFq8qCFODK8
* bbox
 * http://localhost:8084/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=dai
 * http://localhost:8084/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=geonames
 * http://localhost:8084/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=getty
 * http://localhost:8084/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=pleiades
* q
 * http://localhost:8084/spi/place?q=Mainz&type=dai
 * http://localhost:8084/spi/place?q=Mainz&type=geonames
 * http://localhost:8084/spi/place?q=Mainz&type=getty
 * http://localhost:8084/spi/place?q=Mainz&type=pleiades

## GET gazetteer place

` GET http://localhost:8084/spi/place/:type/:id`

**Description**

Gibt Gazetterdaten als GeoJSON zurück. Dies basiert auf [GeoJSON+](https://github.com/linkedgeodesy/geojson-plus/blob/master/datamodel.md).

**Requires authentication**

none

**Parameters**

* **type** *(mandatory)* — [String] {dai;getty;geonames;pleiades;chronontology}.
* **id** *(mandatory)* — [String] gazetteer id.

**Headers**

`Accept: application/json;charset=UTF-8`

`Accept-Encoding: *` `Accept-Encoding: gzip`

**Return format**

Ein GeoJSON+ Objekt, siehe [Gazetteer GeoJSON Dokumentation](https://github.com/linkedgeodesy/geojson-plus/blob/master/datamodel.md#gazetteer-ressource) und [ChronOntology GeoJSON Dokumentation](https://github.com/linkedgeodesy/geojson-plus/blob/master/datamodel.md#chronontology-geojson).

**Response Codes**

* *200 OK* — Alles ok.
* *500 Internal Server Error* — Serverfehler.

**Examples**

* http://localhost:8084/spi/place/dai/2181124
* http://localhost:8084/spi/place/geonames/2874225
* http://localhost:8084/spi/place/getty/7004449
* http://localhost:8084/spi/place/pleiades/109169
* http://localhost:8084/spi/place/chronontology/EfFq8qCFODK8
