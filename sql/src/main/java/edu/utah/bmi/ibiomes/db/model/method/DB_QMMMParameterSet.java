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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * List of settings specific to QM/MM runs.
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="QMMM_PARAMETER_SET")
public class DB_QMMMParameterSet {

	private Long id;
	private String boundaryTreatment;
	private String electrostaticsInteractionType;

	public DB_QMMMParameterSet(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "boundary_treatment")
	public String getBoundaryTreatment() {return boundaryTreatment;}
	public void setBoundaryTreatment(String boundaryTreatment) {this.boundaryTreatment = boundaryTreatment;}

	@Column(name = "electrostatics_interaction")
	public String getElectrostaticsInteractionType() {return electrostaticsInteractionType;}
	public void setElectrostaticsInteractionType(String electrostaticsInteractionType) {this.electrostaticsInteractionType = electrostaticsInteractionType;}

}