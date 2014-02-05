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

package edu.utah.bmi.ibiomes.db.test;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBComputationalMethodClassDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBResidueDefinition;
import edu.utah.bmi.ibiomes.db.model.DBExperiment;
import edu.utah.bmi.ibiomes.db.model.DBExperimentProcess;
import edu.utah.bmi.ibiomes.db.model.DBExperimentProcessGroup;
import edu.utah.bmi.ibiomes.db.model.DBExperimentTask;
import edu.utah.bmi.ibiomes.db.model.method.DBBarostat;
import edu.utah.bmi.ibiomes.db.model.method.DBBasisSet;
import edu.utah.bmi.ibiomes.db.model.method.DBCalculation;
import edu.utah.bmi.ibiomes.db.model.method.DBConstraint;
import edu.utah.bmi.ibiomes.db.model.method.DBForceField;
import edu.utah.bmi.ibiomes.db.model.method.DBMinimizationParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DBMinimizationTask;
import edu.utah.bmi.ibiomes.db.model.method.DBRestraint;
import edu.utah.bmi.ibiomes.db.model.method.DBThermostat;
import edu.utah.bmi.ibiomes.db.model.method.DB_MDParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_MDTask;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMMMParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMMMTask;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMTask;
import edu.utah.bmi.ibiomes.db.model.structure.DBMolecularSystem;
import edu.utah.bmi.ibiomes.db.model.structure.DBMolecule;
import edu.utah.bmi.ibiomes.db.model.structure.DBSmallMolecule;
import edu.utah.bmi.ibiomes.db.model.structure.DBStructureReference;
import edu.utah.bmi.ibiomes.db.model.structure.bio.DBBiomolecule;
import edu.utah.bmi.ibiomes.db.model.structure.bio.DBResidueOccurrence;
import edu.utah.bmi.ibiomes.db.model.structure.bio.DBResidueSequence;

/**
 * Test suite for hibernate mappings
 * @author Julien Thibault, University of Utah
 *
 */
@Repository
public class TestHibernateExperimentManager {

	private final Logger logger = Logger.getLogger(TestHibernateExperimentManager.class);
	
