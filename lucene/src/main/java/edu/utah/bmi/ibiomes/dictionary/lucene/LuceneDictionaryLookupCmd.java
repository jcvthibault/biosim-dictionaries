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
import java.util.List;

import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntry;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue;

/**
 * Command-line for dictionary lookups
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class LuceneDictionaryLookupCmd {

	private final static String usage1 = "lookup -i <index-path> -t <term> [-f <lookup-field>] [-n <max-hits>]";
	private final static String usage2 = "list   -i <index-path>";
	
	public static void main(String[] args) throws Exception
    { 
    	String indexFilePath = null;
    	String lookupFieldName = "TERM";
    	String term = null;
    	int maxHits = 20;
    	
    	if (args.length==0){
    		System.out.println("Missing arguments! Usage:\n" + usage1 + "\n" + usage2);
			System.exit(1);
    	}
    	String action = args[0];
    	
    	for (int i = 1; i < args.length; i++) 
		{
		      if (args[i].equals("-i")) {
		    	  indexFilePath = args[i+1];
		        i++;
		      }
		      else if (args[i].equals("-n")) {
		    	  maxHits = Integer.parseInt(args[i+1]);
		    	  i++;
		      }
		      else if (args[i].equals("-t")) {
		    	  term = args[i+1];
		    	  i++;
		    	  while (i+1<args.length)
		    	  {
		    		  i++;
		    		  if (args[i].startsWith("-")){
		    			  i--;
		    			  break;
		    		  }
		    		  else {
		    			  term += " " + args[i];
		    		  }
		    	  }
		      }
		      else if (args[i].equals("-f")) {
		    	  lookupFieldName = args[i+1];
		    	  i++;
		      }
		      else System.out.println("\nWARNING: unknown option '" + args[i] + "'\n");
		}
    	
    	//list all entries in dictionary
    	if (action.equals("list")){
    	
	    	if (indexFilePath == null){
	    		System.out.println("Missing argument!\nUsage: " + usage2);
				System.exit(1);
	    	}
	    	
	    	File indexFile = new File(indexFilePath);

	    	System.out.println("Dictionary: "+indexFile.getCanonicalPath());
	    	System.out.println("");

	    	LuceneDictionary dictionary = new LuceneDictionary(indexFile, maxHits);
	    	List<DictionaryEntry> matches = dictionary.getAllEntries();
	    	if (matches!=null && matches.size()>0){
	    		System.out.println(matches.size() + " entries found:");
	    		for (DictionaryEntry entry : matches){
	    			System.out.println("\t"+entry.toString());
	    		}
	    	}
	    	else System.out.println("No entry found in this dictionary");
	    	
	    	dictionary.closeSearch();
    	}
    	
    	//do lookup
    	else if (action.equals("lookup")){
    		if (indexFilePath == null || term == null){
	    		System.out.println("Missing argument!\nUsage: " + usage1);
				System.exit(1);
	    	}
	    	File indexFile = new File(indexFilePath);

	    	System.out.println("Lookup field: "+lookupFieldName);
	    	System.out.println("        Term: "+term);
	    	System.out.println("    Max hits: "+maxHits);
	    	System.out.println("  Dictionary: "+indexFile.getCanonicalPath());
	    	
	    	LuceneDictionary dictionary = new LuceneDictionary(indexFile, maxHits);
	    	
	    	System.out.println("Number of entries: " + dictionary.getNumberOfEntries());
	    	System.out.println("");
	    	
	    	List<DictionaryEntry> matches = dictionary.lookupTerm(lookupFieldName, term);
	    	if (matches!=null && matches.size()>0){
	    		System.out.println(matches.size() + " matches:");
	    		for (DictionaryEntry entry : matches){
	    			System.out.println("--------------------------------");
	    			for (DictionaryEntryValue entryField : entry){
	    				System.out.println("  ["+entryField.getAttribute() + "] " + entryField.getValue());
	    			}
	    		}
	    	}
	    	else System.out.println("No match found for '"+term+"' in this dictionary");
			System.out.println("--------------------------------");
	    	
	    	dictionary.closeSearch();
    	}
    	else {
    		System.out.println("Unknown action: '"+action+"'\nUsage: ");
    		System.out.println(usage1);
    		System.out.println(usage2);
    	}
    }
}

