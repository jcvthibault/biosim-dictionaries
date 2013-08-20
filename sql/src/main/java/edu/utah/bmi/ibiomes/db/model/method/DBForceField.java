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

package edu.utah.bmi.ibiomes.db.model.method;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBForceFieldDefinition;

/**
 * Force field
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="FORCE_FIELD")
public class DBForceField {

	private Long id;
	private String name;
	private DBForceFieldDefinition definition;

	public DBForceField() {
	}
	
	/**
	 * Create new force field
	 * @param name Force field name
	 */
	public DBForceField(String name) {
		this.name = name;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "name")
	public String getName() {return name;}
	public void setName(String name) { this.name = name; }

	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="reference_id")
	public DBForceFieldDefinition getDefinition() {return definition;}
	public void setDefinition(DBForceFieldDefinition definition) {this.definition = definition;}
}