	@Test
	public void testListExperiments() throws Exception {

		Session session = null;
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
			SessionFactory sessionFactory = (SessionFactory)context.getBean("sessionFactory");
			
			session = sessionFactory.openSession();
	        session.beginTransaction();
	        
			List<DBExperiment> experiments = session.createQuery("from DBExperiment").list();
			
			for (int i = 0; i < experiments.size(); i++)
			{
				DBExperiment exp = (DBExperiment) experiments.get(i);
		        logger.info("EXPERIMENT [" + exp.getId() + "]: " + exp.getName());
		        
		        List<DBExperimentProcessGroup> processGroups = exp.getProcessGroups();
				for (int g = 0; g < processGroups.size(); g++) {
					DBExperimentProcessGroup processGroup = (DBExperimentProcessGroup) processGroups.get(g);
			        logger.info("   PROCESS GROUP [" + processGroup.getId() + "]");
					if (processGroup.getDescription()!=null)
				        logger.debug("      Description: " + processGroup.getDescription());
			        
					getMolecularSystemInfo(processGroup.getMolecularSystem());
					
			        List<DBExperimentProcess> processes = processGroup.getProcesses();
					for (int p = 0; p < processes.size(); p++) {
						DBExperimentProcess process = (DBExperimentProcess) processes.get(p);
				        logger.info("   PROCESS [" + process.getId() + "]");
						if (process.getDescription()!=null)
					        logger.debug("      Description: " + process.getDescription());
		
				        List<DBExperimentTask> tasks = process.getTasks();
						for (int t = 0; t < tasks.size(); t++) {
							DBExperimentTask task = (DBExperimentTask) tasks.get(t);
					        logger.info("      TASK [" + task.getId() + "]");
					        
					        if (task.getDescription()!=null)
					        	logger.info("           Description: " + task.getDescription());
					        if (task.getSoftware()!=null)
					        	logger.info("           Software: " + task.getSoftware().getName() + " (" + task.getSoftware().getVersion() + ")");
					        
					        if (task.getConditionSet()!=null){
					        	logger.info("           Temperature: " + task.getConditionSet().getReferenceTemperature() + " K");
					        	logger.info("           Pressure:    " + task.getConditionSet().getReferencePressure() + " bar");
					        }
					        //Molecular dynamics task
					        if (task instanceof DB_MDTask){
					        	logger.info("           Method: MD");
					        	DB_MDTask mdTask = (DB_MDTask)task;
					        	getMdInfo(mdTask.getMdParameterSet());
					        }
					        //Minimization task
					        else if (task instanceof DBMinimizationTask){
					        	logger.info("           Method: Minimization");
					        	DBMinimizationTask minTask = (DBMinimizationTask)task;
					        	getMinimizationInfo(minTask.getMinimizationParameterSet());
					        }
					        //QM task
					        else if (task instanceof DB_QMTask){
					        	logger.info("           Method: QM");
					        	DB_QMTask qmTask = (DB_QMTask)task;
					        	getQmInfo(qmTask.getQmParameterSet());
					        }
					        //QM/MM task
					        else if (task instanceof DB_QMMMTask){
					        	logger.debug("           Method: QM/MM");
					        	DB_QMMMTask qmmmTask = (DB_QMMMTask)task;
					        	getQmmmInfo(qmmmTask.getQmmmParameterSet());
					        	getMdInfo(qmmmTask.getMdParameterSet());
					        	getQmInfo(qmmmTask.getQmParameterSet());
					        }
					        
					        //calculations
					        if (task.getCalculations()!=null){
				    			for (DBCalculation calculation : task.getCalculations()){
				    				logger.info("           Calculation: " + calculation.getName());
				    			}
				    		}
					    }
					}
			    }
				logger.debug("----------------------------------------------");
		    }
	        session.getTransaction().commit();
	        session.close();
		}
		catch (Exception e){
			e.printStackTrace();
			if (session!=null && session.isOpen())
				session.close();
			throw e;
		}
    }
	
	private void getMdInfo(DB_MDParameterSet params)
	{
		if (params!=null){
    		logger.info("           Time steps: " + params.getNumberOfTimeSteps());
    		logger.info("           Electrostatics: " + params.getElectrostatics());
    		logger.info("           MM integrator: " + params.getMmIntegrator());
    		logger.info("           Unit shape: " + params.getUnitShape());
    		logger.info("           Collision freq: " + params.getLangevinCollisionFrequency());
    		if (params.getConstraints()!=null){
    			Set<DBConstraint> constraints = params.getConstraints();
    			for (DBConstraint constraint : constraints){
    				logger.info("           Constraint: " + constraint.getAlgorithm());
    			}
    		}
    		if (params.getThermostat()!=null){
    			DBThermostat thermostat = params.getThermostat();
    			logger.info("           Thermostat name: " + thermostat.getAlgorithm());
    			logger.info("           Thermostat cst : " + thermostat.getTimeConstant());
    		}
    		if (params.getBarostat()!=null){
    			DBBarostat barostat = params.getBarostat();
    			logger.info("           Barostat name: " + barostat.getAlgorithm());
    			logger.info("           Barostat cst : " + barostat.getTimeConstant());
    		}
    		if (params.getRestraints()!=null){
    			Set<DBRestraint> restraints = params.getRestraints();
    			for (DBRestraint restraint : restraints){
    				logger.info("           Restraint: " + restraint.getRestraintType());
    			}
    		}
    		if (params.getForceFields()!=null){
    			Set<DBForceField> ffs = params.getForceFields();
    			for (DBForceField ff : ffs){
    				logger.info("           Force-field: " + ff.getName());
    				if (ff.getDefinition()!=null){
    					logger.info("           Force-field def: " + ff.getDefinition().getTerm());
    					if (ff.getDefinition().getType()!=null){
        					logger.info("           Force-field type: " + ff.getDefinition().getTerm());
    					}
    				}
    			}
    		}
		}
	}
	
	private void getQmInfo(DB_QMParameterSet params)
	{
    	if (params!=null){
    		logger.info("           QM method: " + params.getMethodName());
    		if (params.getQmMethod()!=null){
    			if (params.getQmMethod().getMethodClasses()!=null){
        			for (DBComputationalMethodClassDefinition methodClass : params.getQmMethod().getMethodClasses()){
	        			logger.info("           Method class: " + methodClass.getTerm());
        			}
        		}
    		}
    		if (params.getBasisSets()!=null){
    			for (DBBasisSet basisSet : params.getBasisSets()){
    				logger.info("           Basis set: " + basisSet.getName());
    				if (basisSet.getDefinition()!=null){
    					logger.info("           Basis set definition: " + basisSet.getDefinition().getTerm());
    				}
    			}
    		}
    	}
	}
	
	private void getMinimizationInfo(DBMinimizationParameterSet params){
		if (params!=null){
    		logger.info("           Minimization method: " + params.getMethod());
    		logger.info("           Iterations: " + params.getNumberOfIterations());
		}
	}
	
	private void getQmmmInfo(DB_QMMMParameterSet params)
	{
		if (params!=null){
    		logger.debug("           Boundary: " + params.getBoundaryTreatment());
    		logger.debug("           Electrostatics: " + params.getElectrostaticsInteractionType());
		}
	}
	
	private void getMolecularSystemInfo(DBMolecularSystem system){
		if (system!=null){
			logger.info("      SYSTEM [" + system.getId() + "] " + system.getName());
	    	logger.info("           Description: " + system.getDescription());
	    	logger.info("           Number of atoms: " + system.getAtomCount());
	    	logger.info("           Number of solvent molecules: " + system.getSolventCount());
	    	logger.info("           Number of ions: " + system.getIonCount());
	    	
	    	Set<DBMolecule> molecules = system.getMolecules();
	    	if (molecules!=null){
		    	for (DBMolecule molecule : molecules){
		    		getMoleculeInfo(molecule);
		    	}
	    	}
	    	
	    	Set<DBStructureReference> refs = system.getStructureReferences();
	    	if (refs!=null){
		    	for (DBStructureReference ref : refs){
		    		getStructureReferenceInfo(ref);
		    	}
	    	}
		}
	}
	
	private void getMoleculeInfo(DBMolecule molecule)
	{
		if (molecule!=null)
		{
			logger.info("      MOLECULE [" + molecule.getId() + "] " + molecule.getName());
        	logger.info("           Type: " + molecule.getMoleculeType());
	    	logger.info("           Number in system: " + molecule.getCount());
	    	logger.info("           Solvent? " + molecule.isSolvent());
	    	logger.info("           Description: " + molecule.getDescription());
	    	logger.info("           Number of atoms: " + molecule.getAtomCount());
	    	
	        if (molecule instanceof DBSmallMolecule){
	        	DBSmallMolecule compound = (DBSmallMolecule)molecule;
	        	logger.info("           Stoichiometry: " + compound.getStoichiometry());
	        }
	        else if (molecule instanceof DBBiomolecule){
	        	DBBiomolecule biomolecule = (DBBiomolecule)molecule;
	        	
	        	Set<DBResidueSequence> seqs = biomolecule.getSequences();
		    	if (seqs!=null){
			    	for (DBResidueSequence seq : seqs){
			    		logger.info("           Sequence (specific): " + seq.getSpecificChain());
			    		logger.info("           Sequence (normalized): " + seq.getNormalizedChain());
			    		
			    		List<DBResidueOccurrence> residues = seq.getResidues();
			    		if (residues!=null){
			    			for (DBResidueOccurrence residue : residues){
			    				logger.debug("               Residue: " +residue.getSpecificSymbol() + " [" + residue.getCount() + "]");
			    				DBResidueDefinition resDef = residue.getResidueDefinition();
			    				if (resDef!=null)
			    					logger.debug("                        " +resDef.getSymbol1() + " / " +resDef.getSymbol3() + " ( " +resDef.getType() +")");
			    				else logger.debug("                        " +residue.getAtomChain());
			    			}
			    		}
			    	}
		    	}
	        }
	        else {
	        	logger.info("           Type: unknown molecule");
	        }
	        
			Set<DBStructureReference> refs = molecule.getStructureReferences();
	    	if (refs!=null){
		    	for (DBStructureReference ref : refs){
		    		getStructureReferenceInfo(ref);
		    	}
	    	}
		}
	}
	
	private void getStructureReferenceInfo(DBStructureReference ref){
		if (ref!=null){
    		logger.debug("           Reference: " + ref.getEntryId() + "#" + ref.getDatabase());
		}
	}

}
