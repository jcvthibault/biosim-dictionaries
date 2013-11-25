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

package edu.utah.bmi.ibiomes.db.model.structure;

import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Structure reference from community database
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="STRUCTURE_REFERENCE")
public class DBStructureReference {

	private Long id;
	private String database;
	private String entryId;
	private String preparationProtocol;

	public DBStructureReference(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="database_id")
	public String getDatabase() {return database;}
	public void setDatabase(String database) {this.database = database;}

	@Column(name="entry_id")
	public String getEntryId() {return entryId;}
	public void setEntryId(String entryId) {this.entryId = entryId;}

	@Column(name="preparation_protocol")
	public String getPreparationProtocol() {return preparationProtocol;}
	public void setPreparationProtocol(String preparationProtocol) {this.preparationProtocol = preparationProtocol;}

}
