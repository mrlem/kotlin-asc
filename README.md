# Kasc

Kasc is a Kotlin parser for [Esri ASCII raster](https://desktop.arcgis.com/fr/arcmap/latest/manage-data/raster-and-images/esri-ascii-raster-format.htm) files, commonly used in <abbr title="Geographical Information Systems">GIS</abbr>.

## Usage

Import the lib:

```kotlin
dependencies {
    implementation("org.mrlem.kasc:kask:1.0")
}
```

Read some data:

```kotlin
import org.mrlem.kask.Parser

...

val raster = Parser.fromResource("sample.asc")
    .parse()
val cellValue = raster[x, y]
```

## About

* Author: Sébastien Guillemin
* License: GPLv3