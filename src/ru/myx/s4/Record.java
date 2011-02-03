package ru.myx.s4;

import ru.myx.ae3.know.Guid;

/**
 * @author myx
 * 
 */
public interface Record {
	/**
	 * @return date
	 */
	long getCreated();
	
	/**
	 * @return guid
	 */
	Guid getGuid();
}
