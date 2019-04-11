package Search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSearchAlgs {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNullPattern() {
		char txt[]= "101".toCharArray();
		char pat[] = "".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}

	
	@Test
	void testFirstChar() {
		char txt[]= "11101".toCharArray();
		char pat[] = "1".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testLastChar() {
		char txt[]= "11178130".toCharArray();
		char pat[] = "0".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testMiddle() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "7".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testMiddleMore() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "178".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testMiddleAlmostTrue() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "171".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}

	@Test
	void testStartMore() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "111".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testStartAlmostTrue() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "1111".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	
	@Test
	void testEndMore() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "13".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	@Test
	void testEndAlmostTrue() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "8131".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	@Test
	void testNotExist() {
		char txt[]= "1117813".toCharArray();
		char pat[] = "0".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	
	@Test
	void testSpaceExist() {
		char txt[]= "111 813".toCharArray();
		char pat[] = " ".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	
	@Test
	void testSymbolExist() {
		char txt[]= "111(*813".toCharArray();
		char pat[] = "(*".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testSymbolWithText() {
		char txt[]= "111(*AS813".toCharArray();
		char pat[] = "1(*A".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertTrue(res);
	}
	
	@Test
	void testMoreThanText() {
		char txt[]= "ABUAG".toCharArray();
		char pat[] = "ABUAGAASS".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
	
	@Test
	void testNothingToSearchOn() {
		char txt[]= "".toCharArray();
		char pat[] = "ABUAGAASS".toCharArray();
		boolean res = SearchAlg.search(txt,pat);
		assertFalse(res);
	}
}
