package com.inter.collections;
/*
 * 
 * 	Collections Framework
 * 																						Comparable (I)
 * 																						Comparator (I)
 * 
 * 
 * 											Iterable (interface)						Collections (C)
 * 												^
 * 												|
 * 											Collection (interface)								
 * 									/			|					\			
 * 							List (I)		Set (I)					Queue (I) FIFO							Map (I)
 * 							^					^						^									^
 * 							|					|						|									|
 * 						ArrayList (C)		HashSet (C)				PriorityQueue (C)					HashMap (C)
 * 						LinkedList (C)		TreeSet (C)				Deque (I)							TreeMap (C)
 * 						Stack (C) LIFO
 * 
 * 
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollectionsExample {

	public static void main(String[] args) {

		
		//Lists
		
		
		// ArrayList
		// expands the underlying array by 50%, not synchronized (NOT THREAD SAFE)
		// Vector
		// implements list, expands the underlying array by 100%, is synchronized
		// (THREAD SAFE)
		List<Integer> ints = new ArrayList<>();
		ints.add(4);
		ints.add(8);
		ints.add(4);
		System.out.println(ints);
		System.out.println(ints.get(1));
		System.out.println(ints.size());
		
		// LinkedList
		//  doubly linked list. It has references to the next and previous node. The underlying data structure 
		//		does not house a conventional array, it will add nodes to the list and reference it to the previous
		//		and next nodes. It implements the Deque interface, so retrieving from the first and last
		//		positions will be O(1), however retrieving from the middle of the list, will be the same as
		//		a normal array.
		List<String> strings = new LinkedList<>();
		strings.add("addeed");
		strings.add(0, "sodafighoiu");
		System.out.println(strings);
		System.out.println(strings.get(0));
//		strings.clear();
		System.out.println(strings);
		
		//Sets
		
		//HashSet
		//	Uses a underlying HashTable to organize and store the values
		Set<Integer> intset= new HashSet<>();
		intset.add(9);
		intset.add(10);
		intset.add(855);
		intset.add(1);
		intset.add(-55);
		intset.addAll(ints);
		boolean b = intset.add(65854654);		//returns a boolean determining whether or not the value was added
		System.out.println(intset);
		System.out.println(b);
		
		//TreeSet
		//	Uses a red black tree algorithm for sorting the values
		Set<String> sortedStrings = new TreeSet<>();
		sortedStrings.add("go time");
		sortedStrings.add("a bird in the sky");
		sortedStrings.add("Zoo goers");
		sortedStrings.add("&*&");
		sortedStrings.add(" ");
		System.out.println(sortedStrings);
		
		//Queue
		
		// PriorityQueue
		//		keeps everything organized by priority. Queue customarily keeps values in order as they are put
		//			in, this will keep them in order by priority. The first one out will be the one with the
		//			highest priority.
		PriorityQueue<String> pq = new PriorityQueue<>();
		pq.add("hey look mah");
		pq.add("no hands");
		System.out.println(pq.remove());
		System.out.println(pq.remove());
//		System.out.println(pq.remove());
//		ints.get(100);
		
		// Deque
		//		Double ended queue, can retrieve from either end.
		
		
		// Map
		
		// HashMap
		//		HashMap is like HashTable, but is not synchronized and is faster
		Map<String, String> hashedStrings = new HashMap<>();
		hashedStrings.put("key", "value");
		hashedStrings.put("another key", "another value");
		System.out.println(hashedStrings);
		
		// TreeMap
		//		Like TreeSet, is going to organize the elements according to their natural sorting order
		Map<String, String> dictionary = new TreeMap<>();
		dictionary.put("hammer", "a noun associated with a blunt object");
		dictionary.put("aardvark", "a difficult to spell animal");
		dictionary.put("car", "goes vroom vroom");
		System.out.println(dictionary);
		Set<String> keys = dictionary.keySet();
		System.out.println(keys);
		Collection<String> values = dictionary.values();
		System.out.println(values);
		System.out.println(values.getClass());
		
		System.out.println("\n\n\n");
		
		List<Student> students = new ArrayList<>();
		students.add(new Student(3.4, "susan","English"));
		students.add(new Student(3.0, "megan", "Basket Weaving"));
		students.add(new Student(3.3, "greg", "Computer Science"));
		students.add(new Student(3.2, "zachary", "American History"));
		System.out.println(students);
		Collections.sort(students);
		System.out.println(students);
		Collections.sort(students, new StudentNameComparator());
		System.out.println(students);
		
		
		//Iterator
		
		// list iterator has a previous method, it is able to traverse the list back and forth
		ListIterator<String> stringIterator = strings.listIterator();
//		System.out.println(stringIterator.next());
//		System.out.println(stringIterator.next());
//		System.out.println(stringIterator.previous());
//		System.out.println(stringIterator.previous());
////		System.out.println(stringIterator.previous());
//		System.out.println(stringIterator.hasNext());
		
		while(stringIterator.hasNext()) {
			System.out.println(stringIterator.next());
		}
		
		for(String s : strings) {
			System.out.println(s);
		}
		
		Iterator<String> setIterator = sortedStrings.iterator();
		System.out.println(setIterator.next());
		System.out.println(setIterator.next());
		System.out.println(setIterator.next());
		System.out.println(setIterator.next());

		while(setIterator.hasNext()) {
			System.out.println(setIterator.next());
		}
		
		
		Iterator<String> dictionaryIterator = dictionary.keySet().iterator();
		
		while(dictionaryIterator.hasNext()) {
			String key = dictionaryIterator.next();
			System.out.println("The definition for "+key+" is "+dictionary.get(key));
			
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
}
