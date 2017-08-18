# *Spatial API - JSON Data Model*

## Inhaltsverzeichnis

* [Gazetteer GeoJSON](#gazetteer-geojson)
* [ChronOntology GeoJSON](#chronontology-geojson)
* [Names JSON](#names-json)

## Gazetteer GeoJSON

GeoJSON das Gazetter Suchergebnisse enthält.

**Struktur**

```json
{
  "type": "Feature",
  "geometry": {},
  "properties": {
    "@id": "",
    "gazetteerid": "",
    "gazetteertype": "",
    "names": {}
  }
}
```

* **type** *(DEFAULT)* — [String] GeoJSON "Feature".
* **geometry** *(DEFAULT)* — [JSONObject] GeoJSON "geometry".
* **properties** *(DEFAULT)* — [JSONObject] GeoJSON "properties".
  * **@id** *(DEFAULT)* — [String] URI zur Gazetteer Ressource.
  * **gazetteerid** *(DEFAULT)* — [String] Gazetteer Ressource ID.
  * **gazetteertype** *(DEFAULT)* — [String] Gazetteer Typ: dai, getty, geonames, pleiades.
  * **names** *(DEFAULT)* — [JSONObject] siehe [Names JSON](#names-json).

**Beispiel**

* http://localhost:8084/spi/geojson/g

## ChronOntology GeoJSON

GeoJSON einer ChronOntology Ressource.

**Struktur**

```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {},
      "properties": {
        "@id": "",
        "gazetteerid": "",
        "gazetteertype": "",
        "gazetteerrelation": ""
      }
    }
  ],
  "metadata": {
    "@id": "",
    "periodid": "",
    "chronontology": {},
    "names": {},
    "when": []
  }
}
```

* **type** *(DEFAULT)* — [String] GeoJSON "Feature".
* **features** *(DEFAULT)* — [JSONObject] GeoJSON "geometry".
  * **type** *(DEFAULT)* — [String] GeoJSON "Feature".
  * **geometry** *(DEFAULT)* — [JSONObject] GeoJSON "geometry".
  * **properties** *(DEFAULT)* — [JSONObject] GeoJSON "properties".
    * **@id** *(DEFAULT)* — [String] URI zur Gazetteer Ressource.
    * **gazetteerid** *(DEFAULT)* — [String] Gazetteer Ressource ID.
    * **gazetteertype** *(DEFAULT)* — [String] Gazetteer Typ: dai, getty, geonames, pleiades.
    * **gazetteerrelation** *(DEFAULT)* — [String] [ChronOntology Relation](https://github.com/dainst/chronontology-data/blob/master/docs/ChronOntology%20data%20model.md#12-connections-to-the-gazetteer).
* **metadata** *(DEFAULT)* — [JSONObject] Metadaten.
  * **@id** *(DEFAULT)* — [String] URI zur ChronOntology Ressource.
  * **periodid** *(DEFAULT)* — [String] ChronOntology Ressource ID.
  * **chronontology** *(DEFAULT)* — [JSONObject] [ChronOntology Object](https://github.com/dainst/chronontology-data/blob/master/docs/ChronOntology%20data%20model.md#the-chronontology-data-model).
  * **names** *(DEFAULT)* — [JSONObject] [ChronOntology Names Object](https://github.com/dainst/chronontology-data/blob/master/docs/ChronOntology%20data%20model.md#names).
  * **when** *(DEFAULT)* — [JSONArray] [ChronOntology TimeSpan](https://github.com/dainst/chronontology-data/blob/master/docs/ChronOntology%20data%20model.md#timespan-fields).

**Beispiel**

* http://localhost:8084/spi/geojson/c

## Names JSON

JSONObject nach ChronOntology [names object](https://github.com/dainst/chronontology-data/blob/master/docs/ChronOntology%20data%20model.md#names).

**Beispiel**

```json
{
  "de": ["Römisch", "römisch"],
  "en": ["greek", "Greek"]
}
```
