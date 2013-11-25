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

package edu.utah.bmi.ibiomes.db.model;

import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.utah.bmi.ibiomes.db.model.method.DBCalculation;
import edu.utah.bmi.ibiomes.db.model.method.DBImplicitSolventModel;
import edu.utah.bmi.ibiomes.db.model.method.DBSimulatedConditionSet;

/**
 * Experiment task
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="EXPERIMENT_TASK")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(
    name="method_type",
    discriminatorType=DiscriminatorType.STRING
)
public class DBExperimentTask {

	private Long id;
	private String name;
	private String description;
	private boolean periodicBoundaryConditions;
	private String solventType;
	private DBImplicitSolventModel implicitSolventModel;
	private DBSoftware software;
	private DBComputingEnvironment environment;
	private DBSimulatedConditionSet conditionSet;
	private Set<DBCalculation> calculations;
	private DBExperimentTaskExecution taskExecution;
	private Set<DBExperimentTask> dependencies;

	public DBExperimentTask(){
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "name")
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	@Column(name = "description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@Column(name = "has_periodic_bc")
	public boolean isPeriodicBoundaryConditions() {return periodicBoundaryConditions;}
	public void setPeriodicBoundaryConditions(boolean periodicBoundaryConditions) {this.periodicBoundaryConditions = periodicBoundaryConditions;}

	@Column(name = "solvent_type")
	public String getSolventType() {return solventType;}
	public void setSolventType(String solventType) {this.solventType = solventType;}

	@OneToOne
	@JoinColumn(name="implicit_solvent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBImplicitSolventModel getImplicitSolventModel() {return implicitSolventModel;}
	public void setImplicitSolventModel(DBImplicitSolventModel implicitSolventModel) {this.implicitSolventModel = implicitSolventModel;}

	@OneToOne
	@JoinColumn(name="software_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBSoftware getSoftware() {return software;}
	public void setSoftware(DBSoftware software) {this.software = software;}

	@OneToOne
	@JoinColumn(name="environment_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBComputingEnvironment getEnvironment() {return environment;}
	public void setEnvironment(DBComputingEnvironment environment) {this.environment = environment;}

	@OneToMany
	@JoinColumn(name="task_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Set<DBCalculation> getCalculations() {return calculations;}
	public void setCalculations(Set<DBCalculation> calculations) {this.calculations = calculations;}
	
	@OneToOne
	@JoinColumn(name="condition_set_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBSimulatedConditionSet getConditionSet() {return conditionSet;}
	public void setConditionSet(DBSimulatedConditionSet conditionSet) {this.conditionSet = conditionSet;}

	@OneToOne
	@JoinColumn(name="execution_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBExperimentTaskExecution getTaskExecution() {return taskExecution;}
	public void setTaskExecution(DBExperimentTaskExecution taskExecution) {this.taskExecution = taskExecution;}
	
	@ManyToMany
	@JoinTable(
            name="EXPERIMENT_TASK_DEPENDENCY",
            joinColumns = @JoinColumn( name="dep_from"),
            inverseJoinColumns = @JoinColumn(name="dep_to")
    )
	public Set<DBExperimentTask> getDependencies() {return dependencies;}
	public void setDependencies(Set<DBExperimentTask> dependencies) {this.dependencies = dependencies;}

}