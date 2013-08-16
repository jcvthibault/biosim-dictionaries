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

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.dictionary.core.CSVFile;

/**
 * Tests for CSV parser
 * @author Julien Thibault
 *
 */
public class CSVFileTest {

	private final Logger logger = Logger.getLogger(CSVFileTest.class);
	
	private final String[] csvPaths = {
			"../data/csv/dictionary_methods.csv",
			"../data/csv/dictionary_basis_sets.csv",
			"../data/csv/dictionary_force_fields.csv",
			"../data/csv/dictionary_software.csv"
	};
	
	@Test
	public void testLookup() throws Exception
	{
		for (String csvPath : csvPaths){
			try{
				logger.info("Parsing " + csvPath + "...");
				CSVFile csv = new CSVFile(csvPath);
				String[] headers = csv.getHeaders();
				for (String header : headers){
					System.out.print("[" + header + "] ");
				}
				System.out.println("");
				
				ArrayList<ArrayList<String>> data = csv.getData();
				int n = 0;
				for (ArrayList<String> row : data){
					for (String val : row){
						System.out.print(val + " | ");
					}
					System.out.println("");
					n++;
					if (n==3)
						break;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
	}
}
