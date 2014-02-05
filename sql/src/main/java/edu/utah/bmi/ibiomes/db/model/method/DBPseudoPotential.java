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

package edu.utah.bmi.ibiomes.db.model.method;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Pseudo potential for QM methods
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="PSEUDO_POTENTIAL")
public class DBPseudoPotential {

	private Long id;
	private String name;
	private Double planeWaveCutoff;

	public DBPseudoPotential(){}

	/**
	 * Create named pseudo potential
	 * @param name Pseudo potential name
	 */
	public DBPseudoPotential(String name) {
		this.name = name;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "name")
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	@Column(name = "plane_wave_cutoff")
	public Double getPlaneWaveCutoff() {return planeWaveCutoff;}
	public void setPlaneWaveCutoff(Double planeWaveCutoff) {this.planeWaveCutoff = planeWaveCutoff;}

}