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

package edu.utah.bmi.ibiomes.db.model.structure.bio;

import java.lang.String;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Residue sequence
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="RESIDUE_SEQUENCE")
public class DBResidueSequence {

	private Long id;
	private String specificChain;
	private String normalizedChain;
	private List<DBResidueOccurrence> residues;

	public DBResidueSequence(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "specific_seq")
	public String getSpecificChain() {return specificChain;}
	public void setSpecificChain(String specificChain) {this.specificChain = specificChain;}

	@Column(name = "normalized_seq")
	public String getNormalizedChain() {return normalizedChain;}
	public void setNormalizedChain(String normalizedChain) {
		this.normalizedChain = normalizedChain;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="sequence_id")
	public List<DBResidueOccurrence> getResidues() {return residues;}
	public void setResidues(List<DBResidueOccurrence> residues) {this.residues = residues;}

}