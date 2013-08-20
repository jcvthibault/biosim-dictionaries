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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBComputationalMethodDefinition;

/**
 * List of settings specific to Quantum chemistry calculations.
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="QM_PARAMETER_SET")
public class DB_QMParameterSet {

	private Long id;
	private String methodName;
	private Integer spinMultiplicity;
	private Integer charge;
	private Boolean frozenCore;
	private DBComputationalMethodDefinition qmMethod;
	private Set<DBBasisSet> basisSets;
	private Set<DBPseudoPotential> pseudoPotentials;

	public DB_QMParameterSet(){
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name = "method_name")
	public String getMethodName() {return methodName;}
	public void setMethodName(String methodName) {this.methodName = methodName;}

	@Column(name = "spin_multiplicity")
	public Integer getSpinMultiplicity() {return spinMultiplicity;}
	public void setSpinMultiplicity(Integer spinMultiplicity) {this.spinMultiplicity = spinMultiplicity;}

	@Column(name = "charge")
	public Integer getCharge() {return charge;}
	public void setCharge(Integer charge) {this.charge = charge;}

	@Column(name = "frozen_core")
	public Boolean getFrozenCore() {return frozenCore;}
	public void setFrozenCore(Boolean frozenCore) {this.frozenCore = frozenCore;}
	
	@OneToOne
	@JoinColumn(name="std_method_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DBComputationalMethodDefinition getQmMethod() {return qmMethod;}
	public void setQmMethod(DBComputationalMethodDefinition qmMethod) {this.qmMethod = qmMethod;}

	@OneToMany
	@JoinColumn(name="method_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Set<DBBasisSet> getBasisSets() {return basisSets;}
	public void setBasisSets(Set<DBBasisSet> basisSets) {this.basisSets = basisSets;}

	@OneToMany
	@JoinColumn(name="param_set_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Set<DBPseudoPotential> getPseudoPotentials() {return pseudoPotentials;}
	public void setPseudoPotentials(Set<DBPseudoPotential> pseudoPotentials) {this.pseudoPotentials = pseudoPotentials;}
}
