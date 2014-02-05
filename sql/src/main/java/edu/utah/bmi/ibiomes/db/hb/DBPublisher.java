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

package edu.utah.bmi.ibiomes.db.hb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utah.bmi.ibiomes.db.model.DBCitation;
import edu.utah.bmi.ibiomes.db.model.DBComputingEnvironment;
import edu.utah.bmi.ibiomes.db.model.DBExperiment;
import edu.utah.bmi.ibiomes.db.model.DBExperimentProcess;
import edu.utah.bmi.ibiomes.db.model.DBExperimentProcessGroup;
import edu.utah.bmi.ibiomes.db.model.DBExperimentTask;
import edu.utah.bmi.ibiomes.db.model.DBExperimentTaskExecution;
import edu.utah.bmi.ibiomes.db.model.DBFileSystem;
import edu.utah.bmi.ibiomes.db.model.DBResearchGrant;
import edu.utah.bmi.ibiomes.db.model.DBSoftware;
import edu.utah.bmi.ibiomes.db.model.method.DBBasisSet;
import edu.utah.bmi.ibiomes.db.model.method.DBCalculation;
import edu.utah.bmi.ibiomes.db.model.method.DBConstraint;
import edu.utah.bmi.ibiomes.db.model.method.DBForceField;
import edu.utah.bmi.ibiomes.db.model.method.DBImplicitSolventModel;
import edu.utah.bmi.ibiomes.db.model.method.DBMinimizationParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DBMinimizationTask;
import edu.utah.bmi.ibiomes.db.model.method.DBPseudoPotential;
import edu.utah.bmi.ibiomes.db.model.method.DBRestraint;
import edu.utah.bmi.ibiomes.db.model.method.DBSimulatedConditionSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_MDParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_MDTask;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMMMParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMMMTask;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMParameterSet;
import edu.utah.bmi.ibiomes.db.model.method.DB_QMTask;
import edu.utah.bmi.ibiomes.db.model.structure.DBAtomOccurrence;
import edu.utah.bmi.ibiomes.db.model.structure.DBMolecularSystem;
import edu.utah.bmi.ibiomes.db.model.structure.DBMolecule;

/**
 * Utility class for publication of experiments to the database
 * @author Julien Thibault, University of Utah
 *
 */
public class DBPublisher {
	
	private SessionFactory sessionFactory = null;
	
	public DBPublisher(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		this.sessionFactory = (SessionFactory)context.getBean("sessionFactory");
	}
	
