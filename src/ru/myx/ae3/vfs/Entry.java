package ru.myx.ae3.vfs;

import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.common.Value;

/**
 * @author myx
 * 
 * 
 */
public interface Entry {
	/**
	 * Returns ability to make changes in given node.
	 * 
	 * @return
	 */
	boolean canWrite();
	
	/**
	 * Creates cloned entry with parent equal to passed one
	 * 
	 * @param parent
	 * @param key
	 * @param mode
	 * 
	 * @return
	 */
	Entry clone(final Entry parent, final String key, final TreeLinkType mode);
	
	/**
	 * Makes given entry to be a binary. Equivalent to:
	 * 
	 * <pre>
	 * entry.getParent().toContainer().setContentBinary( entry.getKey(), entry.getMode(), binary )
	 * </pre>
	 * 
	 * @param binary
	 * @return
	 */
	Value<Boolean> doSetBinary(TransferCopier binary);
	
	/**
	 * Makes given entry to be collection. Equivalent to:
	 * 
	 * <pre>
	 * entry.getParent().toContainer().setContentContainer( entry.getKey(), entry.getMode() )
	 * </pre>
	 * 
	 * @return
	 */
	Value<Boolean> doSetContainer();
	
	/**
	 * Changes last-modified date of given entry.
	 * 
	 * @param lastModified
	 * @return
	 */
	Value<Boolean> doSetLastModified(final long lastModified);
	
	/**
	 * Makes given entry to be a primitive. Not every value can be primitive.
	 * Applicable only to values that can be stored in-line. Equivalent to:
	 * 
	 * <pre>
	 * entry.getParent().toContainer().setContentPrimitive( entry.getKey(), entry.getMode(), primitive )
	 * </pre>
	 * 
	 * @param primitive
	 * @return
	 */
	Value<Boolean> doSetPrimitive(Object primitive);
	
	/**
	 * Unlinks given entry. Equivalent to:
	 * 
	 * <pre>
	 * entry.getParent().toContainer().setContentUndefined( entry.getKey() )
	 * </pre>
	 * 
	 * @return
	 */
	Value<Boolean> doUnlink();
	
	/**
	 * Returns entry key. Returns NULL when this entry is a root entry.
	 * 
	 * @return key
	 */
	String getKey();
	
	/**
	 * Returns 'last-modified' date of given link.
	 * 
	 * @return
	 */
	long getLastModified();
	
	/**
	 * Returns location path in hierarchy. '' for root entry and so on separated
	 * by '/'.
	 * 
	 * @return location
	 */
	String getLocation();
	
	/**
	 * @return
	 */
	TreeLinkType getMode();
	
	/**
	 * Returns parent entry of this entry. Returns NULL when this entry is a
	 * root entry.
	 * 
	 * @return parent entry
	 */
	Entry getParent();
	
	/**
	 * Indicates whether this Entry has binary content.
	 * 
	 * @return
	 */
	boolean isBinary();
	
	/**
	 * Indicates whether this Entry is a collection.
	 * 
	 * @return
	 */
	boolean isContainer();
	
	/**
	 * Indicates whether this Entry exists on persistent storage.
	 * 
	 * @return
	 */
	boolean isExist();
	
	/**
	 * Indicates whether this Entry is hidden.
	 * 
	 * @return
	 */
	boolean isHidden();
	
	/**
	 * Indicates whether this Entry is a mount.
	 * 
	 * @return
	 */
	boolean isMount();
	
	/**
	 * Indicates whether this Entry has primitive content.
	 * 
	 * @return
	 */
	boolean isPrimitive();
	
	/**
	 * Works when isBinary() method returns TRUE.
	 * 
	 * Returns binary entry instance.
	 * 
	 * NOTE: use this method, "instanceof EntryBinary" will not work well, since
	 * implementation is free to implement any interfaces on actual entry for
	 * it's own purposes and/or memory utilization optimization.
	 * 
	 * @return
	 */
	EntryBinary toBinary();
	
	/**
	 * Works when isContainer() method returns TRUE.
	 * 
	 * Returns container entry instance.
	 * 
	 * NOTE: use this method, "instanceof EntryContainer" will not work well,
	 * since implementation is free to implement any interfaces on actual entry
	 * for it's own purposes and/or memory utilization optimization.
	 * 
	 * @return
	 */
	EntryContainer toContainer();
	
	/**
	 * Works when isMount() method returns TRUE.
	 * 
	 * Returns mount entry instance.
	 * 
	 * NOTE: use this method, "instanceof EntryMount" will not work well, since
	 * implementation is free to implement any interfaces on actual entry for
	 * it's own purposes and/or memory utilization optimization.
	 * 
	 * @return
	 */
	EntryMount toMount();
	
	/**
	 * Works when isPrimitive() method returns TRUE.
	 * 
	 * Returns primitive entry instance.
	 * 
	 * NOTE: use this method, "instanceof EntryPrimitive" will not work well,
	 * since implementation is free to implement any interfaces on actual entry
	 * for it's own purposes and/or memory utilization optimization.
	 * 
	 * @return
	 */
	EntryPrimitive toPrimitive();
}
