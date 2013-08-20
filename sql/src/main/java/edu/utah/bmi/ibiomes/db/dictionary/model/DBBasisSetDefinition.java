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

package edu.utah.bmi.ibiomes.db.dictionary.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Basis set definition
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="BIOSIM_DICT_BASIS_SET")
public class DBBasisSetDefinition {

	private Long id;
	private String term;
	private String description;
	private String citations;
	private DBBasisSetTypeDefinition basisSetType;

	public DBBasisSetDefinition(){
	}
	
	@Id
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="term")
	public String getTerm() {return term;}
	public void setTerm(String term) {this.term = term;}

	@Column(name="description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@Column(name="citation")
	public String getCitations() {return citations;}
	public void setCitations(String citations) {this.citations = citations;}
	
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBBasisSetTypeDefinition getBasisSetType() {return basisSetType;}
	public void setBasisSetType(DBBasisSetTypeDefinition basisSetType) {this.basisSetType = basisSetType;}

}