# Geo Components

## Widgets

### GeoMapComponent

Stellt die räumliche Verteilung einer ChronOntology Period dar.

* controller in `js/components/GeoMapComponent.js`
* template at `partials/geo/map.html`
* used in `partials/period.html`
* `<geomap selected-period-id="document.resource.id"></geomap>`

**JavaScript libs**

*package.json + gulpfile.js["jsDeps"]*

```json
"dependencies": {
  "leaflet": "0.7.7",
  "leaflet-fullscreen": "1.0.2",
  "leaflet-geodesy": "0.2.1",
  "turf": "3.0.14"
}
```

*scss/geo/*

```
* geomap.scss
* leaflet.scss
* leaflet-fullscreen.scss
```

### GeoSearchComponent

Ermöglicht die Suche in einem Gazetteer in einer Bounding Box.

* controller in `js/components/GeoSearchComponent.js`
* template at `partials/geo/search.html`
* used in `partials/period.html`
* `<geosearch datasource="getty"></geosearch>`

**JavaScript libs**

*package.json + gulpfile.js["jsDeps"]*

```json
"dependencies": {
  "leaflet": "0.7.7",
  "leaflet-fullscreen": "1.0.2",
  "leaflet-draw": "0.4.12",
  "leaflet.markercluster": "0.5.0"
}
```

*scss/geo/*

```
* geomap.scss
* leaflet.scss
* leaflet-fullscreen.scss
* leaflet.draw.scss
* MarkerCluster.scss
* MarkerCluster.Default.scss
```

#### TODO

* load URL on popup link click

### GeoSearchResultsComponent

Zeit die Ergebnisse einer Suche (q,bbox) in einer Map an.

* controller in `js/components/GeoSearchResultsComponent.js`
* template at `partials/geo/searchResults.html`
* used in `partials/period.html`
* `<geosearchresults datasource="/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=dai"></geosearchresults>`

**JavaScript libs**

*package.json + gulpfile.js["jsDeps"]*

```json
"dependencies": {
  "leaflet": "0.7.7",
  "leaflet-fullscreen": "1.0.2",
  "leaflet-search": "2.3.7",
  "leaflet.markercluster": "0.5.0"
}
```

*scss/geo/*

```
* geomap.scss
* leaflet.scss
* leaflet-fullscreen.scss
* leaflet-search.src.scss
* MarkerCluster.scss
* MarkerCluster.Default.scss
```

#### TODO

* load URL on popup link click

### GeoSearchResultsListComponent

Zeit die Ergebnisse einer Suche (q,bbox) in einer Tabelle an.

* controller in `js/components/GeoSearchResultsListComponent.js`
* template at `partials/geo/searchResultsList.html`
* used in `partials/period.html`
* `<geosearchresultslist datasource="/spi/place?q=Mainz&type=getty"></geosearchresultslist>`

**JavaScript libs**

*package.json + gulpfile.js["jsDeps"]*

```json
"dependencies": {
  "tablesort": "5.0.2"
}
```

*scss/geo/*

```
* geomap.scss
* tablesort.scss
```

#### TODO

* load URL on name click for frontend

## Picker

### GeoPickerResourceComponent

* Request-Auswahl
  * Typ
  * Gazetteer ID
* Response
  * GeoSearchResultsListComponent
     * URI in frontend
* `<geopickerresource on-place-selected="$ctrl.pickedLocations[relationName].push(place)"></geopickerresource>`

**Angular Components**

* GeoSearchResultsListComponent

#### TODO

* load GeoSearchResultsListComponent with datasource on button click

### GeoPickerComponent

* Request-Auswahl
 * Typ
 * query string
* Response
 * GeoSearchResultsListComponent
   * URI in frontend
* `<geopicker on-place-selected="$ctrl.pickedLocations[relationName].push(place)"></geopicker>`

**Angular Components**

* GeoSearchResultsListComponent

#### TODO

* load GeoSearchResultsListComponent with datasource on button click

### GeoPickerMapComponent

* Request-Auswahl
  * Typ
  * bbox
* Response
  * GeoSearchResultsComponent
   *  URI in frontend
* `<geopickermap on-place-selected="$ctrl.pickedLocations[relationName].push(place)"></geopickermap>`

**Angular Components**

* GeoSearchResultsComponent

#### TODO

* load GeoSearchResultsComponent with datasource on button click
