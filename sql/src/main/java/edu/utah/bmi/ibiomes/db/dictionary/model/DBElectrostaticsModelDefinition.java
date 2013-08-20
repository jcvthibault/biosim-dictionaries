package edu.utah.bmi.ibiomes.db.dictionary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Electrostatics model definition
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@Table(name="BIOSIM_DICT_ELECTROSTATICS_MODEL")
public class DBElectrostaticsModelDefinition {

	private Long id;
	private String term;
	private String description;
	private String citations;

	public DBElectrostaticsModelDefinition(){}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Column(name="term")
	public String getTerm() {return term;}
	public void setTerm(String term) {this.term = term;}

	@Column(name="description")
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	@Column(name="citation")
	public String getCitations() {return citations;}
	public void setCitations(String citations) {this.citations = citations;}

}