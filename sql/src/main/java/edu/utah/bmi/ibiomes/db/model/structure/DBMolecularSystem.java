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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Molecular system
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="MOLECULAR_SYSTEM")
public class DBMolecularSystem {

	private Long id;
	private String name;
	private String description;
	private int atomCount;
	private int solventCount;
	private int ionCount;
	private Double apparentPH;
	private Set<DBStructureReference> structureReferences;
	private Set<DBMolecule> molecules;

	public DBMolecularSystem(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="name")
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	@Column(name="description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@Column(name="atom_count")
	public int getAtomCount() {return atomCount;}
	public void setAtomCount(int atomCount) {this.atomCount = atomCount;}

	@Column(name="solvent_count")
	public int getSolventCount() {return solventCount;}
	public void setSolventCount(int solventCount) {this.solventCount = solventCount;}

	@Column(name="ion_count")
	public int getIonCount() {return ionCount;}
	public void setIonCount(int ionCount) {this.ionCount = ionCount;}

	@Column(name="ph")
	public Double getApparentPH() {return apparentPH;}
	public void setApparentPH(Double apparentPH) {this.apparentPH = apparentPH;}
	
	@ManyToMany
    @JoinTable(
            name="MOLECULAR_SYSTEM_STRUCT_REF",
            joinColumns = @JoinColumn( name="system_id"),
            inverseJoinColumns = @JoinColumn(name="ref_id")
    )
	public Set<DBStructureReference> getStructureReferences() {return structureReferences;}
	public void setStructureReferences(Set<DBStructureReference> structureReferences) {this.structureReferences = structureReferences;}

	@OneToMany
	@JoinColumn(name="system_id")
	public Set<DBMolecule> getMolecules() {return molecules;}
	public void setMolecules(Set<DBMolecule> molecules) {this.molecules = molecules;}

}