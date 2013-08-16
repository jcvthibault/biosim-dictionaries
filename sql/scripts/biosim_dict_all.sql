-- ==================================================================
-- Author:  Julien Thibault, University of Utah
-- Created: 07/18/2013
-- Description: run this script to create the database schema and 
--              populate the tables
-- 
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
-- ==================================================================

-- create schema
source biosim_dict_create_schema.sql;

-- populate tables
source biosim_dict_populate.sql;

