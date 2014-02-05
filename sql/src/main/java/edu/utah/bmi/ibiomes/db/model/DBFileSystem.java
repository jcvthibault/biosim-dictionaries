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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * File collection and associated file system information
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="FILE_SYSTEM")
public class DBFileSystem {

	private Long id;
	private String protocol;
	private String hostname;
	private int port;
	private String instanceId;

	public DBFileSystem(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="protocol")
	public String getProtocol() {return protocol;}
	public void setProtocol(String protocol) {this.protocol = protocol;}

	@Column(name="hostname")
	public String getHostname() {return hostname;}
	public void setHostname(String hostname) {this.hostname = hostname;}

	@Column(name="port")
	public int getPort() {return port;}
	public void setPort(int port) {this.port = port;}

	@Column(name="instance_id")
	public String getInstanceId() {return instanceId;}
	public void setInstanceId(String instanceId) {this.instanceId = instanceId;}

}