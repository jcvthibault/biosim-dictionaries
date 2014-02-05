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

package edu.utah.bmi.ibiomes.db.model.structure;

import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBAtomicElement;

/**
 * Atom occurrences
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="ATOM_OCCURRENCE")
public class DBAtomOccurrence {

	private Long id;
	private int count;
	private String specificSymbol;
	private DBAtomicElement elementDefinition;

	public DBAtomOccurrence(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@Column(name="count")
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}

	@Column(name="symbol")
	public String getSpecificSymbol() {return specificSymbol;}
	public void setSpecificSymbol(String specificSymbol) {this.specificSymbol = specificSymbol;}

	@OneToOne
	@JoinColumn(name="element_id")
	public DBAtomicElement getElementDefinition() {return elementDefinition;}
	public void setElementDefinition(DBAtomicElement elementDefinition) {this.elementDefinition = elementDefinition;}
}