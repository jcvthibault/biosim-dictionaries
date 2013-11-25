SET FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS ATOM_OCCURRENCE
;
DROP TABLE IF EXISTS BAROSTAT
;
DROP TABLE IF EXISTS BASIS_SET
;
DROP TABLE IF EXISTS BIOSIM_DICT_BAROSTAT
;
DROP TABLE IF EXISTS BIOSIM_DICT_BASIS_SET
;
DROP TABLE IF EXISTS BIOSIM_DICT_BASIS_SET_TYPE
;
DROP TABLE IF EXISTS BIOSIM_DICT_CALCULATION
;
DROP TABLE IF EXISTS BIOSIM_DICT_CALCULATION_TYPE
;
DROP TABLE IF EXISTS BIOSIM_DICT_COMPUTATIONAL_METHOD
;
DROP TABLE IF EXISTS BIOSIM_DICT_COMPUTATIONAL_METHOD_CLASS
;
DROP TABLE IF EXISTS BIOSIM_DICT_COMPUTATIONAL_METHOD_DICT_CLASS_DICT
;
DROP TABLE IF EXISTS BIOSIM_DICT_COMPUTATIONAL_METHOD_FAMILY
;
DROP TABLE IF EXISTS BIOSIM_DICT_CONSTRAINT_ALGORITHM
;
DROP TABLE IF EXISTS BIOSIM_DICT_DATA_GENERATION_METHOD
;
DROP TABLE IF EXISTS BIOSIM_DICT_DATA_GENERATION_METHOD_TYPE
;
DROP TABLE IF EXISTS BIOSIM_DICT_ELECTROSTATICS_MODEL
;
DROP TABLE IF EXISTS BIOSIM_DICT_ELEMENT_FAMILY
;
DROP TABLE IF EXISTS BIOSIM_DICT_ENSEMBLE_TYPE
;
DROP TABLE IF EXISTS BIOSIM_DICT_FILE_FORMAT
;
DROP TABLE IF EXISTS BIOSIM_DICT_FORCE_FIELD
;
DROP TABLE IF EXISTS BIOSIM_DICT_FORCE_FIELD_TYPE
;
DROP TABLE IF EXISTS BIOSIM_DICT_FUNCTIONAL_GROUP
;
DROP TABLE IF EXISTS BIOSIM_DICT_PERIODIC_TABLE
;
DROP TABLE IF EXISTS BIOSIM_DICT_RESIDUE
;
DROP TABLE IF EXISTS BIOSIM_DICT_SOFTWARE
;
DROP TABLE IF EXISTS BIOSIM_DICT_THERMOSTAT
;
DROP TABLE IF EXISTS CALCULATION
;
DROP TABLE IF EXISTS CITATION
;
DROP TABLE IF EXISTS COMPUTING_ENVIRONMENT
;
DROP TABLE IF EXISTS EXPERIMENT
;
DROP TABLE IF EXISTS EXPERIMENT_DATA_GENERATING_METHOD
;
DROP TABLE IF EXISTS EXPERIMENT_GRANT_REL
;
DROP TABLE IF EXISTS EXPERIMENT_PROCESS
;
DROP TABLE IF EXISTS EXPERIMENT_PROCESS_DEPENDENCY
;
DROP TABLE IF EXISTS EXPERIMENT_PROCESS_GROUP
;
DROP TABLE IF EXISTS EXPERIMENT_PROCESS_GROUP_DEPENDENCY
;
DROP TABLE IF EXISTS EXPERIMENT_ROLE
;
DROP TABLE IF EXISTS EXPERIMENT_SET
;
DROP TABLE IF EXISTS EXPERIMENT_SET_REL
;
DROP TABLE IF EXISTS EXPERIMENT_TASK
;
DROP TABLE IF EXISTS EXPERIMENT_TASK_DEPENDENCY
;
DROP TABLE IF EXISTS FILE
;
DROP TABLE IF EXISTS FILE_SYSTEM
;
DROP TABLE IF EXISTS FORCE_FIELD
;
DROP TABLE IF EXISTS IMPLICIT_SOLVENT_MODEL
;
DROP TABLE IF EXISTS MD_CONSTRAINT
;
DROP TABLE IF EXISTS MD_PARAMETER_SET
;
DROP TABLE IF EXISTS MD_TASK
;
DROP TABLE IF EXISTS MINIMIZATION_PARAMETER_SET
;
DROP TABLE IF EXISTS MINIMIZATION_TASK
;
DROP TABLE IF EXISTS MOLECULAR_SYSTEM
;
DROP TABLE IF EXISTS MOLECULAR_SYSTEM_DEFINITION_FILE_SET
;
DROP TABLE IF EXISTS MOLECULAR_SYSTEM_STRUCT_REF
;
DROP TABLE IF EXISTS MOLECULE
;
DROP TABLE IF EXISTS MOLECULE_FUNCTIONAL_GROUP_REL
;
DROP TABLE IF EXISTS MOLECULE_IDENTIFIER
;
DROP TABLE IF EXISTS MOLECULE_ROLE
;
DROP TABLE IF EXISTS MOLECULE_STRUCT_REF
;
DROP TABLE IF EXISTS PSEUDO_POTENTIAL
;
DROP TABLE IF EXISTS QM_PARAMETER_SET
;
DROP TABLE IF EXISTS QM_TASK
;
DROP TABLE IF EXISTS QMMM_PARAMETER_SET
;
DROP TABLE IF EXISTS QMMM_TASK
;
DROP TABLE IF EXISTS RESEARCH_GRANT
;
DROP TABLE IF EXISTS RESIDUE_OCCURRENCE
;
DROP TABLE IF EXISTS RESIDUE_SEQUENCE
;
DROP TABLE IF EXISTS RESTRAINT
;
DROP TABLE IF EXISTS SIMULATED_CONDITION_SET
;
DROP TABLE IF EXISTS SOFTWARE
;
DROP TABLE IF EXISTS STRUCTURE_REFERENCE
;
DROP TABLE IF EXISTS TASK_EXECUTION
;
DROP TABLE IF EXISTS TASK_INPUT_FILE
;
DROP TABLE IF EXISTS TASK_OUTPUT_FILE
;
DROP TABLE IF EXISTS THERMOSTAT
;

