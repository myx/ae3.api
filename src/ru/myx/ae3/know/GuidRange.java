package ru.myx.ae3.know;

/**
 * @author myx
 * 
 */
public interface GuidRange {
	/**
	 * Lower limit of given sub-range.
	 * 
	 * @param index
	 *            - range index
	 * @return guid
	 */
	Guid rangeLeft(int index);
	
	/**
	 * Upper limit of given sub-range.
	 * 
	 * Note that result of this method can be same instance as of corresponding
	 * rangeLeft() when given subrange is valid for one GUID only. This behavior
	 * can be used for faster range checks.
	 * 
	 * @param index
	 *            - range index
	 * @return guid
	 */
	Guid rangeRight(int index);
	
	/**
	 * Number of subranges in this range.
	 * 
	 * @return integer
	 */
	int subRangeCount();
}
