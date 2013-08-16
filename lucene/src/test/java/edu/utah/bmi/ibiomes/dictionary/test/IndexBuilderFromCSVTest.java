/* iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2013  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.dictionary.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.dictionary.core.CSVFile;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntry;
import edu.utah.bmi.ibiomes.dictionary.lucene.LuceneDictionary;
import edu.utah.bmi.ibiomes.dictionary.lucene.LuceneDictionaryBuilder;

/**
 * Tests for dictionary builds and lookups
 * @author Julien Thibault
 *
 */
public class IndexBuilderFromCSVTest {

	private final Logger logger = Logger.getLogger(IndexBuilderFromCSVTest.class);
	
	@Test
	public void testIndexSingleCSV() throws Exception
	{
		try{
			//create index from CSV
			String csvPath = "../data/csv/dictionary_methods.csv";
			String outputDirPath = "test";
			LuceneDictionaryBuilder builder = new LuceneDictionaryBuilder();
			
			CSVFile csvFile = new CSVFile(csvPath);
			File indexFile = builder.buildDictionaryFromCSV(csvFile, new File(outputDirPath));
			LuceneDictionary dictionary = new LuceneDictionary(indexFile, 10);
	
			List<DictionaryEntry> inexactMatches1 = dictionary.lookupTerm("TERM", "CCSDT*");
			System.out.println("Inexact matches for 'CCSDT*':");
			for (DictionaryEntry entry : inexactMatches1){
				System.out.println("\t" + entry.toString());
			}
			List<DictionaryEntry> inexactMatches2 = dictionary.lookupTerm("DESCRIPTION", "doubles triples");
			System.out.println("Inexact matches for 'doubles triples':");
			for (DictionaryEntry entry : inexactMatches2){
				System.out.println("\t" + entry.toString());
			}
			//close search
			dictionary.closeSearch();
			
			//delete index file
			builder.deleteDictionary(indexFile.getAbsolutePath());
			indexFile.delete();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testIndexAllCSV() throws Exception
	{	
		try{
			//create index from CSV
			String csvDirPath = "../data/csv";
			String outputDirPath = "test";
			LuceneDictionaryBuilder builder = new LuceneDictionaryBuilder();
			
			//list csv files in directory
			File dir = new File(csvDirPath);
			File[] fileList = dir.listFiles();
			List<File> files = new ArrayList<File>();
			for (File file : fileList){
				if (file.getName().endsWith(".csv")){
					files.add(file);
				}
			}
			File indexFile = builder.buildMasterDictionary(files, new File(outputDirPath));
			LuceneDictionary dictionary = new LuceneDictionary(indexFile, 10);
	
			List<DictionaryEntry> inexactMatches1 = dictionary.lookupTerm("TERM", "method*");
			System.out.println("Inexact matches for 'method*':");
			for (DictionaryEntry entry : inexactMatches1){
				System.out.println("\t" + entry.toString());
			}
			List<DictionaryEntry> inexactMatches2 = dictionary.lookupTerm("DESCRIPTION", "methods");
			System.out.println("Inexact matches for 'methods':");
			for (DictionaryEntry entry : inexactMatches2){
				System.out.println("\t" + entry.toString());
			}
			
			//filtered search
			List<String> attributeTypes = new ArrayList<String>();
			attributeTypes.add("method");
			attributeTypes.add("method_class");
			attributeTypes.add("data_generating_method");
			List<DictionaryEntry> inexactMatches3 = dictionary.lookupTermInSpecificDictionaries("DESCRIPTION", "method*", attributeTypes);
			System.out.println("Inexact matches for 'method*' with filter:");
			for (DictionaryEntry entry : inexactMatches3){
				System.out.println("\t" + entry.toString());
			}
			
			//close search
			dictionary.closeSearch();
			
			//delete index file
			//builder.deleteDictionary(indexFile.getAbsolutePath());
			//indexFile.delete();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
