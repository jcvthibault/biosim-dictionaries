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
 * Minimization parameter set
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="MINIMIZATION_PARAMETER_SET")
public class DBMinimizationParameterSet {

	private Long id;
	private String method;
	private int numberOfIterations;

	public DBMinimizationParameterSet(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@Column(name = "method_name")
	public String getMethod() {return method;}	
	public void setMethod(String method) {this.method = method;}

	@Column(name = "number_iterations")
	public int getNumberOfIterations() {return numberOfIterations;}
	public void setNumberOfIterations(int numberOfIterations) {this.numberOfIterations = numberOfIterations;}

}