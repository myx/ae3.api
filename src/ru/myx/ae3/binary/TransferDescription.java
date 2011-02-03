package ru.myx.ae3.binary;

/**
 * @author myx
 *
 */
/**
 * 
 * FIXME - implement BaseObject
 * 
 * Traffic description. Some of this traffic parameters may be used in IO
 * subroutine implementations to statisfy your demands.
 * 
 */
public interface TransferDescription {
	/**
	 * 
	 */
	TransferDescription	LOW_UNLIMITED		= new TransferDescription() {
												@Override
												public Object getAttachment() {
													return null;
												}
												
												@Override
												public int getNetReadByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getNetReadByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getNetWriteByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getNetWriteByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getPriority() {
													return TransferDescription.PC_LOW;
												}
												
												@Override
												public int getStorageReadByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getStorageWriteByteRateLimit() {
													return -1;
												}
												
												@Override
												public boolean isReplaceable(final TransferDescription description) {
													return true;
												}
												
												@Override
												public boolean isWritable() {
													return false;
												}
												
												@Override
												public Object setAttachment(final Object attachment) {
													return null;
												}
												
												@Override
												public int setNetReadByteRateLimit(final int netByteReadLimit) {
													return -1;
												}
												
												@Override
												public int setNetWriteByteRateLimit(final int netByteWriteLimit) {
													return -1;
												}
												
												@Override
												public int setStorageReadByteRateLimit(final int storageByteReadLimit) {
													return -1;
												}
												
												@Override
												public int setStorageWriteByteRateLimit(final int storageByteWriteLimit) {
													return -1;
												}
												
												@Override
												public void statsNetRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsNetWritten(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageWritten(final int bytes) {
													// empty
												}
												
												@Override
												public String toString() {
													return "LOW_PRIORITY";
												}
											};
	
	/**
	 * 
	 */
	TransferDescription	IDLE_UNLIMITED		= new TransferDescription() {
												@Override
												public Object getAttachment() {
													return null;
												}
												
												@Override
												public int getNetReadByteRateLimit() {
													return 0;
												}
												
												@Override
												public int getNetReadByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getNetWriteByteRateLimit() {
													return 0;
												}
												
												@Override
												public int getNetWriteByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getPriority() {
													return TransferDescription.PC_IDLE;
												}
												
												@Override
												public int getStorageReadByteRateLimit() {
													return 0;
												}
												
												@Override
												public int getStorageWriteByteRateLimit() {
													return 0;
												}
												
												@Override
												public boolean isReplaceable(final TransferDescription description) {
													return true;
												}
												
												@Override
												public boolean isWritable() {
													return false;
												}
												
												@Override
												public Object setAttachment(final Object attachment) {
													return null;
												}
												
												@Override
												public int setNetReadByteRateLimit(final int netByteReadLimit) {
													return 0;
												}
												
												@Override
												public int setNetWriteByteRateLimit(final int netByteWriteLimit) {
													return 0;
												}
												
												@Override
												public int setStorageReadByteRateLimit(final int storageByteReadLimit) {
													return 0;
												}
												
												@Override
												public int setStorageWriteByteRateLimit(final int storageByteWriteLimit) {
													return 0;
												}
												
												@Override
												public void statsNetRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsNetWritten(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageWritten(final int bytes) {
													// empty
												}
												
												@Override
												public String toString() {
													return "IDLE_PRIORITY";
												}
											};
	
	/**
	 * 
	 */
	int					PC_FATAL			= 512;
	
	/**
	 * 
	 */
	int					PC_HIGH				= 64;
	
	/**
	 * 
	 */
	int					PC_DEFAULT			= 8;
	
	/**
	 * 
	 */
	int					PC_LOW				= 1;
	
	/**
	 * 
	 */
	int					PC_IDLE				= 0;
	
	/**
	 * 
	 */
	TransferDescription	HIGH_UNLIMITED		= new TransferDescription() {
												@Override
												public Object getAttachment() {
													return null;
												}
												
												@Override
												public int getNetReadByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getNetReadByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getNetWriteByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getNetWriteByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getPriority() {
													return TransferDescription.PC_HIGH;
												}
												
												@Override
												public int getStorageReadByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getStorageWriteByteRateLimit() {
													return -1;
												}
												
												@Override
												public boolean isReplaceable(final TransferDescription description) {
													return true;
												}
												
												@Override
												public boolean isWritable() {
													return false;
												}
												
												@Override
												public Object setAttachment(final Object attachment) {
													return null;
												}
												
												@Override
												public int setNetReadByteRateLimit(final int netByteReadLimit) {
													return -1;
												}
												
												@Override
												public int setNetWriteByteRateLimit(final int netByteWriteLimit) {
													return -1;
												}
												
												@Override
												public int setStorageReadByteRateLimit(final int storageByteReadLimit) {
													return -1;
												}
												
												@Override
												public int setStorageWriteByteRateLimit(final int storageByteWriteLimit) {
													return -1;
												}
												
												@Override
												public void statsNetRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsNetWritten(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageWritten(final int bytes) {
													// empty
												}
												
												@Override
												public String toString() {
													return "HIGH_PRIORITY";
												}
											};
	
	/**
	 * 
	 */
	TransferDescription	DEFAULT_UNLIMITED	= new TransferDescription() {
												@Override
												public Object getAttachment() {
													return null;
												}
												
												@Override
												public int getNetReadByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getNetReadByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getNetWriteByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getNetWriteByteRateLimitLeft(final long time) {
													return 0;
												}
												
												@Override
												public int getPriority() {
													return TransferDescription.PC_DEFAULT;
												}
												
												@Override
												public int getStorageReadByteRateLimit() {
													return -1;
												}
												
												@Override
												public int getStorageWriteByteRateLimit() {
													return -1;
												}
												
												@Override
												public boolean isReplaceable(final TransferDescription description) {
													return true;
												}
												
												@Override
												public boolean isWritable() {
													return false;
												}
												
												@Override
												public Object setAttachment(final Object attachment) {
													return null;
												}
												
												@Override
												public int setNetReadByteRateLimit(final int netByteReadLimit) {
													return -1;
												}
												
												@Override
												public int setNetWriteByteRateLimit(final int netByteWriteLimit) {
													return -1;
												}
												
												@Override
												public int setStorageReadByteRateLimit(final int storageByteReadLimit) {
													return -1;
												}
												
												@Override
												public int setStorageWriteByteRateLimit(final int storageByteWriteLimit) {
													return -1;
												}
												
												@Override
												public void statsNetRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsNetWritten(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageRead(final int bytes) {
													// empty
												}
												
												@Override
												public void statsStorageWritten(final int bytes) {
													// empty
												}
												
												@Override
												public String toString() {
													return "DEFAULT_CLASS";
												}
											};
	
	/**
	 * @return object
	 */
	Object getAttachment();
	
	/**
	 * @return int
	 */
	int getNetReadByteRateLimit();
	
	/**
	 * @param time
	 * @return int
	 */
	int getNetReadByteRateLimitLeft(long time);
	
	/**
	 * @return int
	 */
	int getNetWriteByteRateLimit();
	
	/**
	 * @param time
	 * @return int
	 */
	int getNetWriteByteRateLimitLeft(long time);
	
	/**
	 * @return int
	 */
	int getPriority();
	
	/**
	 * @return int
	 */
	int getStorageReadByteRateLimit();
	
	/**
	 * @return int
	 */
	int getStorageWriteByteRateLimit();
	
	/**
	 * Return true for null argument if there is one or more cases when this
	 * method will return true.
	 * 
	 * @param description
	 * @return boolean
	 */
	boolean isReplaceable(TransferDescription description);
	
	/**
	 * is setXXX methods usable?
	 * 
	 * @return boolean
	 */
	boolean isWritable();
	
	/**
	 * @param attachment
	 * @return object
	 */
	Object setAttachment(Object attachment);
	
	/**
	 * @param netByteReadLimit
	 * @return int
	 */
	int setNetReadByteRateLimit(int netByteReadLimit);
	
	/**
	 * @param netByteWriteLimit
	 * @return int
	 */
	int setNetWriteByteRateLimit(int netByteWriteLimit);
	
	/**
	 * @param storageByteReadLimit
	 * @return int
	 */
	int setStorageReadByteRateLimit(int storageByteReadLimit);
	
	/**
	 * @param storageByteWriteLimit
	 * @return int
	 */
	int setStorageWriteByteRateLimit(int storageByteWriteLimit);
	
	/**
	 * @param bytes
	 */
	void statsNetRead(int bytes);
	
	/**
	 * @param bytes
	 */
	void statsNetWritten(int bytes);
	
	/**
	 * @param bytes
	 */
	void statsStorageRead(int bytes);
	
	/**
	 * @param bytes
	 */
	void statsStorageWritten(int bytes);
}
