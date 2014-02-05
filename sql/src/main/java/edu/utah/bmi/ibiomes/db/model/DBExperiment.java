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
import javax.persistence.Table;

/**
 * Experiment
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="EXPERIMENT")
public class DBExperiment {

	private Long id;
	private String name;
	private String description;
	private String owner;
	private Long timestamp;
	private List<DBExperimentProcessGroup> processGroups;
	private Set<DBExperimentSet> referencingSets;
	private Set<DBFileSystem> fileCollections;
	private Set<DBCitation> citations;
	private Set<DBResearchGrant> referencingGrants;

	public DBExperiment(){}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "name")
	public String getName() {return name;}
	public void setName(String name) { this.name = name; }

	@Column(name = "description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@Column(name = "owner")
	public String getOwner() {return owner;}
	public void setOwner(String owner) {this.owner = owner;}

	@Column(name = "timestamp")
	public Long getTimestamp() {return timestamp;}
	public void setTimestamp(Long timestamp) {this.timestamp = timestamp;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="experiment_id", insertable=true)
	public List<DBExperimentProcessGroup> getProcessGroups() {return processGroups;}
	public void setProcessGroups(List<DBExperimentProcessGroup> processGroups) {this.processGroups = processGroups;}

	@ManyToMany
    @JoinTable(
            name="EXPERIMENT_SET_REL",
            joinColumns = @JoinColumn( name="experiment_id"),
            inverseJoinColumns = @JoinColumn(name="set_id")
    )
	public Set<DBExperimentSet> getReferencingSets() {return referencingSets;}
	public void setReferencingSets(Set<DBExperimentSet> referencingSets) {this.referencingSets = referencingSets;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="experiment_id")
	public Set<DBFileSystem> getFileCollections() {return fileCollections;}
	public void setFileCollections(Set<DBFileSystem> fileCollections) {this.fileCollections = fileCollections;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="experiment_id")
	public Set<DBCitation> getCitations() {return citations;}
	public void setCitations(Set<DBCitation> citations) {this.citations = citations;}

	@ManyToMany
    @JoinTable(
            name="EXPERIMENT_GRANT_REL",
            joinColumns = @JoinColumn( name="experiment_id"),
            inverseJoinColumns = @JoinColumn(name="grant_id")
    )
	public Set<DBResearchGrant> getReferencingGrants() {return referencingGrants;}
	public void setReferencingGrants(Set<DBResearchGrant> referencingGrants) {this.referencingGrants = referencingGrants;}
}