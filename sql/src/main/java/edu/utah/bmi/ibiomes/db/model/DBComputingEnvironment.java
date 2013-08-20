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
 * Computing environment
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="COMPUTING_ENVIRONMENT")
public class DBComputingEnvironment {

	private Long id;
	private String operatingSystem;
	private String machineArchitecture;
	private String cpuArchitecture;
	private String gpuArchitecture;
	private String resourceDomain;

	public DBComputingEnvironment(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "operating_system")
	public String getOperatingSystem() {return operatingSystem;}
	public void setOperatingSystem(String operatingSystem) {this.operatingSystem = operatingSystem;}

	@Column(name = "cpu_architecture")
	public String getCpuArchitecture() {return cpuArchitecture;}
	public void setCpuArchitecture(String cpuArchitecture) {this.cpuArchitecture = cpuArchitecture;}

	@Column(name = "machine_architecture")
	public String getMachineArchitecture() {return machineArchitecture;}
	public void setMachineArchitecture(String machineArchitecture) {this.machineArchitecture = machineArchitecture;}
	
	@Column(name = "gpu_architecture")
	public String getGpuArchitecture() {return gpuArchitecture;}
	public void setGpuArchitecture(String gpuArchitecture) {this.gpuArchitecture = gpuArchitecture;}

	@Column(name = "domain_name")
	public String getResourceDomain() {return resourceDomain;}
	public void setResourceDomain(String resourceDomain) {this.resourceDomain = resourceDomain;}

}