<!--
 iBIOMES - Integrated Biomolecular Simulations
 Copyright (C) 2013  Julien Thibault, University of Utah
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->

#Biomolecular simulation repository

iBIOMES - Integrated Biomolecular Simulations

Copyright (C) 2014  Julien Thibault, University of Utah

This project provides logical and data models to prototype a repository for 
biomolecular simulations. Scripts are available to build a MySQL DB, along with
the Java API to interact with the database (through Hibernate).

A set of dictionaries (including basis sets, force-fields, and computational 
methods) was also created to define terms that are commonly used in the field of 
biomolecular simulation, whether it it is in the literature or in the input/output
 files used to run the simulations.
All the dictionaries are stored as simple CSV (Comma-Separated Value) files. 
A Java API was created to wrap these dictionaries into Apache Lucene indexes. 
The API can be used within a program to query elements based on their name or 
description. A set of SQL scripts is also provided to use these dictionaries 
as database tables. The scripts create a database schema and populate the new 
tables with the CSV data.

## Directory structure 
This folder (we will call it $BIOSIM_REPO_HOME) contains the following sub-directories:
 - [config] Configuration files to edit before API use. These files specify the SQL connection parameters and the Lucene-related parameters) 
 - [data/csv] CSV files containing the dictionary data
 - [lucene] Java API to build and use the Lucene-based dictionaries
 - [lucene/scripts] Scripts to build and test the Lucene indexes.
 - [sql] Hibernate mappings (Java/SQL) for the database
 - [sql/scripts] Scripts to create and populate the SQL tables
 - [uml] UML logical (Java) and physical (MySQL) models
 - [web] Web interface for the Lucene-based dictionaries

## Creating the database
### Prerequisites
 - MySQL server 5+
 - Java 1.5+
 - Apache Maven 2

### Installation
Copy the CSV files to the /tmp directory so that MySQL can read files:

	cp -f $BIOSIM_REPO_HOME/data/csv/* /tmp/

 
To create the tables and populate the data, source 'db_all.sql' in MySQL. First go
to the scripts directory:

	cd $BIOSIM_REPO_HOME/sql/scripts/

Then source the SQL script with something like:

	mysql -u SQL_USERNAME -D MY_DATABASE -p < db_all.sql

If you only want to create the dictionary tables, source 'biosim_dict_all.sql':

	mysql -u SQL_USERNAME -D MY_DATABASE -p < biosim_dict_all.sql

To compile the Java API (Hibernate mappings):

	cd $BIOSIM_REPO_HOME/sql
	mvn install

## Creating the Lucene-based dictionaries
### Prerequisites
 - Java 1.5+
 - Apache Maven 2

### Installation
To compile the Java API:

	cd $BIOSIM_REPO_HOME/lucene
	mvn package

A JAR file containing all the compiled classes should be created in $BIOSIM_REPO_HOME/lucene/target/biosim-dictionary-lucene-api.jar
 
To build the Lucene indexes:

	cd $BIOSIM_REPO_HOME/lucene
	./scripts/lucene-build-dictionaries.sh <csv-dir> <output-dir>

		<csv-dir> path to the folder containg all the CSV files representing the dictionary data
		<output-dir>: path to the directory where all the Lucene indexes will be created

To test the dictionary lookups:

	cd $BIOSIM_REPO_HOME/lucene
	./scripts/lucene-lookup.sh [options]

	Options: lookup -i <index-path> -t <term> [-f <lookup-field>] [-n <max-hits>]
	         list   -i <index-path>
 
 lookup: lookup a term <term> in the Lucene index at <index-path> in a particular field <lookup-field>.
 list:   list all the entries in the Lucene index at <index-path>.

## Dictionary web interface
### Prerequisites
 - Java 1.5+
 - Apache Maven 2
 - Java container (e.g. Apache Tomcat, JBoss)

### Installation
To compile the Java API and package into WAR file:

	cd $BIOSIM_REPO_HOME/web
	mvn install

This will create a WAR file into the target/ directory. 
To install:
 - Build the Lucene-based dictionary (see above)
 - Set the $BIOSIM_REPO_HOME environment variable
 - Edit the $BIOSIM_REPO_HOME/config/biosim-dictionaries-lucene.properties to provide the absolute path of the Lucene index.
 - Copy the WAR file into your web container, for example with Tomcat:

	cp $BIOSIM_REPO_HOME/web/target/biosim-dictionary-web.war $CATALINA_HOME/webapps/

Restart tyour web server if necessary.

## Building everything
Make sure you read the previous sections first.
To compile: 

	cd $BIOSIM_REPO_HOME
	mvn install 



