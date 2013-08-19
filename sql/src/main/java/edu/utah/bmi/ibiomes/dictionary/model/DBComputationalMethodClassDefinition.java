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

package edu.utah.bmi.ibiomes.dictionary.model;

import java.lang.String;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Class of computational methods (e.g. HF, DFT, MP)
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="BIOSIM_DICT_COMPUTATIONAL_METHOD_CLASS")
public class DBComputationalMethodClassDefinition {

	private Long id;
	private String shortName;
	private String term;
	private String description;
	private DBComputationalMethodFamilyDefinition family;
	private List<DBComputationalMethodDefinition> methods;

	public DBComputationalMethodClassDefinition(){}

	@Id
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="term")
	public String getTerm() {return term;}
	public void setTerm(String term) {this.term = term;}

	@Column(name = "short_name")
	public String getShortName() {return shortName;}
	public void setShortName(String shortName) { this.shortName = shortName; }
	
	@Column(name = "description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@OneToOne
	@JoinColumn(name="type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBComputationalMethodFamilyDefinition getFamily() {return family;}
	public void setFamily(DBComputationalMethodFamilyDefinition family) {this.family = family;}

	@OneToMany
    @JoinTable(
            name="BIOSIM_DICT_COMPUTATIONAL_METHOD_DICT_CLASS_DICT",
            joinColumns = @JoinColumn( name="class_id"),
            inverseJoinColumns = @JoinColumn(name="method_id")
    )
	public List<DBComputationalMethodDefinition> getMethods() {return methods;}
	public void setMethods(List<DBComputationalMethodDefinition> methods) {this.methods = methods;}

}