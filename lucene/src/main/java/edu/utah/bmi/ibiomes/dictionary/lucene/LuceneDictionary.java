/*
 * iBIOMES - Integrated Biomolecular Simulations
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

package edu.utah.bmi.ibiomes.dictionary.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FieldCacheTermsFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntry;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue.DictionaryAttributeType;

/**
 * Wrapper around Lucene dictionary
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class LuceneDictionary {

	protected final Version LUCENE_VERSION = Version.LUCENE_41;

	public static final String LOOKUP_FIELD_UID 			= "UID";
	public static final String LOOKUP_FIELD_ID 				= "ID";
	public static final String LOOKUP_FIELD_TERM 			= "TERM";
	public static final String LOOKUP_FIELD_DESCRIPTION 	= "DESCRIPTION";
	public static final String LOOKUP_FIELD_TYPE 			= "TYPE";
	public static final String LOOKUP_FIELD_TYPE_ID 		= "TYPE_ID";
	public static final String LOOKUP_FIELD_ATTRIBUTE_TYPE 	= "ATTRIBUTE_TYPE";
	
	protected IndexSearcher searcher;
	protected IndexReader indexReader;
    
	protected int maxNumHits;

    /**
     * Instantiate new Lucene dictionary
     * @param indexFile Lucene index file
     * @param maxHits Maximum number of hits to return
     * @throws IOException
     */
    public LuceneDictionary(File indexFile, int maxHits) throws IOException
    { 
    	if (indexFile.exists()){
			this.indexReader = DirectoryReader.open(FSDirectory.open(indexFile));
			this.searcher = new IndexSearcher(this.indexReader);
	        this.maxNumHits = maxHits;
		}
    	else throw new IOException("Lucene index not found at '"+ indexFile.getAbsolutePath() +"'");
    }
    
    /**
     * Instantiate new Lucene dictionary
     * @param indexFilePath Lucene index file path
     * @param maxHits Maximum number of hits to return
     * @throws IOException
     */
    public LuceneDictionary(String indexFilePath, int maxHits) throws IOException
    { 
    	File indexFile = new File(indexFilePath);
    	if (indexFile.exists()){
			this.indexReader = DirectoryReader.open(FSDirectory.open(indexFile));
			this.searcher = new IndexSearcher(this.indexReader);
	        this.maxNumHits = maxHits;
		}
    	else throw new IOException("Lucene index not found at '"+ indexFilePath +"'");
    }
    
    /**
	 * Get all entries in dictionary
	 * @return List of entries
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public List<DictionaryEntry> getAllEntries() throws CorruptIndexException, IOException {
		List<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
		for (int d=0; d<this.indexReader.numDocs();d++){
			Document doc = this.indexReader.document(d);
			DictionaryEntry entry = getDictionaryEntryFromDocument(doc);
			entries.add(entry);
		}
		return entries;
	}
	
	/**
	 * Get dictionary entry
	 * @param doc Document
	 * @return Entry
	 */
	public DictionaryEntry getDictionaryEntryFromDocument(Document doc){
		DictionaryEntry entry = new DictionaryEntry();
		List<IndexableField> fields = doc.getFields();
		for (IndexableField field : fields)
		{
			DictionaryAttributeType fieldType = DictionaryEntryValue.DictionaryAttributeType.STRING;
			if (field.name().matches("(ID)|(UID)|(.*_ID)")){
				fieldType = DictionaryEntryValue.DictionaryAttributeType.INTEGER;
			}
			else if (field.name().matches("(HAS_.*)|(IS_.*)")){
				fieldType = DictionaryEntryValue.DictionaryAttributeType.BOOLEAN;
			}				
			DictionaryEntryValue value = new DictionaryEntryValue(field.name(), fieldType, doc.get(field.name()));
			entry.add(value);
		}
		return entry;
	}
    
    /**
     * Lookup a term in the dictionary
     * @param term Term to lookup
     * @return List of hits
     * @throws Exception
     */
    public List<DictionaryEntry> lookupTerm(String lookupFieldName, String term) throws Exception
    {
    	List<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
    	//create query
    	QueryParser parser = new QueryParser(LUCENE_VERSION, lookupFieldName, new StandardAnalyzer(LUCENE_VERSION));
    	Query query = parser.parse(term);
    	//search
    	ScoreDoc[] hits = this.searcher.search( query, null, this.maxNumHits).scoreDocs;
        for (int i = 0; i < hits.length; i++){
			Document hitDoc = this.searcher.doc(hits[i].doc);
			DictionaryEntry entry = getDictionaryEntryFromDocument(hitDoc);
			entries.add(entry);
        }
        return entries;
    }
    
    /**
     * Lookup a term in the dictionary and filter by element type (ATTRIBUTE_TYPE field)
     * @param term Term to lookup
     * @param lookupFieldName Lookup field
     * @param dictionaries List of dictionaries to use as a source
     * @return List of matching entries
     * @throws Exception
     */
    public List<DictionaryEntry> lookupTermInSpecificDictionaries(String lookupFieldName, String term, List<String> dictionaries) throws Exception
    {
    	List<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
    	String[] dictionaryNames = new String[dictionaries.size()];
    	dictionaryNames = dictionaries.toArray(dictionaryNames);
    	FieldCacheTermsFilter filter = new FieldCacheTermsFilter(LOOKUP_FIELD_ATTRIBUTE_TYPE, dictionaryNames);
    	//create query
    	QueryParser parser = new QueryParser(LUCENE_VERSION, lookupFieldName, new StandardAnalyzer(LUCENE_VERSION));
    	Query query = parser.parse(term);
    	//search
    	ScoreDoc[] hits = this.searcher.search( query, filter, this.maxNumHits).scoreDocs;
        for (int i = 0; i < hits.length; i++){
			Document hitDoc = this.searcher.doc(hits[i].doc);
			DictionaryEntry entry = getDictionaryEntryFromDocument(hitDoc);
			entries.add(entry);
        }
        return entries;
    }
    
    /**
     * Close Lucene index
     * @throws IOException
     */
    public void closeSearch() throws IOException{
    	this.indexReader.close();
    }
    
    /**
     * Return the number of entries in the index
     * @return Number of entries
     */
    public int getNumberOfEntries(){
    	return this.indexReader.numDocs();
    }

    /**
     * Get maximum number of hits to be returned by lookups
     * @return Maximum number of hits to be returned by lookups
     */
	public int getMaxNumHits() {
		return maxNumHits;
	}

	/**
	 * Set the maximum number of hits to be returned by lookups
	 * @param maxNumHits Maximum number of hits to be returned by lookups
	 */
	public void setMaxNumHits(int maxNumHits) {
		this.maxNumHits = maxNumHits;
	}
}

