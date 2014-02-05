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

package edu.utah.bmi.ibiomes.db.dictionary.model;

import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Residue definition
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="BIOSIM_DICT_RESIDUE")
public class DBResidueDefinition {

	private Long id;
	private String symbol1;
	private String symbol3;
	private String term;
	private String description;
	private String type;
	private String formula;

	public DBResidueDefinition(){}
	
	@Id
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="term")
	public String getTerm() {return term;}
	public void setTerm(String term) {this.term = term;}

	@Column(name="description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	@Column(name = "symbol1")
	public String getSymbol1() {return symbol1;}
	public void setSymbol1(String symbol1) {this.symbol1 = symbol1;}

	@Column(name = "symbol3")
	public String getSymbol3() {return symbol3;}
	public void setSymbol3(String symbol3) {this.symbol3 = symbol3;}

	@Column(name = "type")
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	@Column(name = "formula")
	public String getFormula() {return formula;}
	public void setFormula(String formula) {this.formula = formula;}

}