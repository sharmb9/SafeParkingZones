package module.activity;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTheft {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void  testDisKnownResultAll() {
	List<Location> Theft = new ArrayList<Location>();

	Theft=TheftDis.result("data/dataTestDisKnownAll.csv",10);

		assertTrue(Theft.size()==12);
	}
	
	@Test
	public void  testDisKnownResultNone() {
		List<Location> Theft2 = new ArrayList<Location>();
		Theft2=TheftDis.result("data/dataTestResultNone.csv",10);
		assertTrue(Theft2.size()==0);
	}
	
	@Test
	public void  testDisKnownResultNotAll() {
		List<Location> Theft1 = new ArrayList<Location>();
		Theft1=TheftDis.result("data/dataTestKnownNotAll.csv",10);

		assertTrue(Theft1.size()==12);
	}
	
	@Test
	public void  testFreqKnownResultAllDistribute() {
		List<List<Location>> theftList = new ArrayList<List<Location>>();
		List<Location> theft = TheftDis.result("data/dataTestDisKnownAll.csv", 10);
		theftList = TheftFreq.dist(theft,10);

		assertTrue(theftList.get(0).size()==0);
		assertTrue(theftList.get(1).size()==1);
		assertTrue(theftList.get(2).size()==1);
		assertTrue(theftList.get(3).size()==1);
		assertTrue(theftList.get(4).size()==1);
		assertTrue(theftList.get(5).size()==1);
		assertTrue(theftList.get(6).size()==1);
		assertTrue(theftList.get(7).size()==1);
		assertTrue(theftList.get(8).size()==1);
		assertTrue(theftList.get(9).size()==4);

	}
	
	@Test
	public void  testFreqAll() {
		int theftFreq;
		List<Location> theft = TheftDis.result("data/dataTestDisKnownAll.csv", 10);
		
		theftFreq = TheftFreq.freqNor(theft);
		assertTrue(theftFreq == 12);
	}

}
