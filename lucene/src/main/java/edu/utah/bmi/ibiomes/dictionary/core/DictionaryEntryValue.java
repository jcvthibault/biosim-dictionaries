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

import java.util.Dictionary;

/**
 * Dictionary entry value (field)
 * @author Julien Thibault, University of Utah
 *
 */
public class DictionaryEntryValue {

	public enum DictionaryAttributeType{
		STRING, INTEGER, LONG, FLOAT, DOUBLE, CHAR, BOOLEAN, DATE
	}
	
	private String attribute;
	private DictionaryAttributeType attributeType;
	private String value;
	
	/**
	 * Create new entry value
	 * @param attribute
	 * @param value
	 */
	public DictionaryEntryValue(String attribute, String value){
		this.attribute = attribute;
		this.value = value;
		this.attributeType = DictionaryAttributeType.STRING;
	}
	
	/**
	 * Create new entry value
	 * @param attribute
	 * @param value
	 * @param type
	 */
	public DictionaryEntryValue(String attribute, DictionaryAttributeType type, String value){
		this.attribute = attribute;
		this.value = value;
		this.attributeType = type;
		
		if (attributeType == DictionaryAttributeType.BOOLEAN){
			if (this.value.equals("1"))
				this.value = "Yes";
			else this.value = "No";
		}
	}
	
	/**
	 * Get attribute name
	 * @return Attribute name
	 */
	public String getAttribute() {
		return attribute;
	}
	/**
	 * Set attribute name
	 * @param attribute Attribute name
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
	 * Get value
	 * @return Value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Set value
	 * @param value Value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * Get attribute type
	 * @return Attribute type
	 */
	public DictionaryAttributeType getAttributeType() {
		return this.attributeType;
	}
	/**
	 * Set attribute type
	 * @param attributeType Attribute type
	 */
	public void setAttributeType(DictionaryAttributeType attributeType) {
		this.attributeType = attributeType;
	}
	
	@Override
	public String toString(){
		String string = "["+this.attribute+"=\""+this.value+"\"]";
		return string.trim();
	}

}
