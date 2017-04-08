package hu.andrasn.summertime;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void testAdd() {
		LinkedList<String> target = new LinkedList<>();
		target.add("first");
		target.add("second");
		target.add("third");
		
		Iterator<String> iterator = target.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("first", iterator.next());
		
		assertTrue(iterator.hasNext());
		assertEquals("second", iterator.next());
		
		assertTrue(iterator.hasNext());
		assertEquals("third", iterator.next());
		
		assertFalse(iterator.hasNext());		
	}
	
	@Test
	public void testInsertBefore() {
		LinkedList<String> target = new LinkedList<>();
		target.add("first");		
		target.add("third");
		target.insertBefore("second", "third");
		
		Iterator<String> iterator = target.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("first", iterator.next());
		
		assertTrue(iterator.hasNext());
		assertEquals("second", iterator.next());
		
		assertTrue(iterator.hasNext());
		assertEquals("third", iterator.next());
		
		assertFalse(iterator.hasNext());		
	}
	
	@Test
	public void testContains() {
		LinkedList<String> target = new LinkedList<>();
		target.add("first");		
		target.add("third");
		target.insertBefore("second", "third");
		
		assertTrue(target.contains("first"));
		assertTrue(target.contains("second"));
		assertTrue(target.contains("third"));
		assertFalse(target.contains("fourth"));
	}	

}
