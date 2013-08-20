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

Copyright (C) 2013  Julien Thibault, University of Utah

## Directory structure 
This folder (we will call it $BIOSIM_REPO) contains the following sub-directories:
 - [config] Configuration files to edit before API use. These files specify the SQL connection parameters and the Lucene-related parameters) 
 - [data/csv] CSV files containing the dictionary data
 - [lucene] Java API to build and use the Lucene-based dictionaries
 - [lucene/scripts] Scripts to build and test the Lucene indexes.
 - [parent]Maven parent project for lucene/, sql/, and web/ 
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

	cp -f $BIOSIM_REPO/data/csv/* /tmp/

 
To create the tables and populate the data, source 'db_all.sql' in MySQL. First go
to the scripts directory:

	cd $BIOSIM_REPO/sql/scripts/

Then source the SQL script with something like:

	mysql -u SQL_USERNAME -D MY_DATABASE -p < db_all.sql

If you only want to create the dictionary tables, source 'biosim_dict_all.sql':

	mysql -u SQL_USERNAME -D MY_DATABASE -p < biosim_dict_all.sql

To compile the Java API (Hibernate mappings):

	cd $BIOSIM_REPO/sql
	mvn install

## Creating the Lucene-based dictionaries
### Prerequisites
 - Java 1.5+
 - Apache Maven 2

### Installation
To compile the Java API:

	cd $BIOSIM_REPO/lucene
	mvn package

A JAR file containing all the compiled classes should be created in $BIOSIM_REPO/lucene/target/biosim-dictionary-lucene-api.jar
 
To build the Lucene indexes:

	cd $BIOSIM_REPO/lucene
	./scripts/lucene-build-dictionaries.sh <csv-dir> <output-dir>

		<csv-dir> path to the folder containg all the CSV files representing the dictionary data
		<output-dir>: path to the directory where all the Lucene indexes will be created

To test the dictionary lookups:

	cd $BIOSIM_REPO/lucene
	./scripts/lucene-lookup.sh [options]

	Options: lookup -i <index-path> -t <term> [-f <lookup-field>] [-n <max-hits>]
	         list   -i <index-path>
 
 lookup: lookup a term <term> in the Lucene index at <index-path> in a particular field <lookup-field>.
 list:   list all the entries in the Lucene index at <index-path>.

## Building everything
Make sure you read the previous sections first.
To compile: 

	cd $BIOSIM_REPO/parent
	mvn install 



