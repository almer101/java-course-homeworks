package hr.fer.zemris.java.hw05.collections;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

/**
 * This class represents a hash table which stores
 * table entries, and each table entry is modeled
 * with the nested static class TableEntry. The 
 * problem of overflow is managed with linked list.
 * (i.e. this hash table stores references to the lists)
 * 
 * @author ivan
 * @param <K> 
 * 		the type of the key.
 * @param <V> 
 * 		the type of the value.
 * 		
 *
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{

	//=========================Properties==========================
	
	/**
	 * A table of the list heads. It is used for storing 
	 * {@link TableEntry} objects.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * The size number of the {@link TableEntry} elements in
	 * the table.
	 */
	private int size = 0;
	
	/**
	 * The capacity of the hash table (i.e. number of slots)
	 */
	private int capacity;
	
	/**
	 * This variable counts how many modifications were
	 * made on this table.
	 */
	private int modificationCount = 0;
	
	/**
	 * A default size of the table.
	 */
	private static final int DEFAULT_SIZE = 16;
	
	/**
	 * A maximum threshold how much can <code>size/capacity</code> 
	 * can be before resizing the table.
	 */
	private static final double THRESHOLD = 0.75;
	
	//=========================Constructors==============================
	
	/**
	 * A default constructor which initializes a <code>table</code> 
	 * with 16 slots. 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[])(new TableEntry[DEFAULT_SIZE]);
		capacity = DEFAULT_SIZE;
	}
	
	/**
	 * This constructor initializes a {@link SimpleHashtable}. A size 
	 * of the table will be the first number of type 2^n larger or 
	 * equal to size. (e.g. if the specified size is 30, the size of
	 * the table will be 2^5 = 32).
	 * 
	 * @param capacity
	 * 		initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("The given capacity "
					+ "must be a number larger or equal to 1! Was + "
					+ capacity);
		}
		int tableCapacity = 1;
		while(tableCapacity < capacity) {
			tableCapacity *= 2;
		}
		table = (TableEntry<K, V>[])(new TableEntry[tableCapacity]);
		this.capacity = tableCapacity;
	}
	
	//=======================Helper classes===================================
	
	/**
	 * This class models a entry in the hash table. Each
	 * entry is of type (key, value).
	 * 
	 * @author ivan
	 *
	 * @param <K>
	 * 		the type of the key.
	 * @param <V>
	 * 		the type of the value.
	 */
	public static class TableEntry<K,V> {
		
		/**
		 * The key of the entry.
		 */
		private K key;
		
		/**
		 * The value of the entry.
		 */
		private V value;
		
		/**
		 * The next entry in the table slot when the overflow
		 * happens.
		 */
		private TableEntry<K, V> next;

		/**
		 * A constructor which initializes properties of an object
		 * which is instance of this class.
		 * 
		 * @param key
		 * 		initial value of the <code>key</code> property.
		 * 
		 * @param value
		 * 		initial value of the <code>value</code> property.
		 */
		public TableEntry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = null;
		}
		
		/**
		 * This method returns the value of the <code>value</code>
		 * property.
		 * 
		 * @return
		 * 		the value of the <code>value</code>.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * This method sets the value of the <code>value</code> 
		 * property to the specified value.
		 * 
		 * @param value
		 * 		the value to set the property to.
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * This method returns the value of the <code>key</code>
		 * property.
		 * 
		 * @return
		 * 		the value of the <code>key</code>.
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public int hashCode() {
			return key.hashCode();
		}
		
		@Override
		public String toString() {
			return key.toString() +
					"=" + value.toString();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof TableEntry<?, ?>)) return false;
			TableEntry<?, ?> other = (TableEntry<?, ?>)obj;
			return key.equals(other.key);
		}
	}
	
	/**
	 * This is the implementation of {@link Iterator} and this 
	 * iterator iterates through the table from the first slot
	 * to the last, and in the list from the head to the tail.
	 * 
	 * @author ivan
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> { 
		
		/**
		 * This variable is the copy of the outer class' variable
		 * modificationCount and it is used to determine whether the
		 * table was modified using something other than this class'
		 * methods.
		 */
		private int modificationCount;
		
		/**
		 * This variable represents a flag which indicates whether
		 * the element can be removed or not. For example, if the
		 * element is removed the next method called can not be remove().
		 */
		private boolean canRemove = false;
		
		/**
		 * The slot index.
		 */
		private int slot;
		
		/**
		 * The current entry this iterator is on.
		 */
		private TableEntry<K, V> currentEntry;
		
		/**
		 * A constructor which initializes values of the
		 * object which is instance of this class.
		 * 
		 */
		public IteratorImpl() {
			modificationCount = SimpleHashtable.this.modificationCount;
			canRemove = false;
			slot = -1;
			currentEntry = null;
		}
		
		/**
		 * This method returns true if it has elements to return
		 * to user.
		 * 
		 */
		public boolean hasNext() {
			checkModificationCount();
			int helperSlot = slot;
			
			TableEntry<K, V> e = findNext();
			slot = helperSlot;
			return e != null;
		}
		
		/**
		 * This method returns a next element from the table.
		 * 
		 */
		public TableEntry<K,V> next() {
			checkModificationCount();
			if(!hasNext()) {
				throw new NoSuchElementException("There are "
						+ "no more entries in the table!");
			}
			currentEntry = findNext();
			canRemove = true;
			return currentEntry;
		}
		
		/**
		 * This method removes the current element from the table. 
		 * 
		 */
		public void remove() {
			checkModificationCount();
			if(!canRemove) {
				throw new IllegalStateException("The method remove can not "
						+ "be called more times in a row!");
			}
			SimpleHashtable.this.remove(currentEntry.key);
			modificationCount++;
			canRemove = false;
		} 
		
		/**
		 * This method finds and returns the next element in the table.
		 * If there are no more elements returns null.
		 * 
		 * @return	
		 * 		the next element in the table if such exists;
		 * 		<code>null</code> otherwise.
		 */
		private TableEntry<K,V> findNext() {
			if(currentEntry != null && currentEntry.next != null) {
				return currentEntry.next;
			}
			slot++;
			while(slot < capacity) {
				if(table[slot] == null) {
					slot++;
					continue;
				}
				return table[slot];
			}
			return null;
		}
		
		/**
		 * This method checks whether the table was modified from
		 * the outside and if yes than it throws an exception.
		 * 
		 * @throws ConcurrentModificationException
		 * 		if the table was modified from the outside.
		 */
		private void checkModificationCount() {
			if(modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("The table "
						+ "was modified from outside!");
			}
		}
	}
	
	//====================Method implementations=====================================
	
	/**
	 * This method puts a new pair of (key, value) to the table.
	 * 
	 * @param key
	 * 		the key to put in the table.
	 * 
	 * @param value
	 * 		the value assigned to the key.
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("The given "
					+ "key must not be null!");
		}
		ensureCapacity();
		if(!containsKey(key)) {
			//add a new element
			addNewEntry(new TableEntry<>(key, value));
			modificationCount++;
			size++;
		} else {
			//update existing
			updateValue(key, value);
		}
	}
	
	/**
	 * This method updates the existing entry with the
	 * specified key to the specified value.
	 * 
	 * @param key
	 * 		key of the existing entry.
	 * 
	 * @param value
	 * 		the new value to update the entry with.
	 */
	private void updateValue(K key, V value) {
		TableEntry<K, V> entry = getEntry(key);
		entry.setValue(value);
	}

	/**
	 * This method returns an entry with the specified key.
	 * The method returns null if the entry with specified
	 * key does not exist.
	 * 
	 * @param key
	 * 		the key of the entry.
	 * 
	 * @return
	 * 		an entry with the specified key.
	 */
	private TableEntry<K, V> getEntry(Object key) {
		int index = getHash(key);
		TableEntry<K, V> entry = table[index];
		
		while(entry != null) {
			if(key.equals(entry.key)) {
				return entry;
			}
			entry = entry.next;
		}
		return null;
	}
	
	/**
	 * This is a method which adds a specified entry to the table.
	 * 
	 * @param entry
	 * 		an entry to be added.
	 */
	private void addNewEntry(TableEntry<K, V> newEntry) {
		int index = getHash(newEntry.key);
		TableEntry<K, V> e = table[index];
		
		if(e == null) {
			table[index] = newEntry;
			return;
		}
		while(e.next != null) {
			e = e.next;
		}
		e.next = newEntry;
	}

	/**
	 * This is a method which returns a value of the entry
	 * which has a specified key. If the entry with
	 * the specified key is not contained within the 
	 * table, then returns <code>null</code>.
	 * 
	 * @param key
	 * 		a key of the entry.
	 * 
	 * @return
	 * 		a value of the entry with specified key.
	 * 		<code>null</code> if such does not 
	 *		exist.
	 */
	public V get(Object key) {
		if(!containsKey(key)) return null; 
		TableEntry<K, V> entry = getEntry(key);
		return entry.value;
	}
	
	/**
	 * This method checks if a certain key is contained
	 * within a hash table.
	 * 
	 * @param key
	 * 		a key for which it has to be checked if it is contained
	 * 		in the table.
	 * 
	 * @return
	 * 		<code>true</code> if the key is contained in the table; 
	 * 		<code>false</code> otherwise
	 */
	public boolean containsKey(Object key) {
		if(key == null) return false;
		TableEntry<K, V> entry = getEntry(key);
		return entry != null;
	}
	
	/**
	 * This method returns an index of the slot in the table
	 * where the object should be put. The index is calculated
	 * using hash value modulo size.
	 * 
	 * @param obj
	 * 		object for which to generate a hash value.
	 * 
	 * @return
	 * 		the index of the table slot.
	 */
	private int getHash(Object obj) {
		return Math.abs(obj.hashCode()) % table.length;
	}
	
	/**
	 * This method returns a number of elements in a table.
	 * 
	 * @return
	 * 		a number of elements in the table.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * This method checks if the specified value is in
	 * the table. If the null value is entered then searches
	 * for the entry with null value.
	 * 
	 * @param value
	 * 		the value for which has to be checked if
	 * 		it is in the table.
	 * 
	 * @return
	 * 		returns <code>true</code> if the value
	 * 		is contained in the table; 
	 * 		<code>false</code> otherwise.
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length; i++) {
			if(checkSlot(value, i)) return true;
		}
		return false;
	}
	
	/**
	 * This method checks if the specified value is 
	 * contained in the slot of the table with the
	 * specified index
	 * 
	 * @param value
	 * 		value for which it has to be checked
	 * 		if it is in the table.
	 * 
	 * @param index
	 * 		the index of the slot of the table.
	 * 
	 * @return
	 * 		<code>true</code> if the value is
	 * 		contained in the slot; <code>false</code>
	 * 		otherwise.
	 */
	private boolean checkSlot(Object value, int index) {
		TableEntry<K, V> entry = table[index];
		if(entry == null) return false;
		
		while(entry != null) {
			if(value == null && entry.getValue() == null) 
				return true; 
			if(entry.getValue().equals(value)) return true;
			entry = entry.next;
		}
		return false;
	}

	/**
	 * This method removes an entry with the specified
	 * key.
	 * 
	 * @param key
	 * 		key of the entry to be removed.
	 */
	public void remove(Object key) {
		if(key == null || !containsKey(key)) return;
		
		int index = getHash(key);
		TableEntry<K, V> entry = table[index];

		//in case the wanted entry is first in the slot.
		if(key.equals(entry.key)) {
			table[index] = entry.next;
			size--;
			modificationCount++;
			return;
		}
		//in case it is not the first entry in the slot.
		TableEntry<K, V> previous = entry;
		while(entry != null) {
			if(key.equals(entry.key)) {
				previous.next = entry.next;
				size--;
				modificationCount++;
				return;	
			}
			previous = entry;
			entry = entry.next;
		}
	}
	
	/**
	 * This is a method which checks whether the hash
	 * table is empty and returns true if it is and 
	 * false otherwise.
	 * 
	 * @return
	 * 		<code>true</code> if the table is empty;
	 * 		<code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * This method returns a {@link String} representation
	 * of this table.
	 * 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean printComma = false;
		Iterator<TableEntry<K, V>> it = iterator();
		
		sb.append("[");
		while(it.hasNext()) {
			TableEntry<K, V> entry = it.next();
			if(!printComma) {
				printComma = true;
				sb.append(entry.toString());
				continue;
			}
			sb.append(", " + entry.toString());
		}
		sb.append("]");
		return sb.toString();
	}

//	/**
//	 * This method appends all the entries from the slot 
//	 * on specified index to the specified {@link StringBuilder}
//	 * 
//	 * @param sb
//	 * 		a {@link StringBuilder} to which to append the
//	 * 		entries.
//	 * 
//	 * @param index
//	 * 		an index of the slot of the table.
//	 */
//	private void appendEntries(StringBuilder sb, int index) {
//		TableEntry<K, V> entry = table[index];
//		if(entry == null) return;
//		
//		while(entry != null) {
//			if(sb.toString().equals("[")) {
//				sb.append(entry.getKey() + "=" + entry.getValue());
//				continue;
//			}
//			sb.append(", " + entry.getKey() + "=" + entry.getValue());
//			entry = entry.next;
//		}
//	}
	
	/**
	 * This method removes all the elements from the table.
	 * Puts all slot values to <code>null</code> value so 
	 * the garbage collector can collect those unreferenced
	 * table entries.
	 * 
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	
	/**
	 * This method ensures that the number of entries does not pass
	 * <code>THRESHOLD * capacity</code>. If it does it allocates
	 * a new table with double capacity and transfers all the entries
	 * to the new table.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void ensureCapacity() {
		if(size + 1 >= capacity * THRESHOLD) {
			capacity *= 2;
			size = 0;
			TableEntry<K, V>[] helperTable = table;
			table = (TableEntry<K, V>[])(new TableEntry[capacity]);
			transferEntries(helperTable);
		}
	}

	/**
	 * This method transfers the entries from this table to the specified new
	 * table.
	 * 
	 * @param helperTable
	 * 		a table to transfer the entries to.
	 */
	private void transferEntries(TableEntry<K, V>[] helperTable) {
		for(int i = 0; i < helperTable.length; i++) {
			transferSlot(helperTable[i]);
		}
	}

	/**
	 * This method transfers all the elements from the specified
	 * slot to this table.
	 * 
	 * @param tableEntry
	 * 		a slot to transfer.
	 */
	private void transferSlot(TableEntry<K, V> tableEntry) {
		if(tableEntry == null) return;
		while(tableEntry != null) {
			put(tableEntry.key, tableEntry.value);
			tableEntry = tableEntry.next;
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