	/**
	 * Add experiment information to database
	 * @param experiment Experiment
	 * @param fs File system hosting the experiment files
	 * @return ID of the experiment in the database
	 */
	public long publishExperiment(DBExperiment experiment, DBFileSystem fs, String owner)
	{
		Session session = null;
		Transaction tx = null;
	
		try{
	        //set owner
	        experiment.setOwner(owner);
	        
	        //set file collections
	        if (fs != null){
	        	Set<DBFileSystem> fsSet = new HashSet<DBFileSystem>();
	        	fsSet.add(fs);
	        	experiment.setFileCollections(fsSet);
	        	
	        	session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(fs);
		        tx.commit();
		        session.close();
	        }
	        
	        //grants
	    	Set<DBResearchGrant> grants = experiment.getReferencingGrants();
	        if (grants!=null){
	        	for (DBResearchGrant grant : grants) {
		        	session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(grant);
			        tx.commit();
			        session.close();
	        	}
	        }
	      
	        //citations
	    	Set<DBCitation> citations = experiment.getCitations();
	        if (grants!=null){
	        	for (DBCitation citation : citations) {
		        	session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(citation);
			        tx.commit();
			        session.close();
	        	}
	        }
	        
	        //process groups
	        List<DBExperimentProcessGroup> processGroups = experiment.getProcessGroups();
	        if (processGroups != null){
	        	List<DBExperimentProcessGroup> updatedProcessGroups = new ArrayList<DBExperimentProcessGroup>();
	        	for (DBExperimentProcessGroup processGroup : processGroups){
	        		DBExperimentProcessGroup updatedProcessGroup = publishExperimentProcessGroup(processGroup);
	        		updatedProcessGroups.add(updatedProcessGroup);
	        	}
	        	experiment.setProcessGroups(updatedProcessGroups);
	        }

	        //experiment
	        session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(experiment);
	        tx.commit();
	        
	        return experiment.getId();
		}
		catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			return -1;
        }finally{
	        if (session.isOpen())
	        	session.close();
        }
	}
	
	/**
	 * Add experiment process group information to database
	 * @param process Process
	 * @return Process
	 */
	public DBExperimentProcessGroup publishExperimentProcessGroup(DBExperimentProcessGroup group)
	{
		Session session = null;
		Transaction tx = null;

		//molecular system
		DBMolecularSystem system = group.getMolecularSystem();
        if (system!=null){
        	DBMolecularSystem updatedSystem = publishMolecularSystem(system);
        	group.setMolecularSystem(updatedSystem);
        }
        
		//set proceses
		List<DBExperimentProcess> processes = group.getProcesses();
		if (processes != null){
        	List<DBExperimentProcess> updatedProcesses = new ArrayList<DBExperimentProcess>();
        	for (DBExperimentProcess process : processes){
        		DBExperimentProcess updatedProcess = publishExperimentProcess(process);
        		updatedProcesses.add(updatedProcess);
        	}
        	group.setProcesses(updatedProcesses);
        }
		
		try{
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        
	        session.save(group);
	        tx.commit();
	        session.close();
	        
	        return group;
		}
		catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			return group;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}
	
	/**
	 * Add experiment process information to database
	 * @param process Process
	 * @return Process
	 */
	public DBExperimentProcess publishExperimentProcess(DBExperimentProcess process)
	{
		Session session = null;
		Transaction tx = null;
        
		//set tasks
		List<DBExperimentTask> tasks = process.getTasks();
        if (tasks != null){
        	List<DBExperimentTask> updatedTasks = new ArrayList<DBExperimentTask>();
        	for (DBExperimentTask task : tasks){
        		DBExperimentTask updatedTask = publishExperimentTask(task);
        		updatedTasks.add(updatedTask);
        	}
        	process.setTasks(updatedTasks);
        }
		
		try{
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        
	        session.save(process);
	        tx.commit();
	        session.close();
	        
	        return process;
		}
		catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			return process;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}

	private DBExperimentTask publishExperimentTask(DBExperimentTask task)
	{
		Session session = null;
		Transaction tx = null;
		try{
			DBImplicitSolventModel implicitModel = task.getImplicitSolventModel();
			DBSoftware software = task.getSoftware();
			DBComputingEnvironment env = task.getEnvironment();
			DBExperimentTaskExecution exec = task.getTaskExecution();
			Set<DBCalculation> calculations = task.getCalculations();
			DBSimulatedConditionSet conditions = task.getConditionSet();
			
	        if (implicitModel!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(implicitModel);
		        tx.commit();
		        session.close();	
	        }
	        
	        if (software!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(software);
		        tx.commit();
		        session.close();	
	        }
	        
	        if (env!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(env);
		        tx.commit();
		        session.close();
	        }
	        
	        if (exec!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(exec);
		        tx.commit();
		        session.close();	
	        }

	        if (calculations!=null){
	        	for (DBCalculation calculation : calculations){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(calculation);
			        tx.commit();
			        session.close();
	        	}
	        }
	        
	        if (conditions!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(conditions);
		        tx.commit();
		        session.close();	
	        }
	        
			if (task instanceof DB_MDTask){
				publishMDParameterSet(((DB_MDTask)task).getMdParameterSet());
			}
			else if (task instanceof DB_QMTask){
				publishQMParameterSet(((DB_QMTask)task).getQmParameterSet());
			}
			else if (task instanceof DB_QMMMTask){
				publishQMParameterSet(((DB_QMMMTask)task).getQmParameterSet());
				publishMDParameterSet(((DB_QMMMTask)task).getMdParameterSet());
				publishQMMMParameterSet(((DB_QMMMTask)task).getQmmmParameterSet());
			}
			else if (task instanceof DBMinimizationTask){
				publishMinParameterSet(((DBMinimizationTask)task).getMinimizationParameterSet());
			}
			
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(task);
	        tx.commit();
	        	        
	        return task;
		}
		catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			return task;
        }finally{
	        if (session.isOpen())
	        	session.close();
        }
	}
	
	private void publishQMMMParameterSet(DB_QMMMParameterSet parameterSet) {
		Session session = null;
		Transaction tx = null;
		if (parameterSet!=null){
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(parameterSet);
	        tx.commit();
	        session.close();
		}
	}

	private void publishMinParameterSet(DBMinimizationParameterSet parameterSet) {
		Session session = null;
		Transaction tx = null;
		if (parameterSet!=null){
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(parameterSet);
	        tx.commit();
	        session.close();
		}
		
	}

	private void publishQMParameterSet(DB_QMParameterSet parameterSet) {
		Session session = null;
		Transaction tx = null;
		if (parameterSet!=null){
			
			if (parameterSet.getBasisSets()!=null){
				for (DBBasisSet bs : parameterSet.getBasisSets()){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(bs);
			        tx.commit();
			        session.close();
				}
			}
			if (parameterSet.getPseudoPotentials()!=null){
				for (DBPseudoPotential potential : parameterSet.getPseudoPotentials()){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(potential);
			        tx.commit();
			        session.close();
				}
			}
			
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(parameterSet);
	        tx.commit();
	        session.close();
		}
	}

	private void publishMDParameterSet(DB_MDParameterSet parameterSet) {
		Session session = null;
		Transaction tx = null;
		if (parameterSet!=null)
		{
			//barostat
			if (parameterSet.getBarostat()!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(parameterSet.getBarostat());
		        tx.commit();
		        session.close();
			}
			//thermostat
			if (parameterSet.getThermostat()!=null){
				session = sessionFactory.openSession();
		        tx= session.beginTransaction();
		        session.save(parameterSet.getThermostat());
		        tx.commit();
		        session.close();
			}
			//restraints
			if (parameterSet.getRestraints()!=null){
				for (DBRestraint restraint : parameterSet.getRestraints()){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(restraint);
			        tx.commit();
			        session.close();
				}
			}
			//constraints
			if (parameterSet.getConstraints()!=null){
				for (DBConstraint constraint : parameterSet.getConstraints()){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(constraint);
			        tx.commit();
			        session.close();
				}
			}
			//force fields
			if (parameterSet.getForceFields()!=null){
				for (DBForceField ff : parameterSet.getForceFields()){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(ff);
			        tx.commit();
			        session.close();
				}
			}
			
			session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(parameterSet);
	        tx.commit();
	        session.close();
		}
	}

	private DBMolecularSystem publishMolecularSystem(DBMolecularSystem system)
	{
		Session session = null;
		Transaction tx = null;
		try{
	        Set<DBMolecule> molecules = system.getMolecules();
	        system.setMolecules(null);
	        
	        session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(system);
	        tx.commit();
	        session.close();
	        
	        if (molecules!=null){
	        	Set<DBMolecule> updatedMolecules = new HashSet<DBMolecule>();
	        	for (DBMolecule molecule : molecules){
			        updatedMolecules.add(publishMolecule(molecule));
	        	}
	        	system.setMolecules(updatedMolecules);
	        }
	        
	        session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.update(system);
	        tx.commit();
	        session.close();
	        
	        return system;
		}
		catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			return system;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}
	
	private DBMolecule publishMolecule(DBMolecule molecule)
	{
		Session session = null;
		Transaction tx = null;
		try{
	        Set<DBAtomOccurrence> atoms = molecule.getAtoms();
	        molecule.setAtoms(null);
	        
	        session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.save(molecule);
	        tx.commit();
	        session.close();	
	        
	        if (atoms!=null){
	        	for (DBAtomOccurrence atom : atoms){
					session = sessionFactory.openSession();
			        tx= session.beginTransaction();
			        session.save(atom);
			        tx.commit();
			        session.close();
	        	}
	        }
	        
	        molecule.setAtoms(atoms);
	        session = sessionFactory.openSession();
	        tx= session.beginTransaction();
	        session.update(molecule);
	        tx.commit();
	        session.close();		        
	        
	        return molecule;
		}
		catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			return molecule;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}
}
