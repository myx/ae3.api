package ru.myx.s4;

import ru.myx.ae3.common.Value;
import ru.myx.ae3.know.Guid;

/**
 * @author myx
 * 
 */
public interface StorageTransaction {
	/**
	 * 
	 */
	void cancel();
	
	/**
	 * @return boolean
	 * 
	 */
	Value<Boolean> commit();
	
	/**
	 * @return record change
	 */
	RecordChange createNewRecordChange();
	
	/**
	 * @param guid
	 * @return record change
	 */
	RecordChange createRecordChange(final Guid guid);
	
	/**
	 * @param record
	 * @return record change
	 */
	RecordChange createRecordChange(final Record record);
	
	/**
	 * @return nested change
	 */
	StorageTransaction createTransaction();
	
	/**
	 * @param record
	 */
	void drop(final Record record);
}
