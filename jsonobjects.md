# *Spatial API - JSON Data Model*

## Inhaltsverzeichnis

* [Gazetteer GeoJSON](#gazetteer-geojson)
 * [Gazetteer Ressource](##gazetteer-ressource)
 * [Gazetteer Suche](##gazetteer-suche)
* [ChronOntology GeoJSON](#chronontology-geojson)
* [Names JSON](#names-json)
* [Similarity JSON](#similarity-json)
 * [Point Distance](#point-distance)
 * [String Distance](#string-distance)

## Gazetteer GeoJSON

### Gazetteer Ressource

GeoJSON einer Gazetteer Ressource

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

### Gazetteer Suche

GeoJSON einer Gazetteer Suche.

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
        "names": {},
        "similarity": {}
      }
    }
  ],
  "metadata": {
    "gazetteertype": "",
    "searchstring": "",
    "upperleftLon": "",
    "lowerleftLon": "",
    "lowerrightLat": "",
    "upperrightLon": "",
    "upperleftLat": "",
    "lowerleftLat": "",
    "lowerrightLon": "",
    "upperrightLat": ""
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
  * **similarity** *(DEFAULT)* — [JSONObject] siehe [Similarity JSON](#similarity-json).
* **metadata** *(DEFAULT)* — [JSONObject] Metadaten zur Suche.
  * **gazetteertype** *(DEFAULT)* — [String] Gazetteer Typ: dai, getty, geonames, pleiades.
  * **searchstring** *(OPTIONAL)* — [String] String für Suche.
  * **upperleftLon**, **lowerleftLon**, **lowerrightLat**, **upperrightLon**, **upperleftLat**, **lowerleftLat**, **lowerrightLon**, **upperrightLat**, *(OPTIONAL)* — [Double] BoundingBox Koordinaten.

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

## Names JSON

JSONObject nach ChronOntology [names object](https://github.com/dainst/chronontology-data/blob/master/docs/ChronOntology%20data%20model.md#names).

```json
{
  "language1": [],
  "language2": []
}
```
* **language** *(DEFAULT)* — [JSONArray] Sprachvarianten.

**Beispiel**

```json
{
  "de": ["Römisch", "römisch"],
  "en": ["greek", "Greek"]
}
```

## Similarity JSON

Das Similarity JSON kann aus zwei verschiendenen Varianten bestehen. Beim Vergleich der Zeichenkette werden verschiedene Distanzen berechnet, bei einer Suche mit der Bounding Box die Distanz zum Zentrum der BBox.

### Point Distance

```json
{
	"distance": "",
	"bboxcenter": ["", ""],
	"point": ["", ""]
}
```

* **distance** *(DEFAULT)* — [Double] Errechnete Distanz in Kilometern zwischen bboxcenter und Point.
* **bboxcenter** *(DEFAULT)* — [JSONArray] lon, lat des Mittelpunkts der Bounding Box.
* **point** *(DEFAULT)* — [String] lon, lat eines Punktes in der Bounding Box.

**Beispiel**

```json
{
	"distance": 1.36,
	"bboxcenter": [8.266449999999999, 49.993275999999994],
	"point": [8.2791, 49.98419]
}
```

### String Distance

```json
{
  "searchString": "",
  "gazetteerString": "",
  "levenshtein": "",
  "normalizedlevenshtein": "",
  "dameraulevenshtein": "",
  "jarowinkler": ""
}
```

* **searchString** *(DEFAULT)* — [String] String nach dem gesucht werden soll.
* **gazetteerString** *(DEFAULT)* — [String] Resultierender String aus dem Gazetteer Suchergebnis.
* **levenshtein** *(DEFAULT)* — [Double] [Levenshtein Distanz](https://github.com/tdebatty/java-string-similarity#levenshtein).
* **normalizedlevenshtein** *(DEFAULT)* — [Double] [Normalisierte Levenshtein Distanz](https://github.com/tdebatty/java-string-similarity#normalized-levenshtein).
* **dameraulevenshtein** *(DEFAULT)* — [Double] [Damerau-Levenshtein Distanz](https://github.com/tdebatty/java-string-similarity#damerau-levenshtein).
* **jarowinkler** *(DEFAULT)* — [Double] [Jaro-Winkler Distanz](https://github.com/tdebatty/java-string-similarity#jaro-winkler).


**Beispiel**

```json
{
  "searchString": "Mainz",
  "gazetteerString": "Wiesbaden-Mainz-Kastel",
  "levenshtein": 17.0,
  "dameraulevenshtein": 17.0,
  "jarowinkler": 0.68,
  "normalizedlevenshtein": 0.77
}
```
