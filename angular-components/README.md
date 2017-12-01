# Angular Geo Components

Diese Datei beschreibt die Nutzung der `spatial API` in Angular Components des [ChronOntology Frontends](https://github.com/dainst/chronontology-frontend). Alle Pfade verhalten sich realtiv zum Frontend Repository. Die Geo Components bestehen aus zwei Teilen, Core Components und Picker.

## Core Components

### GeoMapComponent

Stellt die räumliche Verteilung einer ChronOntology Periode dar.

* controller in `js/components/GeoMapComponent.js`
* template at `partials/geo/map.html`
* used in `partials/period.html`
* tag: `<geomap></geomap>`
* attribute: `selected-period-id="periodID"`

**component example**

```xml
<geomap selected-period-id="document.resource.id"></geomap>
```

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
* used in `partials/geo/pickerMap.html`
* tag: `<geosearch></geosearch>`
* attribute: `datasource="gazetterType"`

**component example**

```xml
<geosearch datasource="getty"></geosearch>
```

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

### GeoSearchResultsMapComponent

Zeit die Ergebnisse einer Suche (q,bbox) in einer Map an.

* controller in `js/components/GeoSearchResultsMapComponent.js`
* template at `partials/geo/searchResultsMap.html`
* used in
 * `partials/geo/pickerResource.html`
 * `partials/geo/pickerQuery.html`
 * `partials/geo/pickerMap.html`
* tag: `<geosearchresultsmap></geosearchresultsmap>`
* attribute: `datasource="SPI-URI{bbox,q,resource}"`

 **component example**

 ```xml
<geosearchresultsmap datasource="/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=dai"></geosearchresultsmap>
<geosearchresultsmap datasource="/spi/place?q=Mainz&type=getty"></geosearchresultsmap>
<geosearchresultsmap datasource="/spi/place/geonames/2874225"></geosearchresultsmap>
 ```

**JavaScript libs**

*package.json + gulpfile.js["jsDeps"]*

```json
"dependencies": {
  "leaflet": "0.7.7",
  "leaflet-fullscreen": "1.0.2",
  "leaflet-search": "2.3.7",
  "leaflet.markercluster": "0.5.0",
  "leaflet-minimap": "3.6.0"
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
* Control.MiniMap.min.scss
```

### GeoSearchResultsListComponent

Zeit die Ergebnisse einer Suche (q,bbox) in einer Tabelle an.

* controller in `js/components/GeoSearchResultsListComponent.js`
* template at `partials/geo/searchResultsList.html`
* used in
 * `partials/geo/pickerResource.html`
 * `partials/geo/pickerQuery.html`
 * `partials/geo/pickerMap.html`
* tag: `<geosearchresultslist></geosearchresultslist>`
* attribute: `datasource="SPI-URI{bbox,q,resource}"`

 **component example**

 ```xml
 <geosearchresultslist datasource="/spi/place?bbox=50.082665;8.161050;50.082665;8.371850;49.903887;8.161050;49.903887;8.371850&type=dai"></geosearchresultslist>
 <geosearchresultslist datasource="/spi/place?q=Mainz&type=getty"></geosearchresultslist>
 <geosearchresultslist datasource="/spi/place/geonames/2874225"></geosearchresultslist>
 ```

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

## Picker

Picker dienen dazu, dass ein Nutzer durch eine bestmmtes Auswahlverfahren eine URI eines Gazetters erhalten kann.

### GeoPickerResourceComponent

* controller in `js/components/GeoPickerResourceComponent.js`
* template at `partials/geo/pickerResource.html`
* expected in `partials/period/edit.html`
* tag: `<geopickerresource></geopickerresource>`
* attribute: `on-place-selected="*"`

**picker example**

```xml
<geopickerresource on-place-selected="testValue = place"></geopickerresource>
```

**Angular Components**

* GeoSearchResultsListComponent
* GeoSearchResultsMapComponent

### GeoPickerQueryComponent

* controller in `js/components/GeoPickerQueryComponent.js`
* template at `partials/geo/pickerQuery.html`
* expected in `partials/period/edit.html`
* tag: `<geopickerquery></geopickerquery>`
* attribute: `on-place-selected="*"`

**picker example**

```xml
<geopickerquery on-place-selected="testValue2 = place"></geopickerquery>
```

**Angular Components**

* GeoSearchResultsListComponent
* GeoSearchResultsMapComponent

### GeoPickerMapComponent

* controller in `js/components/GeoPickerMapComponent.js`
* template at `partials/geo/pickerMap.html`
* expected in `partials/period/edit.html`
* tag: `<geopickermap></geopickermap>`
* attribute: `on-place-selected="*"`

**picker example**

```xml
<geopickermap on-place-selected="testValue3 = place"></geopickermap>
```

**Angular Components**

* GeoSearchComponent
* GeoSearchResultsListComponent
* GeoSearchResultsMapComponent
