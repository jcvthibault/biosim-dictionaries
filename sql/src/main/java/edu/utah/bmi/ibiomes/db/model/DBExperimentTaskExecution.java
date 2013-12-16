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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Experiment task execution
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="TASK_EXECUTION")
public class DBExperimentTaskExecution {

	private Long id;
	private double time;
	private boolean normalTermination;
	private Integer numberOfCPUs;
	private Integer numberOfGPUs;
	private Long startTimestamp;
	private Long endTimestamp;

	public DBExperimentTaskExecution(){
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "time")
	public double getTime() {return time;}
	public void setTime(double time) {this.time = time;}

	@Column(name = "normal_termination")
	public boolean isNormalTermination() {return normalTermination;}
	public void setNormalTermination(boolean normalTermination) {this.normalTermination = normalTermination;}

	@Column(name = "number_of_cpus")
	public int getNumbeOfCPUs() {return numberOfCPUs;}
	public void setNumbeOfCPUs(int numberOfCPUs) {this.numberOfCPUs = numberOfCPUs;}

	@Column(name = "number_of_gpus")
	public int getNumbeOfGPUs() {return numberOfGPUs;}
	public void setNumbeOfGPUs(int numberOfGPUs) {this.numberOfGPUs = numberOfGPUs;}

	@Column(name = "start_timestamp")
	public Long getStartTimestamp() {return startTimestamp;}
	public void setStartTimestamp(Long startTimestamp) {this.startTimestamp = startTimestamp;}

	@Column(name = "end_timestamp")
	public Long getEndTimestamp() {return endTimestamp;}
	public void setEndTimestamp(Long endTimestamp) {this.endTimestamp = endTimestamp;}
}