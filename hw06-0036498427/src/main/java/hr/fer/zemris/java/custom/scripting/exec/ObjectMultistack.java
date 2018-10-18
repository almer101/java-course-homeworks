package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a special kind
 * of map which allows users to store 
 * more different values for the same key.
 * The map maps a {@link String} to the first
 * element in the stack.
 * 
 * @author ivan
 *
 */
public class ObjectMultistack {

	//=======================Properties==================================
	
	/**
	 * This is the map which stores the elements. The key is
	 * a {@link String} and the value is the "stack" of 
	 * {@link MultistackEntry}s. The mapped value is the 
	 * first element on the stack.
	 */
	Map<String, MultistackEntry> collection = new HashMap<>();
	
	//=======================Constructor=================================
	
	/**
	 * This is a default constructor.
	 * 
	 */
	public ObjectMultistack() {
		super();
	}
	
	//======================Helper class=================================
	
	private static class MultistackEntry {
		
		/**
		 * This is the value stored in this entry.
		 */
		private ValueWrapper value;
		
		/**
		 * This is the reference to the next 
		 * {@link MultistackEntry}, in this case the one 
		 * below the this one on the stack.
		 */
		private MultistackEntry next;

		/**
		 * This constructor gets one argument which is the
		 * {@link ValueWrapper} which encapsulates some value.
		 * 
		 * @param value
		 * 		the initial value of the property 
		 * 		<code>value</code>.
		 */
		public MultistackEntry(ValueWrapper value) {
			Objects.requireNonNull(value, "The given value "
					+ "wrapper must not be null!");
			this.value = value;
			this.next = null;
		}
	}
	
	//=====================Method implementations========================
	
	/**
	 * This method puts a new pair (key, value) to the internal
	 * map. The key in this case is <code>name</code> argument.
	 * The value is the {@link ValueWrapper} provided. It pushes 
	 * the specified <code>valueWrapper</code> to the stack which
	 * is under specified key.
	 * 
	 * @param name
	 * 		the key of the stack with entries.
	 * 
	 * @param valueWrapper
	 * 		the value of the first element on the stack under
	 * 		that key.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(name, "The given key must not "
				+ "be null!");
		Objects.requireNonNull(valueWrapper, "The given value "
				+ "wrapper must not be null!");
		
		MultistackEntry entry = new MultistackEntry(valueWrapper);
		//if no element is found under the key the method get() 
		//will return null and that is the wanted behavior.
		entry.next = collection.get(name);
		collection.put(name, entry);
	}
	
	/**
	 * This method removes and returns the value of the first 
	 * element from the stack which is mapped to the 
	 * specified key <code>name</code>. Throws an exception 
	 * if the given argument is <code>null</code> or there is
	 * no stack for that key to be popped from.
	 * 
	 * @param name
	 * 		the key of the entry.
	 * 
	 * @return
	 * 		the value of value of the first element on the 
	 * 		stack which is under the specified key.
	 */
	public ValueWrapper pop(String name) {
		ValueWrapper value = peek(name);
		MultistackEntry entry = collection.get(name).next;
		collection.remove(name);
		collection.put(name, entry);
		return value;
	}
	
	/**
	 * This method returns the value of the first 
	 * element from the stack which is mapped to the 
	 * specified key <code>name</code>. Throws an exception 
	 * if the given argument is <code>null</code> or there 
	 * is no stack for that key to be popped from.
	 * 
	 * @param name
	 * 		the key of the entry.
	 * 
	 * @return
	 * 		the value of value of the first element on the 
	 * 		stack which is under the specified key.
	 * 
	 * @throws NullPointerException
	 * 		if the specified name is <code>null</code>
	 * 
	 * @throws EmptyStackException
	 * 		if there is no elements for the given key(name).
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name, "The given key "
				+ "must not be null!");
		if(!collection.containsKey(name)) {
			throw new EmptyStackException("The empty stack "
					+ "cannot be popped!");
		}
		
		return collection.get(name).value;
	}
	
	/**
	 * This method checks if there is an existing element 
	 * (stack) under the specified key <code>name</code> and 
	 * returns a boolean value accordingly.
	 * 
	 * @param name
	 * 		the key of the element in the map.
	 * 
	 * @return
	 * 		<code>true</code> if there are existing elements 
	 * 		(stack) for the specified key. <code>false</code>
	 * 		otherwise.
	 */
	public boolean isEmpty(String name) {
		Objects.requireNonNull(name);
		return collection.get(name) == null;
	}
}