CREATE TABLE ATOM_OCCURRENCE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	molecule_id INTEGER,
	element_id INTEGER,
	count INTEGER NOT NULL,
	symbol VARCHAR(10) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_ATOM_OCCURENCE_id(id),
	KEY (molecule_id),
	KEY (element_id)
) 
;


CREATE TABLE BAROSTAT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	algorithm VARCHAR(50),
	time_constant DOUBLE,
	PRIMARY KEY (id),
	UNIQUE UQ_BAROSTAT_id(id)
) 
;


CREATE TABLE BASIS_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	method_id INTEGER,
	definition_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_BASIS_SET_id(id),
	KEY (definition_id),
	KEY (method_id)
) 
;


CREATE TABLE BIOSIM_DICT_BAROSTAT
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_BAROSTAT_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_BASIS_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	type_id INTEGER,
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_BASIS_SET_DICTIONARY_id(id),
	KEY (type_id)
) 
;


CREATE TABLE BIOSIM_DICT_BASIS_SET_TYPE
(
	id INTEGER NOT NULL,
	term VARCHAR(50),
	description TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_BASIS_SET_TYPE_DICTIONARY_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_CALCULATION
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	type_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_CALCULATION_id(id),
	KEY (type_id)
) 
;


CREATE TABLE BIOSIM_DICT_CALCULATION_TYPE
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_CALCULATION_TYPE_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_COMPUTATIONAL_METHOD_DICTIONARY_term(term),
	UNIQUE UQ_METHOD_DICTIONARY_ID(id)
) 
;


CREATE TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD_CLASS
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	short_name VARCHAR(50) NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	family_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_COMPUTATIONAL_METHOD_CLASS_DICTIONARY_term(term),
	UNIQUE UQ_METHOD_CLASS_ID(id),
	UNIQUE UQ_METHOD_CLASS_DICTIONARY_SHORT_NAME(short_name),
	KEY (family_id)
) 
;


CREATE TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD_DICT_CLASS_DICT
(
	method_id INTEGER NOT NULL,
	class_id INTEGER NOT NULL,
	PRIMARY KEY (method_id, class_id),
	KEY (class_id),
	KEY (method_id)
) 
;


CREATE TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD_FAMILY
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_COMPUTATIONAL_METHOD_FAMILY_DICTIONARY_term(term),
	UNIQUE UQ_METHOD_FAMILY_DICTIONARY_ID(id)
) 
;


CREATE TABLE BIOSIM_DICT_CONSTRAINT_ALGORITHM
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_CONSTRAINT_ALORITHM_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_DATA_GENERATION_METHOD
(
	id INTEGER NOT NULL,
	term VARCHAR(100),
	description VARCHAR(255),
	type_id INTEGER,
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_STD_DATA_GEN_METHOD_id(id),
	KEY (type_id)
) 
;


