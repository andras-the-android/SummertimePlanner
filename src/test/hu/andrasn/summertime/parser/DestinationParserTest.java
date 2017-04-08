package hu.andrasn.summertime.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import hu.andrasn.summertime.data.DestinationDto;

public class DestinationParserTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testEmpty() {
		List<String> inputDestinations = new ArrayList<>();		
		
		List<DestinationDto> result = new DestinationParserImpl().parse(inputDestinations);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testNull() {
		List<DestinationDto> result = new DestinationParserImpl().parse(null);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testItemsWithoutDependency() {
		List<String> inputDestinations = new ArrayList<>();	
		inputDestinations.add("x =>");
		inputDestinations.add("y=>");
		inputDestinations.add("z => ");
		
		List<DestinationDto> result = new DestinationParserImpl().parse(inputDestinations);
		DestinationDto dto;
		
		assertEquals(3, result.size());
		
		dto = result.get(0);
		assertEquals("x", dto.getName());
		assertTrue(dto.getDependency().isEmpty());
		
		dto = result.get(1);
		assertEquals("y", dto.getName());
		assertTrue(dto.getDependency().isEmpty());
		
		dto = result.get(2);
		assertEquals("z", dto.getName());
		assertTrue(dto.getDependency().isEmpty());
	}
	
	@Test
	public void testItemsWithDependency() {
		List<String> inputDestinations = new ArrayList<>();	
		inputDestinations.add("x => y");
		inputDestinations.add("y=>z");
		inputDestinations.add("z => ");
		inputDestinations.add("this is longer and =>has spaces");
		inputDestinations.add("@&% =>   123");
		
		List<DestinationDto> result = new DestinationParserImpl().parse(inputDestinations);
		DestinationDto dto;
		
		assertEquals(5, result.size());
		
		dto = result.get(0);
		assertEquals("x", dto.getName());
		assertEquals("y", dto.getDependency());
		
		dto = result.get(1);
		assertEquals("y", dto.getName());
		assertEquals("z", dto.getDependency());
		
		dto = result.get(2);
		assertEquals("z", dto.getName());
		assertTrue(dto.getDependency().isEmpty());
		
		dto = result.get(3);
		assertEquals("this is longer and", dto.getName());
		assertEquals("has spaces", dto.getDependency());
		
		dto = result.get(4);
		assertEquals("@&%", dto.getName());
		assertEquals("123", dto.getDependency());
		

	}
	
	@Test
	public void testItemNull() {
		List<String> inputDestinations = new ArrayList<>();	
		inputDestinations.add("x =>");
		inputDestinations.add(null);
		inputDestinations.add("z => ");
		
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("Input destination is null");
		
	    new DestinationParserImpl().parse(inputDestinations);
	}
	
	@Test
	public void testItemWithoutArrow() {
		List<String> inputDestinations = new ArrayList<>();	
		inputDestinations.add("x =>");
		inputDestinations.add("xy");
		inputDestinations.add("z => ");
		
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("Input destination doesn't contain \"=>\": xy");
		
	    new DestinationParserImpl().parse(inputDestinations);
	}
	
	@Test
	public void testItemTooMuchArrow() {
		List<String> inputDestinations = new ArrayList<>();	
		inputDestinations.add("x =>");
		inputDestinations.add("x=>y=>z");
		inputDestinations.add("z => ");
		
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("Input destination can contain only one \"=>\": x=>y=>z");
		
	    new DestinationParserImpl().parse(inputDestinations);
	}

}
