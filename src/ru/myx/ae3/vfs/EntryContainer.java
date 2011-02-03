package ru.myx.ae3.vfs;

import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.common.Value;

/**
 * @author myx
 * 
 */
public interface EntryContainer extends Entry {
	/**
	 * Works when isCollection() method returns TRUE.
	 * 
	 * Should return NULL when isCollection() method returns FALSE.
	 * 
	 * @param type
	 * 
	 * @return
	 */
	Value<? extends Entry[]> getContentCollection(final TreeReadType type);
	
	/**
	 * Works when isCollection() method returns TRUE.
	 * 
	 * When "defaultMode" argument is NULL or canWrite is FALSE, should return
	 * NULL when given content element doesn't exist. Otherwise, valid entry
	 * will be returned and isExist() method can be used instead.
	 * 
	 * Should return NULL when isCollection() method returns FALSE.
	 * 
	 * @param key
	 * @param defaultMode
	 * @return
	 */
	Value<? extends Entry> getContentElement(final String key, final TreeLinkType defaultMode);
	
	/**
	 * Returns TRUE when given container doesn't contain persistent (not cache
	 * nor product) entries.
	 * 
	 * @return
	 */
	boolean isContainerEmpty();
	
	/**
	 * @param key
	 * @param mode
	 * @param binary
	 * @return
	 */
	Value<?> setContentBinary(final String key, final TreeLinkType mode, final TransferCopier binary);
	
	/**
	 * Creates container (directory) child entry.
	 * 
	 * @param key
	 * @param mode
	 * @return
	 */
	Value<?> setContentContainer(final String key, final TreeLinkType mode);
	
	/**
	 * Not every value can be primitive. Applicable only to values that can be
	 * stored in-line.
	 * 
	 * @param key
	 * @param mode
	 * @param primitive
	 * @return
	 */
	Value<?> setContentPrimitive(final String key, final TreeLinkType mode, final Object primitive);
	
	/**
	 * Deletes child entry.
	 * 
	 * @param key
	 * @param mode
	 * @return
	 */
	Value<?> setContentUndefined(final String key);
}