CREATE TABLE BIOSIM_DICT_DATA_GENERATION_METHOD_TYPE
(
	id INTEGER NOT NULL,
	term VARCHAR(50),
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_STD_DATA_GEN_METHOD_TYPE_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_ELECTROSTATICS_MODEL
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_ELECTROSTATICS_MODEL_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_ELEMENT_FAMILY
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_ELEMENT_FAMILY_DICT_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_ENSEMBLE_TYPE
(
	id INTEGER NOT NULL,
	term VARCHAR(50),
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_ENSEMBLE_TYPE_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_FILE_FORMAT
(
	id INTEGER NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	software VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_FILE_FORMAT_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_FORCE_FIELD
(
	id INTEGER NOT NULL,
	term VARCHAR(255) NOT NULL,
	description VARCHAR(255),
	citation TEXT,
	type_id INTEGER,
	coarse_grain BOOL,
	PRIMARY KEY (id),
	UNIQUE UQ_FORCE_FIELD_DICTIONARY_id(id),
	KEY (type_id)
) 
;


CREATE TABLE BIOSIM_DICT_FORCE_FIELD_TYPE
(
	id INTEGER NOT NULL,
	term VARCHAR(50),
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_FORCE_FIELD_TYPE_term(term),
	UNIQUE UQ_FORCE_FIELD_TYPE_DICTIONARY_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_FUNCTIONAL_GROUP
(
	id INTEGER NOT NULL,
	term VARCHAR(50),
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_FUNCTIONAL_GROUP_DICTIONARY_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_PERIODIC_TABLE
(
	id INTEGER NOT NULL,
	symbol VARCHAR(3) NOT NULL,
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	mass DOUBLE NOT NULL,
	family_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_PERIODIC_TABLE_id(id),
	KEY (family_id)
) 
;


CREATE TABLE BIOSIM_DICT_RESIDUE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	symbol1 VARCHAR(1) NOT NULL,
	symbol3 VARCHAR(3),
	term VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	type VARCHAR(50),
	formula VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_RESIDUE_DICT_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_SOFTWARE
(
	id INTEGER NOT NULL,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_SOFTWARE_DICTIONARY_id(id)
) 
;


CREATE TABLE BIOSIM_DICT_THERMOSTAT
(
	id INTEGER NOT NULL,
	term VARCHAR(50),
	description VARCHAR(255),
	citation TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_BIOSIM_DICT_THERMOSAT_id(id)
) 
;


CREATE TABLE CALCULATION
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	task_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_CALCULATION_id(id),
	KEY (task_id)
) 
;


CREATE TABLE CITATION
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	url TEXT NOT NULL,
	doi VARCHAR(255) NOT NULL,
	experiment_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_CITATION_id(id),
	KEY (experiment_id)
) 
;


CREATE TABLE COMPUTING_ENVIRONMENT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	operating_system VARCHAR(50),
	cpu_architecture VARCHAR(50),
	gpu_architecture VARCHAR(50),
	machine_architecture VARCHAR(50),
	domain_name VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_COMPUTING_ENVIRONMENT_id(id)
) 
;


CREATE TABLE EXPERIMENT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(255),
	owner VARCHAR(50) NOT NULL,
	timestamp INTEGER NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_id(id)
) 
;


CREATE TABLE EXPERIMENT_DATA_GENERATING_METHOD
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(100),
	experiment_id INTEGER,
	reference_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_DATA_GENERATING_METHOD_id(id),
	KEY (experiment_id),
	KEY (reference_id)
) 
;


CREATE TABLE EXPERIMENT_GRANT_REL
(
	experiment_id INTEGER NOT NULL,
	grant_id INTEGER NOT NULL,
	PRIMARY KEY (experiment_id, grant_id),
	KEY (experiment_id),
	KEY (grant_id)
) 
;


CREATE TABLE EXPERIMENT_PROCESS
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	process_group_id INTEGER,
	name VARCHAR(255),
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_PROCESS_id(id),
	KEY (process_group_id)
) 
;


CREATE TABLE EXPERIMENT_PROCESS_DEPENDENCY
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	dep_from INTEGER,
	dep_to INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_PROCESS_DEPENDENCY_id(id),
	KEY (dep_from),
	KEY (dep_to)
) 
;


CREATE TABLE EXPERIMENT_PROCESS_GROUP
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	description VARCHAR(255),
	experiment_id INTEGER,
	system_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_PROCESS_GROUP_id(id),
	KEY (experiment_id),
	KEY (system_id)
) 
;


CREATE TABLE EXPERIMENT_PROCESS_GROUP_DEPENDENCY
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	dep_from INTEGER,
	dep_to INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_PROCESS_GROUP_DEP_id(id),
	KEY (dep_from),
	KEY (dep_to)
) 
;


CREATE TABLE EXPERIMENT_ROLE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(100),
	description VARCHAR(255),
	experiment_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_ROLE_id(id),
	KEY (experiment_id)
) 
;


CREATE TABLE EXPERIMENT_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_SET_id(id)
) 
;


CREATE TABLE EXPERIMENT_SET_REL
(
	set_id INTEGER NOT NULL,
	experiment_id INTEGER NOT NULL,
	PRIMARY KEY (set_id, experiment_id),
	KEY (experiment_id),
	KEY (set_id)
) 
;


CREATE TABLE EXPERIMENT_TASK
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	description VARCHAR(255),
	has_periodic_bc BOOL,
	solvent_type VARCHAR(50),
	method_type VARCHAR(255),
	process_id INTEGER,
	environment_id INTEGER,
	implicit_solvent_id INTEGER,
	software_id INTEGER,
	condition_set_id INTEGER,
	execution_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_TASK_id(id),
	KEY (execution_id),
	KEY (environment_id),
	KEY (process_id),
	KEY (software_id),
	KEY (implicit_solvent_id),
	KEY (condition_set_id)
) 
;


CREATE TABLE EXPERIMENT_TASK_DEPENDENCY
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	dep_from INTEGER,
	dep_to INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_EXPERIMENT_TASK_DEPENDENCY_id(id),
	KEY (dep_from),
	KEY (dep_to)
) 
;


