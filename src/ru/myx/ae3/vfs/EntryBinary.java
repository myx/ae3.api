package ru.myx.ae3.vfs;

import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.common.Value;

/**
 * @author myx
 * 
 */
public interface EntryBinary extends Entry {
	
	/**
	 * @return
	 */
	Value<TransferCopier> getBinaryContent();
	
	/**
	 * @return
	 */
	long getBinaryContentLength();
}
