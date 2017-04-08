package hu.andrasn.summertime.formatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DestinationFormatterTest {
	
	private DestinationOutputFormatter target;
	
	@Before
	public void setUp() {
		target = new DestinationOutputFormatterImpl();
	}

	@Test
	public void testNull() {
		String result = target.format(null);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testFormat() {
		List<String> input = Arrays.asList(new String[] {"x", "y", "z"});
		
		String result = target.format(input.iterator());
		
		assertEquals("x => y => z", result);
	}

}