CREATE TABLE FILE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	uid INTEGER,
	file_system_id INTEGER,
	format VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_FILE_id(id),
	KEY (file_system_id)
) 
;


CREATE TABLE FILE_SYSTEM
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	protocol VARCHAR(100) NOT NULL,
	hostname VARCHAR(255),
	port INTEGER,
	instance_id VARCHAR(50),
	experiment_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_FILE_SYSTEM_id(id),
	KEY (experiment_id)
) 
;


CREATE TABLE FORCE_FIELD
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	method_id INTEGER,
	reference_id INTEGER,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_FORCE_FIELD_id(id),
	KEY (reference_id),
	KEY (method_id)
) 
;


CREATE TABLE IMPLICIT_SOLVENT_MODEL
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_IMPLICIT_SOLVENT_MODEL_id(id)
) 
;


CREATE TABLE MD_CONSTRAINT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	method_id INTEGER,
	algorithm VARCHAR(50),
	target VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_CONSTRAINT_id(id),
	KEY (method_id)
) 
;


CREATE TABLE MD_PARAMETER_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	unit_shape VARCHAR(50),
	mm_integrator VARCHAR(50),
	electrostatics VARCHAR(50),
	time_step_length DOUBLE,
	number_time_steps INTEGER,
	langevin_collision_freq DOUBLE,
	cutoff_nonbonded DOUBLE,
	cutoff_vdw DOUBLE,
	cutoff_electrostatics DOUBLE,
	ensemble_type VARCHAR(50),
	thermostat_id INTEGER,
	barostat_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_MD_METHOD_id(id),
	KEY (barostat_id)
) 
;


CREATE TABLE MD_TASK
(
	id INTEGER NOT NULL,
	md_method_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_MD_TASK_id(id)
) 
;


CREATE TABLE MINIMIZATION_PARAMETER_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	number_iterations INTEGER,
	method_name VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_MINIMIZATION_METHOD_id(id)
) 
;


CREATE TABLE MINIMIZATION_TASK
(
	id INTEGER NOT NULL,
	min_method_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_MINIMIZATION_TASK_id(id),
	KEY (min_method_id)
) 
;


CREATE TABLE MOLECULAR_SYSTEM
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	description VARCHAR(255),
	atom_count INTEGER,
	solvent_count INTEGER,
	ion_count INTEGER,
	ph DOUBLE,
	PRIMARY KEY (id),
	UNIQUE UQ_MOLECULAR_SYSTEM_id(id)
) 
;


CREATE TABLE MOLECULAR_SYSTEM_DEFINITION_FILE_SET
(
	system_id INTEGER NOT NULL,
	file_id INTEGER NOT NULL,
	PRIMARY KEY (system_id, file_id),
	KEY (system_id),
	KEY (file_id)
) 
;


CREATE TABLE MOLECULAR_SYSTEM_STRUCT_REF
(
	system_id INTEGER NOT NULL,
	ref_id INTEGER NOT NULL,
	PRIMARY KEY (system_id, ref_id),
	KEY (ref_id),
	KEY (system_id)
) 
;


CREATE TABLE MOLECULE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	molecule_type VARCHAR(50),
	system_id INTEGER,
	is_solvent BOOL NOT NULL,
	count INTEGER NOT NULL,
	description VARCHAR(255),
	mass DOUBLE,
	stoichiometry VARCHAR(50),
	atom_count INTEGER,
	species VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_MOLECULE_id(id),
	KEY (system_id)
) 
;


CREATE TABLE MOLECULE_FUNCTIONAL_GROUP_REL
(
	molecule_id INTEGER NOT NULL,
	functional_group_id INTEGER NOT NULL,
	PRIMARY KEY (molecule_id, functional_group_id),
	KEY (molecule_id),
	KEY (functional_group_id)
) 
;


CREATE TABLE MOLECULE_IDENTIFIER
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	syntax VARCHAR(50) NOT NULL,
	string TEXT,
	molecule_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_MOLECULE_IDENTIFIER_id(id),
	KEY (molecule_id)
) 
;


CREATE TABLE MOLECULE_ROLE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	molecule_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_MOLECULE_ROLE_id(id),
	KEY (molecule_id)
) 
;


CREATE TABLE MOLECULE_STRUCT_REF
(
	molecule_id INTEGER NOT NULL,
	ref_id INTEGER NOT NULL,
	KEY (molecule_id),
	KEY (ref_id)
) 
;


CREATE TABLE PSEUDO_POTENTIAL
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	param_set_id INTEGER NOT NULL,
	name VARCHAR(50),
	plane_wave_cutoff DOUBLE,
	PRIMARY KEY (id),
	UNIQUE UQ_PSEUDO_POTENTIAL_id(id),
	KEY (param_set_id)
) 
;


CREATE TABLE QM_PARAMETER_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	method_name VARCHAR(255),
	std_method_id INTEGER,
	spin_multiplicity INTEGER,
	charge INTEGER,
	frozen_core BOOL,
	PRIMARY KEY (id),
	UNIQUE UQ_QM_METHOD_id(id),
	KEY (std_method_id)
) 
;


