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

package edu.utah.bmi.ibiomes.db.model.structure.bio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBResidueDefinition;

/**
 * Residue occurrences in the system
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="RESIDUE_OCCURRENCE")
public class DBResidueOccurrence {

	private Long id;
	private int count;
	private boolean isStandard;
	private String specificSymbol;
	private String atomChain;
	private DBResidueDefinition residueDefinition;

	public DBResidueOccurrence(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "count")
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}

	@Column(name = "is_standard")
	public boolean isStandard() {return isStandard;}
	public void setStandard(boolean isStandard) {this.isStandard = isStandard;}

	@Column(name = "specific_symbol")
	public String getSpecificSymbol() {return specificSymbol;}
	public void setSpecificSymbol(String specificSymbol) {this.specificSymbol = specificSymbol;}

	@Column(name = "atom_chain")
	public String getAtomChain() {return atomChain;}
	public void setAtomChain(String atomChain) {this.atomChain = atomChain;}

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="definition_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public DBResidueDefinition getResidueDefinition() {return residueDefinition;}
	public void setResidueDefinition(DBResidueDefinition residueDefinition) {this.residueDefinition = residueDefinition;}

}