package hr.fer.zemris.java.hw05.db;

/**
 * This class defines public static final
 * variables of type {@link IComparisonOperator}.
 * Each of these variables represents one
 * comparison.
 * 
 * @author ivan
 *
 */
public class ComparisonOperators {

	//==================Public static final variables===================
	/**
	 * This comparison returns true if the 
	 * first value is less than the second one.
	 * (e.g. "Boat" < "Car")
	 * 
	 */
	public static final IComparisonOperator LESS =
			(v1, v2) -> v1.compareTo(v2) < 0;
			
	/**
	 * This comparison returns <code>true</code> 
	 * if the first value is less than or equals to
	 * the second one, and <code>false</code> otherwise.
	 * (e.g. "Boat" <= "Car")
	 * 
	 */
	public static final IComparisonOperator LESS_OR_EQUALS =
			(v1, v2) -> v1.compareTo(v2) <= 0;
		
	/**
	 * This comparison returns <code>true</code> 
	 * if the first value is greater than the second one, 
	 * and <code>false</code> otherwise.
	 * (e.g. "Music" > "Airplane")
	 * 
	 */
	public static final IComparisonOperator GREATER =
			(v1, v2) -> v1.compareTo(v2) > 0;
	
	/**
	 * This comparison returns <code>true</code> 
	 * if the first value is greater than or equals to 
	 * the second one, and <code>false</code> otherwise.
	 * (e.g. "Music" >= "Airplane")
	 * 
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS =
			(v1, v2) -> v1.compareTo(v2) >= 0;
		
	/**
	 * This comparison returns <code>true</code> 
	 * if the first value is equals to the second one, 
	 * and <code>false</code> otherwise.
	 * (e.g. "Car" == "Car")
	 * 
	 */
	public static final IComparisonOperator EQUALS =
			(v1, v2) -> v1.compareTo(v2) == 0;
		
	/**
	 * This comparison returns <code>true</code> 
	 * if the first value is not equals to the second one, 
	 * and <code>false</code> otherwise.
	 * (e.g. "Car" != "CAR")
	 * 
	 */
	public static final IComparisonOperator NOT_EQUALS =
			(v1, v2) -> v1.compareTo(v2) != 0;
		
	private static final char STAR_CHAR = '*';
	
	/**
	 * This comparison returns true if the first argument 
	 * has the pattern specified by the second argument.
	 * 
	 */
	public static final IComparisonOperator LIKE = 
			new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value, String pattern) {
			int valueIndex = 0;
			int patternIndex = 0;
			
			if(findNextStar(0, pattern) == -1) 
				return value.equals(pattern);
			
			while(valueIndex <= value.length() - 1) {
				if(pattern.charAt(patternIndex) == STAR_CHAR) {
					String sequence = nextSequence(
							patternIndex, pattern);
					//if the Star char is the last char in the 
					//pattern then everything from value after 
					//the current index will be accepted!
					if(sequence.length() == 0 && 
							patternIndex >= pattern.length() - 1) 
						return true;
					int newIndex = value
							.substring(valueIndex)
							.indexOf(sequence);
					
					if(newIndex == -1) return false;
					patternIndex += sequence.length() + 1;
					valueIndex += newIndex + sequence.length();
					if(patternIndex > pattern.length() - 1) {
						if(valueIndex < value.length()) {
							return checkRest(valueIndex, 
									value, sequence); 
						}
						break;
					}
					continue;
					
				} else if(value.charAt(valueIndex) != 
						pattern.charAt(patternIndex)) return false;
				
				valueIndex++;
				patternIndex++;
			}
			return true;
		}
	};
	
	//=================================Helper methods==============================
	/**
	 * This method searches for the next 
	 * 
	 * @param index
	 * @param s
	 * @return
	 */
	private static String nextSequence(int startIndex, String s) {
		//skip the star index
		startIndex++;
		int endIndex = findNextStar(startIndex, s);
		
		if(endIndex == -1) {
			return s.substring(startIndex);
		} else {
			return s.substring(startIndex, endIndex);
		}
		
	}
	
	/**
	 * This method finds the index of the first occurrence of
	 * the char '*' and returns it. If non is found returns -1.
	 * 
	 * @param index
	 * 		search starting index in the specified {@link String}
	 * 
	 * @param s
	 * 		the {@link String} in which we search for a '*'
	 * 
	 * @return
	 * 		index of the first occurrence of the '*'. If none
	 * 		found returns -1.
	 */
	private static int findNextStar(int index, String s) {
		for(int i = index, len = s.length(); i < len; i++) {
			if(s.charAt(i) == STAR_CHAR) return i;
			
		}
		return -1;
	}

	/**
	 * This method checks if the rest of the specified 
	 * string value is equal to the specified sequence.
	 * The rest of the value which has to be checked 
	 * starts at specified index
	 * 
	 * @param valueIndex
	 * 		the starting index of the part of the 
	 * 		value which has to be checked.
	 * 
	 * @param value
	 * 		a string to be checked.
	 * 
	 * @param sequence
	 * 		a sequence which is used as a reference.
	 */
	private static boolean checkRest(int valueIndex, String value, String sequence) {
		return sequence.equals(value.substring(valueIndex));		
	}
}