CREATE TABLE QM_TASK
(
	id INTEGER NOT NULL,
	qm_method_id INTEGER,
	PRIMARY KEY (id),
	KEY (id)
) 
;


CREATE TABLE QMMM_PARAMETER_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	electrostatics_interaction VARCHAR(255),
	boundary_treatment VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_QMMM_METHOD_id(id)
) 
;


CREATE TABLE QMMM_TASK
(
	id INTEGER NOT NULL,
	qm_method_id INTEGER,
	md_method_id INTEGER,
	qmmm_method_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_QMMM_TASK_id(id),
	KEY (md_method_id),
	KEY (qm_method_id),
	KEY (qmmm_method_id)
) 
;


CREATE TABLE RESEARCH_GRANT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	source VARCHAR(50) NOT NULL,
	code VARCHAR(50),
	name VARCHAR(50) NOT NULL,
	description VARCHAR(255),
	url VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_GRANT_id(id)
) 
;


CREATE TABLE RESIDUE_OCCURRENCE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	sequence_id INTEGER,
	definition_id INTEGER,
	count INTEGER NOT NULL,
	specific_symbol VARCHAR(5) NOT NULL,
	is_standard BOOL NOT NULL,
	atom_chain VARCHAR(255),
	PRIMARY KEY (id),
	UNIQUE UQ_RESIDUE_OCCURENCE_id(id),
	KEY (definition_id),
	KEY (sequence_id)
) 
;


CREATE TABLE RESIDUE_SEQUENCE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	normalized_seq TEXT,
	specific_seq TEXT NOT NULL,
	molecule_id INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_RESIDUE_SEQUENCE_id(id),
	KEY (molecule_id)
) 
;


CREATE TABLE RESTRAINT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	method_id INTEGER,
	target VARCHAR(50),
	restraint_type VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_RESTRAINT_id(id),
	KEY (method_id)
) 
;


CREATE TABLE SIMULATED_CONDITION_SET
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	pressure DOUBLE,
	temperature DOUBLE,
	PRIMARY KEY (id),
	UNIQUE UQ_MOLECULAR_SYSTEM_ENVIRONMENT_id(id)
) 
;


CREATE TABLE SOFTWARE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	version VARCHAR(50),
	install_version VARCHAR(50),
	PRIMARY KEY (id),
	UNIQUE UQ_SOFTWARE_id(id)
) 
;


CREATE TABLE STRUCTURE_REFERENCE
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	database_id VARCHAR(50) NOT NULL,
	entry_id VARCHAR(50) NOT NULL,
	preparation_protocol TEXT,
	PRIMARY KEY (id),
	UNIQUE UQ_STRUCTURE_REFERENCE_id(id)
) 
;


CREATE TABLE TASK_EXECUTION
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	time DOUBLE,
	normal_termination BOOL,
	number_of_cpus INTEGER,
	number_of_gpus INTEGER,
	PRIMARY KEY (id),
	UNIQUE UQ_TASK_EXECUTION_id(id)
) 
;


CREATE TABLE TASK_INPUT_FILE
(
	task_id INTEGER NOT NULL,
	file_id INTEGER NOT NULL,
	PRIMARY KEY (task_id, file_id),
	KEY (task_id),
	KEY (file_id)
) 
;


CREATE TABLE TASK_OUTPUT_FILE
(
	task_id INTEGER NOT NULL,
	file_id INTEGER NOT NULL,
	PRIMARY KEY (task_id, file_id),
	KEY (task_id),
	KEY (file_id)
) 
;


CREATE TABLE THERMOSTAT
(
	id INTEGER NOT NULL AUTO_INCREMENT,
	algorithm VARCHAR(50),
	time_constant DOUBLE,
	PRIMARY KEY (id),
	UNIQUE UQ_THERMOSTAT_id(id)
) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE ATOM_OCCURRENCE ADD CONSTRAINT FK_ATOM_COMPOSITON_MOLECULE 
	FOREIGN KEY (molecule_id) REFERENCES MOLECULE (id)
;

ALTER TABLE ATOM_OCCURRENCE ADD CONSTRAINT FK_ATOM_COMPOSITON_PERIODIC_TABLE 
	FOREIGN KEY (element_id) REFERENCES BIOSIM_DICT_PERIODIC_TABLE (id)
;

ALTER TABLE BASIS_SET ADD CONSTRAINT FK_BASIS_SET_BASIS_SET_DICTIONARY 
	FOREIGN KEY (definition_id) REFERENCES BIOSIM_DICT_BASIS_SET (id)
;

ALTER TABLE BASIS_SET ADD CONSTRAINT FK_BASIS_SET_QM_METHOD 
	FOREIGN KEY (method_id) REFERENCES QM_PARAMETER_SET (id)
;

ALTER TABLE BIOSIM_DICT_BASIS_SET ADD CONSTRAINT FK_BASIS_SET_DICT_TYPE_DICT 
	FOREIGN KEY (type_id) REFERENCES BIOSIM_DICT_BASIS_SET_TYPE (id)
