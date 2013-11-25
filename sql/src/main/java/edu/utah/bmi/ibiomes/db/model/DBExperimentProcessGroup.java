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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.utah.bmi.ibiomes.db.model.structure.DBMolecularSystem;


/**
 * Experiment process group
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="EXPERIMENT_PROCESS_GROUP")
public class DBExperimentProcessGroup {

	private Long id;
	private String name;
	private String description;
	private DBMolecularSystem molecularSystem;
	private List<DBExperimentProcess> processes;
	private Set<DBExperimentProcessGroup> dependencies;

	public DBExperimentProcessGroup(){
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="name")
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	@Column(name="description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@OneToOne
	@JoinColumn(name="system_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBMolecularSystem getMolecularSystem() {return molecularSystem;}
	public void setMolecularSystem(DBMolecularSystem molecularSystem) {this.molecularSystem = molecularSystem;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="process_group_id")
	public List<DBExperimentProcess> getProcesses() {return processes;}
	public void setProcesses(List<DBExperimentProcess> processes) {this.processes = processes;}

	@ManyToMany
	@JoinTable(
            name="EXPERIMENT_PROCESS_GROUP_DEPENDENCY",
            joinColumns = @JoinColumn( name="dep_from"),
            inverseJoinColumns = @JoinColumn(name="dep_to")
    )
	public Set<DBExperimentProcessGroup> getDependencies() {return dependencies;}
	public void setDependencies(Set<DBExperimentProcessGroup> dependencies) {this.dependencies = dependencies;}

}