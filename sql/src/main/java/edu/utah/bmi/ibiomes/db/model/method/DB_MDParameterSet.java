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

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Set of simulation settings specific to Molecular Dynamics runs.
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="MD_PARAMETER_SET")
public class DB_MDParameterSet {

	private Long id;
	private String unitShape;
	private String mmIntegrator;
	private String electrostatics;
	private Double timeStepLength;
	private Integer numberOfTimeSteps;
	private Double langevinCollisionFrequency;
	private Double cutoffForNonbondedInteractions;
	private Double cutoffForVanDerWaals;
	private Double cutoffForElectrostatics;
	private Set<DBForceField> forceFields;
	private Set<DBRestraint> restraints;
	private Set<DBConstraint> constraints;
	private DBBarostat barostat;
	private DBThermostat thermostat;

	public DB_MDParameterSet(){

	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "unit_shape")
	public String getUnitShape() {return unitShape;}
	public void setUnitShape(String unitShape) {this.unitShape = unitShape;}

	@Column(name = "mm_integrator")
	public String getMmIntegrator() {return mmIntegrator;}
	public void setMmIntegrator(String mmIntegrator) {this.mmIntegrator = mmIntegrator;}

	@Column(name = "electrostatics")
	public String getElectrostatics() {return electrostatics;}
	public void setElectrostatics(String electrostatics) {this.electrostatics = electrostatics;}

	@Column(name = "time_step_length")
	public Double getTimeStepLength() {return timeStepLength;}
	public void setTimeStepLength(Double timeStepLength) {this.timeStepLength = timeStepLength;}

	@Column(name = "number_time_steps")
	public Integer getNumberOfTimeSteps() {return numberOfTimeSteps;}
	public void setNumberOfTimeSteps(Integer numberOfTimeSteps) {this.numberOfTimeSteps = numberOfTimeSteps;}

	@Column(name = "cutoff_nonbonded")
	public Double getCutoffForNonbondedInteractions() {return cutoffForNonbondedInteractions;}
	public void setCutoffForNonbondedInteractions(Double cutoffForNonbondedInteractions) {this.cutoffForNonbondedInteractions = cutoffForNonbondedInteractions;}

	@Column(name = "cutoff_vdw")
	public Double getCutoffForVanDerWaals() {return cutoffForVanDerWaals;}
	public void setCutoffForVanDerWaals(Double cutoffForVanDerWaals) {this.cutoffForVanDerWaals = cutoffForVanDerWaals;}

	@Column(name = "cutoff_electrostatics")
	public Double getCutoffForElectrostatics() {return cutoffForElectrostatics;}
	public void setCutoffForElectrostatics(Double cutoffForElectrostatics) {this.cutoffForElectrostatics = cutoffForElectrostatics;}
	
	@Column(name = "langevin_collision_freq")
	public Double getLangevinCollisionFrequency() {return langevinCollisionFrequency;}
	public void setLangevinCollisionFrequency(Double langevinCollisionFrequency) {this.langevinCollisionFrequency = langevinCollisionFrequency;}

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="barostat_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBBarostat getBarostat() {return barostat;}
	public void setBarostat(DBBarostat barostat) {this.barostat = barostat;}

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="thermostat_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBThermostat getThermostat() {return thermostat;}
	public void setThermostat(DBThermostat thermostat) {this.thermostat = thermostat;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="method_id")
	public Set<DBForceField> getForceFields() {return forceFields;}
	public void setForceFields(Set<DBForceField> forceFields) {this.forceFields = forceFields;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="method_id")
	public Set<DBRestraint> getRestraints() {return restraints;}
	public void setRestraints(Set<DBRestraint> restraints) {this.restraints = restraints;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="method_id")
	public Set<DBConstraint> getConstraints() {return constraints;}
	public void setConstraints(Set<DBConstraint> constraints) {this.constraints = constraints;}
}