;

ALTER TABLE BIOSIM_DICT_CALCULATION ADD CONSTRAINT FK_CALCULATION_DICT_CALCULATION_TYPE_DICT 
	FOREIGN KEY (type_id) REFERENCES BIOSIM_DICT_CALCULATION_TYPE (id)
;

ALTER TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD_CLASS ADD CONSTRAINT FK_METHOD_CLASS_FAMILY 
	FOREIGN KEY (family_id) REFERENCES BIOSIM_DICT_COMPUTATIONAL_METHOD_FAMILY (id)
;

ALTER TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD_DICT_CLASS_DICT ADD CONSTRAINT FK_METHOD_CLASS_REL_CLASS 
	FOREIGN KEY (class_id) REFERENCES BIOSIM_DICT_COMPUTATIONAL_METHOD_CLASS (id)
;

ALTER TABLE BIOSIM_DICT_COMPUTATIONAL_METHOD_DICT_CLASS_DICT ADD CONSTRAINT FK_METHOD_CLASS_REL_METHOD 
	FOREIGN KEY (method_id) REFERENCES BIOSIM_DICT_COMPUTATIONAL_METHOD (id)
;

ALTER TABLE BIOSIM_DICT_DATA_GENERATION_METHOD ADD CONSTRAINT FK_STD_DATA_GEN_METHOD_TYPE 
	FOREIGN KEY (type_id) REFERENCES BIOSIM_DICT_DATA_GENERATION_METHOD_TYPE (id)
;

ALTER TABLE BIOSIM_DICT_FORCE_FIELD ADD CONSTRAINT FK_FF_DICTIONARY_FF_TYPE_DICTIONARY 
	FOREIGN KEY (type_id) REFERENCES BIOSIM_DICT_FORCE_FIELD_TYPE (id)
;

ALTER TABLE BIOSIM_DICT_PERIODIC_TABLE ADD CONSTRAINT FK_PERIODIC_TABLE_ELEMENT_FAMILY_DICT 
	FOREIGN KEY (family_id) REFERENCES BIOSIM_DICT_ELEMENT_FAMILY (id)
;

