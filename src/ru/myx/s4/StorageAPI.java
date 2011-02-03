package ru.myx.s4;

import java.util.Collection;

import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.common.Value;
import ru.myx.ae3.know.Guid;

/**
 * @author myx
 * 
 */
public interface StorageAPI {
	
	/**
	 * @param record
	 * @return task
	 */
	Value<Void> commit(final Record record);
	
	/**
	 * @return root change
	 */
	StorageTransaction createChange();
	
	/**
	 * @param record
	 * @return binary
	 */
	Value<TransferCopier> getBinary(final Record record);
	
	/**
	 * @param guid
	 * @return record
	 */
	Value<? extends Record> getByGuid(final Guid guid);
	
	/**
	 * @param record
	 * @param linkageKey
	 *            null or key filter
	 * @param linkageMode
	 *            null or mode filter
	 * @return result
	 */
	Value<? extends Collection<? extends Record>> getContainers(
			final Record record,
			final String linkageKey,
			final LinkageMode linkageMode);
	
	/**
	 * @param record
	 * @param linkageKey
	 *            key to find
	 * @param linkageMode
	 *            null or mode filter
	 * @return result
	 */
	Value<? extends Record> getContentByKey(final Record record, final String linkageKey, final LinkageMode linkageMode);
	
	/**
	 * @param record
	 * @param linkageMode
	 *            null or mode filter
	 * @return result
	 */
	Value<? extends Collection<? extends Record>> getContents(final Record record, final LinkageMode linkageMode);
	
	/**
	 * @param guid
	 * @return record
	 */
	Value<? extends Record> getOrCreateByGuid(final Guid guid);
}
