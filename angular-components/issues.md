# Geo Angular Components issues

## GeoSearchResultsMapComponent
* Minimap zerstört Tily Layout. Hier müsste wohl der View einmal neu geladen werden?!
* Reload von MarkerCluster funktioniert nicht richtig. Bei einer neuen Suchanfrage sind immer noch zum Teil ein Teil der alten Daten vorhanden. Timout Fehler?! Hier müsste wohl der View einmal neu geladen werden?!

## GeoPickerMapComponent
* Listenansicht funktioniert noch nicht. Wenn BoundingBox geladen, müsste die Liste mit den BBox Parametern initialisiert werden. Bei einem Klick auf die Weltkugel müsste dann ein Aufruf der GeoSearchResultsMapComponent erfolgen. Bei Klick auf den Resetknopf müsste der View komplett neu, leer geladen werden.
