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

package edu.utah.bmi.ibiomes.db.model.structure.bio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Nucleic acid
 * @author Julien Thibault, University of Utah
 *
 */
@Entity
@DiscriminatorValue("Nucleic acid")
public class DBNucleicAcid extends DBBiomolecule {

	public DBNucleicAcid(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}