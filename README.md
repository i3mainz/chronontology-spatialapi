# ChronOntology Spatial API

[![build](https://travis-ci.org/i3mainz/chronontology-spatialapi.svg?branch=master)](https://travis-ci.org/i3mainz/chronontology-spatialapi) [![version](https://img.shields.io/badge/version-1.0--SNAPSHOT-green.svg)](#)  [![java](https://img.shields.io/badge/jdk-1.8-red.svg)](#)  [![maven](https://img.shields.io/badge/maven-3.5.0-orange.svg)](#) [![output](https://img.shields.io/badge/output-war-red.svg)](#) [![docs](https://img.shields.io/badge/apidoc-b13cc2d-blue.svg)](https://i3mainz.github.io/chronontology-spatialapi/)  [![license](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/i3mainz/chronontology-spatialapi/blob/master/LICENSE)

## Usage

Look at the [API documentation](https://github.com/i3mainz/chronontology-spatialapi/blob/master/apidoc.md) for API usage.

## Prerequisites

The code is developed using and tested with:

* maven 3.5.0
* Netbeans 8.2
* Apache Tomcat 8.0.27.0
* JDK 1.8
* Java EE 7 Web

## Maven

The `chronontology-spatialapi` web application is build using `maven` as WAR-file.

For details have a look at [pom.xml](https://github.com/i3mainz/chronontology-spatialapi/blob/master/pom.xml).

[Download](http://maven.apache.org/download.cgi) and [install](https://www.mkyong.com/maven/how-to-install-maven-in-windows/) `maven` and [run](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) it.

## Setup

Download and install `maven`.

Run `git clone https://github.com/i3mainz/chronontology-spatialapi.git` to create a local copy of this repository.

Run `mvn install` to install all required dependencies.

Run `mvn clean install site` for cleaning, building, testing and generating the documentation files.

Run the war-file using maven with `mvn tomcat7:run` in an installed Tomcat. Usually Tomcat will use port 8080 on `http://localhost:8080/`.

Run the war-file as in Tomcat using Netbeans with `Run / Debug` or deploy it to an existing Tomcat. Using Netbeans, usually Tomcat will use port 8084 on `http://localhost:8084/`.

Running `mvn test` will run the unit tests with `JUnit`.

## Documentation

Look at [GitHub Pages](https://i3mainz.github.io/chronontology-spatialapi/) for the latest developer documentation like `maven` and `javadoc`.

## Developer Hints

Look at [Gist](https://gist.github.com/florianthiery/0f8c0c015555939c96eb13428bbf1cd4) hints for `Configurations for JAVA projects using Maven`.

## Repo Developers

Florian Thiery M.Sc. (i3mainz)
