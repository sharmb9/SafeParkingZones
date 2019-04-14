package Search;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSearch {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNullPattern() {
		char txt[]= "101".toCharArray();
		char pat[] = "".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}

	
	@Test
	public void testFirstChar() {
		char txt[]= "11101".toCharArray();
		char pat[] = "1".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testLastChar() {
		char txt[]= "11178130".toCharArray();
		char pat[] = "0".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testMiddle() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "7".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testMiddleMore() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "178".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testMiddleAlmostTrue() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "171".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}

	@Test
	public void testStartMore() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "111".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testStartAlmostTrue() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "1111".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	
	@Test
	public void testEndMore() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "13".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	@Test
	public void testEndAlmostTrue() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "8131".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	@Test
	public void testNotExist() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "0".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	
	@Test
	public void testSpaceExist() {
		char txt[]= "111 813".toCharArray();
		char pat[] = " ".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	
	@Test
	public void testSymbolExist() {
		char txt[]= "111(*813".toCharArray();
		char pat[] = "(*".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testSymbolWithText() {
		char txt[]= "111(*AS813".toCharArray();
		char pat[] = "1(*A".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	public void testMoreThanText() {
		char txt[]= "ABUAG".toCharArray();
		char pat[] = "ABUAGAASS".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	
	@Test
	public void testNothingToSearchOn() {
		char txt[]= "".toCharArray();
		char pat[] = "ABUAGAASS".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}

}
