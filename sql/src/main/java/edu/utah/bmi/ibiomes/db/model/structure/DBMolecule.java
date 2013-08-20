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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBFunctionalGroupDefinition;

/**
 * Molecule
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="MOLECULE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="molecule_type",
    discriminatorType=DiscriminatorType.STRING
)
public class DBMolecule {

	private Long id;
	private String name;
	private String description;
	private String moleculeType;
	private boolean isSolvent;
	private int count;
	private int atomCount;
	private Set<DBStructureReference> structureReferences;
	private Set<DBAtomOccurrence> atoms;
	private Set<DBFunctionalGroupDefinition> functionalGroups;
	private Set<DBMoleculeRole> roles;

	public DBMolecule(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="name")
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	@Column(name="molecule_type", insertable=false, updatable=false)
	public String getMoleculeType() {return moleculeType;}
	public void setMoleculeType(String moleculeType) {this.moleculeType = moleculeType;}
	
	@Column(name="description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@Column(name="is_solvent")
	public boolean isSolvent() {return isSolvent;}
	public void setSolvent(boolean isSolvent) {this.isSolvent = isSolvent;}

	@Column(name="count")
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}

	@Column(name="atom_count")
	public int getAtomCount() {return atomCount;}
	public void setAtomCount(int atomCount) {this.atomCount = atomCount;}
	
	@ManyToMany
    @JoinTable(
            name="MOLECULE_STRUCT_REF",
            joinColumns = @JoinColumn( name="molecule_id"),
            inverseJoinColumns = @JoinColumn(name="ref_id")
    )
	public Set<DBStructureReference> getStructureReferences() {return structureReferences;}
	public void setStructureReferences(Set<DBStructureReference> structureReferences) {this.structureReferences = structureReferences;}

	@OneToMany
	@JoinColumn(name="molecule_id")
	public Set<DBAtomOccurrence> getAtoms() {return this.atoms;}
	public void setAtoms(Set<DBAtomOccurrence> atoms) {this.atoms = atoms;}

	@ManyToMany
    @JoinTable(
            name="MOLECULE_FUNCTIONAL_GROUP_REL",
            joinColumns = @JoinColumn( name="molecule_id"),
            inverseJoinColumns = @JoinColumn(name="functional_group_id")
    )
	public Set<DBFunctionalGroupDefinition> getFunctionalGroups() {return functionalGroups;}
	public void setFunctionalGroups(Set<DBFunctionalGroupDefinition> functionalGroups) {this.functionalGroups = functionalGroups;}

	@OneToMany
	@JoinColumn(name="molecule_id")
	public Set<DBMoleculeRole> getRoles() {return this.roles;}
	public void setRoles(Set<DBMoleculeRole> roles) {this.roles = roles;}
}
