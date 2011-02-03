/**
 * 
 */
package ru.myx.ae3.exec;

/**
 * @author myx
 * 
 */
public interface NamedArgumentsMapper {
	
	/**
	 * @return int
	 */
	int nameCount();
	
	/**
	 * @param key
	 * @return index, -1 when not found
	 */
	int nameIndex(final String key);
	
	/**
	 * @return array
	 */
	int[] nameIndicesAll();
	
	/**
	 * @return array
	 */
	String[] names();
	
}