ALTER TABLE CALCULATION ADD CONSTRAINT FK_CALCULATION_TASK 
	FOREIGN KEY (task_id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE CITATION ADD CONSTRAINT FK_CITATION_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE EXPERIMENT_DATA_GENERATING_METHOD ADD CONSTRAINT FK_DATA_GEN_METHOD_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE EXPERIMENT_DATA_GENERATING_METHOD ADD CONSTRAINT FK_DATA_GEN_METHOD_STANDARD 
	FOREIGN KEY (reference_id) REFERENCES BIOSIM_DICT_DATA_GENERATION_METHOD (id)
;

ALTER TABLE EXPERIMENT_GRANT_REL ADD CONSTRAINT FK_EXPERIMENT_GRANT_REL_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE EXPERIMENT_GRANT_REL ADD CONSTRAINT FK_EXPERIMENT_GRANT_REL_GRANT 
	FOREIGN KEY (grant_id) REFERENCES RESEARCH_GRANT (id)
;

ALTER TABLE EXPERIMENT_PROCESS ADD CONSTRAINT FK_PROCESS_PROCESS_GROUP 
	FOREIGN KEY (process_group_id) REFERENCES EXPERIMENT_PROCESS_GROUP (id)
;

ALTER TABLE EXPERIMENT_PROCESS_DEPENDENCY ADD CONSTRAINT FK_PROCESS_DEP_FROM 
	FOREIGN KEY (dep_from) REFERENCES EXPERIMENT_PROCESS (id)
;

ALTER TABLE EXPERIMENT_PROCESS_DEPENDENCY ADD CONSTRAINT FK_PROCESS_DEP_TO 
	FOREIGN KEY (dep_to) REFERENCES EXPERIMENT_PROCESS (id)
;

ALTER TABLE EXPERIMENT_PROCESS_GROUP ADD CONSTRAINT FK_PROCESS_GROUP_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE EXPERIMENT_PROCESS_GROUP ADD CONSTRAINT FK_PROCESS_GROUP_MOLECULAR_SYSTEM 
	FOREIGN KEY (system_id) REFERENCES MOLECULAR_SYSTEM (id)
;

ALTER TABLE EXPERIMENT_PROCESS_GROUP_DEPENDENCY ADD CONSTRAINT FK_PROC_GRP_DEP_FROM 
	FOREIGN KEY (dep_from) REFERENCES EXPERIMENT_PROCESS_GROUP (id)
;

ALTER TABLE EXPERIMENT_PROCESS_GROUP_DEPENDENCY ADD CONSTRAINT FK_PROC_GRP_DEP_TO 
	FOREIGN KEY (dep_to) REFERENCES EXPERIMENT_PROCESS_GROUP (id)
;

ALTER TABLE EXPERIMENT_ROLE ADD CONSTRAINT FK_EXPERIMENT_ROLE_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE EXPERIMENT_SET_REL ADD CONSTRAINT FK_EXPERIMENT_SET_REL_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE EXPERIMENT_SET_REL ADD CONSTRAINT FK_EXPERIMENT_SET_REL_EXPERIMENT_SET 
	FOREIGN KEY (set_id) REFERENCES EXPERIMENT_SET (id)
;

ALTER TABLE EXPERIMENT_TASK ADD CONSTRAINT FK_EXPERIMENT_TASK_EXECUTION 
	FOREIGN KEY (execution_id) REFERENCES TASK_EXECUTION (id)
;

ALTER TABLE EXPERIMENT_TASK ADD CONSTRAINT FK_TASK_ENVIRONMENT 
	FOREIGN KEY (environment_id) REFERENCES COMPUTING_ENVIRONMENT (id)
;

ALTER TABLE EXPERIMENT_TASK ADD CONSTRAINT FK_TASK_PROCESS 
	FOREIGN KEY (process_id) REFERENCES EXPERIMENT_PROCESS (id)
;

ALTER TABLE EXPERIMENT_TASK ADD CONSTRAINT FK_TASK_SOFTWARE 
	FOREIGN KEY (software_id) REFERENCES SOFTWARE (id)
;

ALTER TABLE EXPERIMENT_TASK ADD CONSTRAINT FK_TASK_IMPLICIT_SOLVENT 
	FOREIGN KEY (implicit_solvent_id) REFERENCES IMPLICIT_SOLVENT_MODEL (id)
;

ALTER TABLE EXPERIMENT_TASK ADD CONSTRAINT FK_TASK_CONDITION_SET 
	FOREIGN KEY (condition_set_id) REFERENCES SIMULATED_CONDITION_SET (id)
;

ALTER TABLE EXPERIMENT_TASK_DEPENDENCY ADD CONSTRAINT FK_TASK_DEP_FROM 
	FOREIGN KEY (dep_from) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE EXPERIMENT_TASK_DEPENDENCY ADD CONSTRAINT FK_TASK_DEP_TO 
	FOREIGN KEY (dep_to) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE FILE ADD CONSTRAINT FK_FILE_FILE_SYSTEM 
	FOREIGN KEY (file_system_id) REFERENCES FILE_SYSTEM (id)
;

ALTER TABLE FILE_SYSTEM ADD CONSTRAINT FK_FILE_SYSTEM_EXPERIMENT 
	FOREIGN KEY (experiment_id) REFERENCES EXPERIMENT (id)
;

ALTER TABLE FORCE_FIELD ADD CONSTRAINT FK_FORCE_FIELD_FORCE_FIELD_DICTIONARY 
	FOREIGN KEY (reference_id) REFERENCES BIOSIM_DICT_FORCE_FIELD (id)
;

ALTER TABLE FORCE_FIELD ADD CONSTRAINT FK_FORCE_FIELD_MD_METHOD 
	FOREIGN KEY (method_id) REFERENCES MD_PARAMETER_SET (id)
;

ALTER TABLE MD_CONSTRAINT ADD CONSTRAINT FK_CONSTRAINT_MD_METHOD 
	FOREIGN KEY (method_id) REFERENCES MD_PARAMETER_SET (id)
;

ALTER TABLE MD_PARAMETER_SET ADD CONSTRAINT FK_MD_METHOD_BAROSTAT 
	FOREIGN KEY (barostat_id) REFERENCES BAROSTAT (id)
;

ALTER TABLE MD_TASK ADD CONSTRAINT FK_MD_TASK_EXPERIMENT_TASK 
	FOREIGN KEY (id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE MINIMIZATION_TASK ADD CONSTRAINT FK_MIN_TASK_MIN_METHOD 
	FOREIGN KEY (min_method_id) REFERENCES MINIMIZATION_PARAMETER_SET (id)
;

ALTER TABLE MINIMIZATION_TASK ADD CONSTRAINT FK_MINIMIZATION_TASK_EXPERIMENT_TASK 
	FOREIGN KEY (id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE MOLECULAR_SYSTEM_DEFINITION_FILE_SET ADD CONSTRAINT FK_FILE_SET_MOLECULAR_SYSTEM 
	FOREIGN KEY (system_id) REFERENCES MOLECULAR_SYSTEM (id)
;

ALTER TABLE MOLECULAR_SYSTEM_DEFINITION_FILE_SET ADD CONSTRAINT FK_SYSTEM_FILE_SET_FILE 
	FOREIGN KEY (file_id) REFERENCES FILE (id)
;

ALTER TABLE MOLECULAR_SYSTEM_STRUCT_REF ADD CONSTRAINT FK_SYSTEM_STRUCT_REF_STRUCTURE_REF 
	FOREIGN KEY (ref_id) REFERENCES STRUCTURE_REFERENCE (id)
;

ALTER TABLE MOLECULAR_SYSTEM_STRUCT_REF ADD CONSTRAINT FK_SYSTEM_STRUCT_REF_SYSTEM 
	FOREIGN KEY (system_id) REFERENCES MOLECULAR_SYSTEM (id)
;

ALTER TABLE MOLECULE ADD CONSTRAINT FK_MOLECULE_REF_MOLECULAR_SYSTEM 
	FOREIGN KEY (system_id) REFERENCES MOLECULAR_SYSTEM (id)
;

ALTER TABLE MOLECULE_FUNCTIONAL_GROUP_REL ADD CONSTRAINT FK_FUNCT_GROUP_REL_MOLECULE 
	FOREIGN KEY (molecule_id) REFERENCES MOLECULE (id)
;

ALTER TABLE MOLECULE_FUNCTIONAL_GROUP_REL ADD CONSTRAINT FK_FUNCTI_GROUP_REL_DICTIONARY 
	FOREIGN KEY (functional_group_id) REFERENCES BIOSIM_DICT_FUNCTIONAL_GROUP (id)
;

ALTER TABLE MOLECULE_IDENTIFIER ADD CONSTRAINT FK_MOLECULE_IDENTIFIER_MOLECULE 
	FOREIGN KEY (molecule_id) REFERENCES MOLECULE (id)
;

ALTER TABLE MOLECULE_ROLE ADD CONSTRAINT FK_MOLECULE_ROLE_MOLECULE 
	FOREIGN KEY (molecule_id) REFERENCES MOLECULE (id)
;

ALTER TABLE MOLECULE_STRUCT_REF ADD CONSTRAINT FK_MOLECULE_STRUCT_REF_MOLECULE 
	FOREIGN KEY (molecule_id) REFERENCES MOLECULE (id)
;

ALTER TABLE MOLECULE_STRUCT_REF ADD CONSTRAINT FK_MOLECULE_STRUCT_REF_STRUCT_REF 
	FOREIGN KEY (ref_id) REFERENCES STRUCTURE_REFERENCE (id)
;

ALTER TABLE PSEUDO_POTENTIAL ADD CONSTRAINT FK_PSEUDO_POTENTIAL_PARAM_SET 
	FOREIGN KEY (param_set_id) REFERENCES QM_PARAMETER_SET (id)
;

ALTER TABLE QM_PARAMETER_SET ADD CONSTRAINT FK_QM_METHOD_TO_DICTIONARY 
	FOREIGN KEY (std_method_id) REFERENCES BIOSIM_DICT_COMPUTATIONAL_METHOD (id)
;

ALTER TABLE QM_TASK ADD CONSTRAINT FK_QM_TASK_EXPERIMENT_TASK 
	FOREIGN KEY (id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE QMMM_TASK ADD CONSTRAINT FK_QMMM_TASK_EXPERIMENT_TASK 
	FOREIGN KEY (id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE QMMM_TASK ADD CONSTRAINT FK_QMMM_TASK_MD_METHOD 
	FOREIGN KEY (md_method_id) REFERENCES MD_PARAMETER_SET (id)
;

ALTER TABLE QMMM_TASK ADD CONSTRAINT FK_QMMM_TASK_QM_METHOD 
	FOREIGN KEY (qm_method_id) REFERENCES QM_PARAMETER_SET (id)
;

ALTER TABLE QMMM_TASK ADD CONSTRAINT FK_QMMM_TASK_QMMM_METHOD 
	FOREIGN KEY (qmmm_method_id) REFERENCES QMMM_PARAMETER_SET (id)
;

ALTER TABLE RESIDUE_OCCURRENCE ADD CONSTRAINT FK_RESIDUE_RESIDUE_DICT 
	FOREIGN KEY (definition_id) REFERENCES BIOSIM_DICT_RESIDUE (id)
;

ALTER TABLE RESIDUE_OCCURRENCE ADD CONSTRAINT FK_RESIDUE_RESIDUE_SEQUENCE 
	FOREIGN KEY (sequence_id) REFERENCES RESIDUE_SEQUENCE (id)
;

ALTER TABLE RESIDUE_SEQUENCE ADD CONSTRAINT FK_RESIDUE_SEQUENCE_MOLECULE 
	FOREIGN KEY (molecule_id) REFERENCES MOLECULE (id)
;

ALTER TABLE RESTRAINT ADD CONSTRAINT FK_RESTRAINT_MD_METHOD 
	FOREIGN KEY (method_id) REFERENCES MD_PARAMETER_SET (id)
;

ALTER TABLE TASK_INPUT_FILE ADD CONSTRAINT FK_INPUT_FILE_SET_TASK 
	FOREIGN KEY (task_id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE TASK_INPUT_FILE ADD CONSTRAINT FK_TASK_INPUT_FILE_SET_FILE 
	FOREIGN KEY (file_id) REFERENCES FILE (id)
;

ALTER TABLE TASK_OUTPUT_FILE ADD CONSTRAINT FK_OUTPUT_FILE_SET_TASK 
	FOREIGN KEY (task_id) REFERENCES EXPERIMENT_TASK (id)
;

ALTER TABLE TASK_OUTPUT_FILE ADD CONSTRAINT FK_TASK_OUTPUT_FILE_SET_FILE 
	FOREIGN KEY (file_id) REFERENCES FILE (id)
;
