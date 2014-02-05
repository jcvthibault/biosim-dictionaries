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

package edu.utah.bmi.ibiomes.dictionary.core;

import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue.DictionaryAttributeType;

/**
 * Dictionary entry
 * @author Julien Thibault, University of Utah
 *
 */
public class DictionaryEntry extends ArrayList<DictionaryEntryValue>{

	private static final long serialVersionUID = 4222662838008964062L;
	/**
	 * Enumeration of possible attribute types
	 * @author Julien Thibault, University of Utah
	 *
	 */
	
	public DictionaryEntry(){	
	}
	
	/**
	 * Get value of attribute
	 * @param attributeName Attribute name
	 * @return Value of attribute
	 */
	public DictionaryEntryValue getValue(String attributeName){
		DictionaryEntryValue entryValue = null;
		for (DictionaryEntryValue value : this){
			if (value.getAttribute().equals(attributeName))
				entryValue = value;
		}
		return entryValue;
	}
	
	/**
	 * Replace entry value
	 * @param attributeName Name of the attribute
	 * @param newValue New value
	 */
	public void replaceValue(String attributeName, String newValue) {
		DictionaryEntryValue entryValue = this.getValue(attributeName);
		entryValue.setValue(newValue);
	}

	/**
	 * Get list of attributes
	 * @return List of attribute names
	 */
	public List<String> getAttributeNames() {
		List<String> attributeNames = new ArrayList<String>();
		for (DictionaryEntryValue value : this){
			attributeNames.add(value.getAttribute());
		}
		return attributeNames;
	}

	/**
	 * Get list of attribute types
	 * @return List of attribute names
	 */
	public List<DictionaryAttributeType> getAttributeTypes() {
		List<DictionaryAttributeType> attributeTypes = new ArrayList<DictionaryAttributeType>();
		for (DictionaryEntryValue value : this){
			attributeTypes.add(value.getAttributeType());
		}
		return attributeTypes;
	}
	
	@Override
	public String toString(){
		String string = "";
		for (DictionaryEntryValue value : this){
			string += value.toString() + " ";
		}
		return string.trim();
	}

}
