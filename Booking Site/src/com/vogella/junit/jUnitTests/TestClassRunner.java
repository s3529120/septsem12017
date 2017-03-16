package com.vogella.junit.jUnitTests;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.sql.PreparedStatement;
import Controller.DatabaseController;
import Model.DatabaseModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Run all tests in alphabetical order
public class TestClassRunner {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Create DB connection. Possibly add test values into a test table.
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//Drop DB tables and close connection.
	}
	
	
	@Test 
	public void test() {
		 
		fail("Not yet implemented");
	}

}
