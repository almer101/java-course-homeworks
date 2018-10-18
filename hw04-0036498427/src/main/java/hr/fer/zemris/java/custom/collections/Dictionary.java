package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a dictionary, which stores
 * data as (key, value), where key is unique and 
 * only one such can be in a dictionary, whereas
 * different keys can have same values.
 * 
 * @author ivan
 *
 */
public class Dictionary {


	//================Properties of the class============================
	
	/**
	 * This array indexed collection is used for storing
	 * <code>Entry</code> objects (i.e. this Collection
	 * is used for implementation dictionary.)
	 * 
	 */
	ArrayIndexedCollection dictionary;
	
	//====================Constructors====================================
	
	/**
	 * This constructor initializes the {@link Dictionary} object.
	 * Sets the <code>dictionary</code> property to the initial
	 * value.
	 * 
	 */
	public Dictionary() {
		dictionary = new ArrayIndexedCollection();
	}
	
	//======================Helper class==================================
	
	/**
	 * This class represents one entry in the dictionary
	 * it stores two elements : <code>key</code> and 
	 * <code>value</code>.
	 * 
	 * @author ivan
	 *
	 */
	private static class Entry {
		
		/**
		 * The key of the entry.
		 * 
		 */
		private Object key;
		
		/**
		 * The value of the entry with this
		 * <code>key</code>
		 * 
		 */
		private Object value;

		/**
		* This constructor receives two values - 
		* the value of the <code>key</code> and
		* the value of the <code>value</code>. Initializes
		* the properties of the instance of this class with
		* specified values.
		* 
		* @param key
		* 		initial value to set the <code>key</code> to.
		* 
		* @param value
		* 		initial value to set the <code>value</code> to.
		*/
		public Entry(Object key, Object value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Entry)) return false;
			return key.equals(((Entry)obj).key);
		}
	}
	
	//==================Method implementations================================
	
	/**
	 * This method checks if the dictionary is empty
	 * and returns <code>true</code> if it is, and <code>false</code>
	 * otherwise.
	 * 
	 * @return
	 * 		<code>true</code> if the dictionary is empty;
	 * 		<code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}
	
	/**
	 * This method gets and returns the size of the
	 * dictionary.
	 * 
	 * @return
	 * 		the size of the dictionary.
	 */
	public int size() {
		return dictionary.size();
	}
	
	/**
	 * This method removes all the elements from the 
	 * dictionary.
	 * 
	 */
	public void clear() {
		dictionary.clear();
	}
	
	/**
	 * This method puts new {@link Entry} with the 
	 * values specified by parameters <code>key</code> and
	 * <code>value</code>. If the dictionary already has 
	 * an entry with the key <code>key</code> then it puts 
	 * the entry with the new values to the dictionary.
	 * 
	 * @param key
	 * 		the key of the {@link Entry} object which is to be put
	 * 		in the dictionary.
	 * 
	 * @param value
	 * 		the value of the {@link Entry} object which is to be put
	 * 		in the dictionary.
	 */
	public void put(Object key, Object value) {
		if(key == null) {
			throw new NullPointerException("The key must not be null!");
			
		}
		Entry newEntry = new Entry(key, value);
		
		if(!dictionary.contains(newEntry)) {
			dictionary.add(newEntry);
			
		} else {
			dictionary.remove(newEntry);
			dictionary.add(newEntry);
			
		}
	}
	
	/**
	 * This method gets the value of the {@link Entry} from the 
	 * dictionary with the key <code>key</code>. If there is no
	 * such entry then returns <code>null</code>.
	 * 
	 * @param key
	 * 		the key of the entry which has the desired value.
	 * 
	 * @return
	 * 		the value of the entry with the key <code>key</code>
	 */
	public Object get(Object key) {
		Entry e = new Entry(key, null);
		
		int index = dictionary.indexOf(e);
		if(index == -1) {
			return null;
		}
		
		return ((Entry)dictionary.get(index)).value;
	}
}
