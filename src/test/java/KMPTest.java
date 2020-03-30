import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import searchAlgorithms.KMP;

public class KMPTest {

	private String text = "";
	private String pattern = "";
	private char[] textArr;
	private char[] patternArr;
	private List<Integer> found;
	private KMP kmp;

	// Don't include @Before because we want the text and pattern strings
	// initialized in the JUnit test method first
	public void setUp() {
		found = new ArrayList<Integer>();
		textArr = new char[text.length()];
		patternArr = new char[pattern.length()];
		for (int i = 0; i < text.length(); i++) {
			textArr[i] = text.charAt(i);
		}
		for (int j = 0; j < pattern.length(); j++) {
			patternArr[j] = pattern.charAt(j);
		}
		kmp = new KMP(textArr);
		found = kmp.search(patternArr);
	}

	@After
	public void tearDown() {
		text = "";
		pattern = "";
		textArr = null;
		patternArr = null;
	}

	@Test
	public void simpleTest() {
		text = "ABABDABACDABABCABAB";
		pattern = "ABABCABAB";
		setUp();
		assertTrue(found.contains(10));
		assertTrue(found.size() == 1);
		tearDown();
	}

	@Test
	public void manyWordTest() {
		text = "The symptom patient's symptoms are symptoms.";
		pattern = "symptom";
		setUp();
		assertTrue(found.contains(4));
		assertTrue(found.contains(22));
		assertTrue(found.contains(35));
		assertTrue(found.size() == 3);
		tearDown();
	}

	@Test
	public void notFoundTest() {
		text = "The symptom patient's symptoms are symptoms.";
		pattern = "ache";
		setUp();
		assertTrue(found.size() == 0);
		tearDown();
	}

	@Test
	public void emptyTest() {
		text = "";
		pattern = "ache";
		setUp();
		assertTrue(found.size() == 0);
		tearDown();
	}
}
