package hu.andrasn.summertime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * We need this custom linked list implementation because standard Java LinkedList provides O(n) search
 * and lacks the insertBefore feature.
 */
public class LinkedList<T> {
	
	/**
	 * The purpose of this map is the O(1) search
	 */
	private Map<T, ListItem<T>> searchMap = new HashMap<>();
	private ListItem<T> firstItem;
	private ListItem<T> lastItem;
	
	public void add(T item) {
		ListItem<T> newListItem = new ListItem<T>(item);
		searchMap.put(item,  newListItem);
		if (firstItem == null) {
			firstItem = newListItem;
			lastItem = firstItem;
		} else {
			lastItem.next = newListItem;
			newListItem.previous = lastItem;
			lastItem = newListItem;
		}
	}
	
	public void insertBefore(T item, T before) {
		ListItem<T> beforeListItem = searchMap.get(before);
		
		if (beforeListItem == null) {
			throw new IllegalArgumentException("No such item in the list: " + before);
		}
		
		ListItem<T> newListItem = new ListItem<T>(item);
		searchMap.put(item,  newListItem);
		
		if (beforeListItem.previous != null) {
			beforeListItem.previous.next = newListItem;
			newListItem.previous = beforeListItem.previous;
		} else {
			firstItem = newListItem;
		}		
		newListItem.next = beforeListItem;
		beforeListItem.previous = newListItem;
	}
	
	public boolean contains(T item) {
		return searchMap.containsKey(item);
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private ListItem<T> currentListItem = firstItem;

			@Override
			public boolean hasNext() {
				return currentListItem != null;
			}

			@Override
			public T next() {
				T value = currentListItem.value;
				currentListItem = currentListItem.next;
				return value;
			}
		};
	}
	
	private static class ListItem<T> {
		
		T value; 
		ListItem<T> previous; 
		ListItem<T> next;
		
		public ListItem(T current) {
			this.value = current;
		}
	}

}
