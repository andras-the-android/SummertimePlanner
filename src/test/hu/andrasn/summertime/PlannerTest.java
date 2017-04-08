package hu.andrasn.summertime;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import hu.andrasn.summertime.formatter.DestinationOutputFormatterImpl;
import hu.andrasn.summertime.parser.DestinationParserImpl;


public class PlannerTest {
	
	private Planner target;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() {
		target = new Planner(new DestinationParserImpl(), new DestinationOutputFormatterImpl());
	}

	@Test
	public void testEmpty() {
		List<String> input = new ArrayList<>();		
		
		String result = target.plan(input);
		
		Assert.assertEquals("", result);
	}
	
	@Test
	public void testSingleItem() {
		List<String> input = new ArrayList<>();
		input.add("x =>");
		
		String result = target.plan(input);
		
		Assert.assertEquals("x", result);
	}
	
	@Test
	public void testWithoutDependency() {
		List<String> input = new ArrayList<>();
		input.add("x => ");
		input.add("y => ");
		input.add("z => ");
		
		String result = target.plan(input);
		
		Assert.assertEquals("x => y => z", result);
	}
	
	@Test
	public void testWithOneDependency() {
		List<String> input = new ArrayList<>();
		input.add("x => ");
		input.add("y => z");
		input.add("z => ");
		
		String result = target.plan(input);
		
		Assert.assertEquals("x => z => y", result);
	}
	
	@Test
	public void testWithMultipleDependencies() {
		List<String> input = new ArrayList<>();
		input.add("u => ");
		input.add("v => w");
		input.add("w => z");
		input.add("x => u");
		input.add("y => v");
		input.add("z =>");
		
		String result = target.plan(input);
		
		Assert.assertEquals("u => z => w => v => x => y", result);
	}
	
	@Test
	public void testWithMoreComplexData() {
		List<String> input = new ArrayList<>();
		input.add("a => c");
		input.add("b => a");
		input.add("c => d");
		input.add("d => ");
		input.add("e => d");
		input.add("f => ");
		input.add("g => h");
		input.add("h => i");
		input.add("i => ");
		input.add("j => ");
		
		String result = target.plan(input);
		
		Assert.assertEquals("d => c => a => b => e => f => i => h => g => j", result);
	}
	
	@Test
	public void testLoopDetection() {
		List<String> input = new ArrayList<>();
		input.add("a => b");
		input.add("b => c");
		input.add("c => a");
		
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("A loop detected among the destinations" );
		
		target.plan(input);
	}
}
