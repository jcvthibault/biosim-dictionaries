/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
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

package edu.utah.bmi.ibiomes.dictionary.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import edu.utah.bmi.ibiomes.dictionary.core.CSVFile;

/**
 * Build lookup dictionaries (Lucene indexes) from CSV files
 * @author Julien Thibault, University of Utah
 *
 */
public class LuceneDictionaryBuilder {

	public final static String PATH_FOLDER_SEPARATOR  = ((System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) ? "\\" : "/");
	
	public LuceneDictionaryBuilder() {
	}

	public static void main(String args[]) throws Exception {

		if (args.length<2){
			System.err.println("Missing argument!\nUsage: <csv-dir> <output-dir>\n");
			System.exit(1);
		}
		else {
			File outputDir = new File(args[1]);
			if (!outputDir.exists()){
				System.err.println("Output directory '"+args[1]+"' does not exist!");
				System.exit(1);
			}
			
			LuceneDictionaryBuilder dico = new LuceneDictionaryBuilder();

			//list csv files in directory
			File dir = new File(args[0]);
			File[] fileList = dir.listFiles();
			List<File> files = new ArrayList<File>();
			for (File file : fileList){
				if (file.getName().endsWith(".csv")){
					files.add(file);
				}
			}
			
			//create indexes from CSV
			dico.buildDictionariesFromCSVs(files, outputDir);
			
			//create master dictionary
			dico.buildMasterDictionary(files, outputDir);
		}
	}

	/**
	 * Build all dictionaries from CSV files located in given directory
	 * @param files List of CSV files containing the data to index
	 * @param outputDir Output directory where the Lucene indexes will be stored
	 * @throws Exception
	 */
	public void buildDictionariesFromCSVs(List<File> files, File outputDir) throws Exception
	{
		//build 1 dictionary for each CSV file
		for (File file : files){
			//load CSV file
			CSVFile csvFile = new CSVFile(file.getAbsolutePath());
			//build dictionaries
			buildDictionaryFromCSV(csvFile, outputDir);
		}
	}

	/**
	 * Build master dictionaries with entries populated from all the CSV files
	 * @param files List of CSV files
	 * @param outputDir Path to output directory for the index
	 * @return New index file
	 * @throws Exception 
	 */
	public File buildMasterDictionary(List<File> files, File outputDir) throws Exception {
		
		List<CSVFile> csvFiles = new ArrayList<CSVFile>();
		for (File file : files){
			//load CSV file
			csvFiles.add(new CSVFile(file.getAbsolutePath()));	
		}
		
		int docCount = 0;
		IndexWriter writer = null;
		
		String indexFilePath = outputDir.getAbsolutePath() + PATH_FOLDER_SEPARATOR + "dictionary_all";
		File indexFile = new File(indexFilePath);
		try {
			Directory dir = FSDirectory.open(indexFile);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
			writer = new IndexWriter(dir, config);
			
			for (CSVFile csvFile : csvFiles)
			{
				ArrayList<ArrayList<String>> data = csvFile.getData();
				String[] headers = csvFile.getHeaders();
				
				//read each CSV entry
				for (ArrayList<String> dataEntry : data)
				{
					docCount++;
					Document document = new Document();
	
					document.add(new TextField(LuceneDictionary.LOOKUP_FIELD_UID, String.valueOf(docCount), Field.Store.YES));
					
					//indexed fields
					for (int c=0; c<headers.length;c++)
					{
						if (dataEntry.size()>c){
							String value = dataEntry.get(c);
							if (value == null)
								value = "";
							document.add(new TextField(headers[c], value, Field.Store.YES));
						}
					}
					writer.addDocument(document);
				}
			}
			writer.close();

			System.out.println(docCount + " entries added to index '" + indexFile);
			return indexFile;
		}
		catch (Exception e)
		{
			System.out.println("An error occurred!");
			if (writer!=null){
				writer.close();
			}
			System.out.println("\n" + docCount + " entries added to index '" + indexFile);
			throw e;
		}		
	}

	/**
	 * Build lookup dictionary (Lucene index) from CSV files
	 * @param csvFile CSV file
	 * @param outputDir Output directory
	 * @return Index file
	 * @throws Exception
	 */
	public File buildDictionaryFromCSV(CSVFile csvFile, File outputDir) throws Exception
	{
		int docCount = 0;
		IndexWriter writer = null;
		
		ArrayList<ArrayList<String>> data = csvFile.getData();
		String[] headers = csvFile.getHeaders();

		String csvFileName = csvFile.getAbsolutePath().substring(0,csvFile.getAbsolutePath().length()-4);
		if (csvFileName.indexOf(PATH_FOLDER_SEPARATOR)>-1)
			csvFileName = csvFileName.substring(csvFileName.lastIndexOf(PATH_FOLDER_SEPARATOR)+1,csvFileName.length());
		
		String indexFilePath = outputDir.getAbsolutePath() + PATH_FOLDER_SEPARATOR + csvFileName;
		File indexFile = new File(indexFilePath);
		try {
			Directory dir = FSDirectory.open(indexFile);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
			writer = new IndexWriter(dir, config);

			//read each CSV entry
			for (ArrayList<String> dataEntry : data)
			{
				docCount++;
				Document document = new Document();

				//indexed fields
				for (int c=0; c<headers.length;c++)
				{
					if (dataEntry.size()>c){
						String value = dataEntry.get(c);
						if (value == null)
							value = "";
						document.add(new TextField(headers[c], value, Field.Store.YES));
					}
				}
				writer.addDocument(document);
			}
			writer.close();

			System.out.println(docCount + " entries added to index '" + indexFile);
			return indexFile;
		}
		catch (Exception e)
		{
			System.out.println("An error occurred!");
			if (writer!=null){
				writer.close();
			}
			System.out.println("\n" + docCount + " entries added to index '" + indexFile);
			throw e;
		}
	}
	
	/**
	 * Delete dictionary
	 * @param indexFilePath Path to index file
	 * @throws IOException 
	 */
	public void deleteDictionary(String indexFilePath) throws IOException{
		File indexFile = new File(indexFilePath);
		Directory dir = FSDirectory.open(indexFile);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
		IndexWriter writer = new IndexWriter(dir, config);
		writer.deleteAll();
		writer.close();
	}
}

