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

package edu.utah.bmi.ibiomes.db.dictionary.test;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utah.bmi.ibiomes.db.dictionary.model.DBAtomicElement;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBAtomicElementFamily;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBBarostatDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBBasisSetDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBBasisSetTypeDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBCalculationDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBCalculationTypeDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBComputationalMethodClassDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBComputationalMethodDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBComputationalMethodFamilyDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBConstraintAlgorithmDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBDataGeneratingMethodDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBEnsembleTypeDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBFileFormatDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBForceFieldDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBForceFieldTypeDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBFunctionalGroupDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBResidueDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBSoftwareDefinition;
import edu.utah.bmi.ibiomes.db.dictionary.model.DBThermostatDefinition;

/**
 * Test suite for MySQL dictionaries
 * @author Julien Thibault, University of Utah
 *
 */
public class HBMappingTest {

	private final Logger logger = Logger.getLogger(HBMappingTest.class);
	
	@Test
	public void testSelect() throws Exception
	{
		Session session = null;
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
			SessionFactory sessionFactory = (SessionFactory)context.getBean("sessionFactory");
			
			//atomic elements (periodic table)
			session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBAtomicElement> atomicElements = session.createQuery("from DBAtomicElement").list();
			for (DBAtomicElement atomicElt : atomicElements){
				DBAtomicElementFamily family = atomicElt.getFamily();
		    }
	        session.getTransaction().commit();
	        session.close();
	        
	        //barostats
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBBarostatDefinition> barostats = session.createQuery("from DBBarostatDefinition").list();
	        session.getTransaction().commit();
	        session.close();

	        //basis sets
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBBasisSetDefinition> bsets = session.createQuery("from DBBasisSetDefinition").list();
			for (DBBasisSetDefinition bset : bsets){
				DBBasisSetTypeDefinition type = bset.getBasisSetType();
		    }
			session.getTransaction().commit();
	        session.close();

	        //calculations
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBCalculationDefinition> calculations = session.createQuery("from DBCalculationDefinition").list();
			for (DBCalculationDefinition calculation : calculations){
				DBCalculationTypeDefinition type = calculation.getCalculationType();
		    }
			session.getTransaction().commit();
	        session.close();
	        
	        //calculations
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBComputationalMethodDefinition> methods = session.createQuery("from DBComputationalMethodDefinition").list();
			for (DBComputationalMethodDefinition method : methods){
				Set<DBComputationalMethodClassDefinition> types = method.getMethodClasses();
				for (DBComputationalMethodClassDefinition type : types){
					DBComputationalMethodFamilyDefinition family = type.getFamily();
				}
		    }
			session.getTransaction().commit();
	        session.close();

	        //constraint algorithms
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBConstraintAlgorithmDefinition> constraints = session.createQuery("from DBConstraintAlgorithmDefinition").list();
	        session.getTransaction().commit();
	        session.close();

	        //enhanced sampling methods
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBDataGeneratingMethodDefinition> samplMethods = session.createQuery("from DBDataGeneratingMethodDefinition").list();
	        session.getTransaction().commit();
	        session.close();
	        
	        //ensemble types
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBEnsembleTypeDefinition> ensembles = session.createQuery("from DBEnsembleTypeDefinition").list();
	        session.getTransaction().commit();
	        session.close();

	        //file formats
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBFileFormatDefinition> fileFormats = session.createQuery("from DBFileFormatDefinition").list();
	        session.getTransaction().commit();
	        session.close();
	        
	        //force fields
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBForceFieldDefinition> ffs = session.createQuery("from DBForceFieldDefinition").list();
			for (DBForceFieldDefinition ff : ffs){
				DBForceFieldTypeDefinition type = ff.getType();
		    }
			session.getTransaction().commit();
	        session.close();

	        //functional groups
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBFunctionalGroupDefinition> fgs = session.createQuery("from DBFunctionalGroupDefinition").list();
	        session.getTransaction().commit();
	        session.close();

	        //residues
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBResidueDefinition> residues = session.createQuery("from DBResidueDefinition").list();
	        session.getTransaction().commit();
	        session.close();
	        
	        //thermostats
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBThermostatDefinition> thermostats = session.createQuery("from DBThermostatDefinition").list();
	        session.getTransaction().commit();
	        session.close();

	        //software
	        session = sessionFactory.openSession();
	        session.beginTransaction();
			List<DBSoftwareDefinition> softwares = session.createQuery("from DBSoftwareDefinition").list();
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
}
