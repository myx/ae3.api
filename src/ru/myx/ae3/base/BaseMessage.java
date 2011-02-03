package ru.myx.ae3.base;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import ru.myx.ae3.binary.TransferBuffer;
import ru.myx.ae3.reflect.ReflectionHidden;
import ru.myx.ae3.report.Event;

/**
 * @author barachta
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 * @param <T>
 */
public interface BaseMessage<T extends BaseMessage<?>> extends Event<T> {
	/**
	 * Filled later in ae3.sys
	 */
	@ReflectionHidden
	BaseObject<?>	PROTOTYPE	= new BaseNativeObject( BaseObject.PROTOTYPE );
	
	/**
	 * 
	 */
	BaseMessage<?>	NUL_MESSAGE	= new NulMessage();
	
	/**
	 * Returns a map of attributes assigned to this message whose main purpose
	 * is to help with further message processing. Must not return <b>null </b>
	 * when hasAttributes() returns <b>true </b>.
	 * 
	 * @return map
	 */
	BaseObject<?> getAttributes();
	
	/**
	 * @return buffer
	 */
	TransferBuffer getBinary();
	
	/**
	 * The date of this message creation.
	 */
	@Override
	long getDate();
	
	/**
	 * @return file
	 */
	File getFile();
	
	/**
	 * returns a checksum.
	 * 
	 * @return checksum
	 */
	MessageDigest getMessageDigest();
	
	/**
	 * @return object
	 */
	Object getObject();
	
	/**
	 * @return class
	 */
	Class<?> getObjectClass();
	
	/**
	 * returns a string describing an author of this message. Should not ever
	 * return <b>null </b> value.
	 */
	@Override
	String getOwner();
	
	/**
	 * returns a protocol name, may be used as a hint, must return null if
	 * unknown.
	 * 
	 * @return string
	 */
	String getProtocolName();
	
	/**
	 * returns a protocol variant name, may be used as a hint, may return null.
	 * 
	 * @return string
	 */
	String getProtocolVariant();
	
	/**
	 * @return message
	 */
	BaseMessage<?>[] getSequence();
	
	/**
	 * returns a string with hardware or software address describing a sender of
	 * this message. I.e.: COM1, LPT2, 127.0.0.1, www.grammy.ru.....
	 * 
	 * @return string
	 */
	String getSourceAddress();
	
	/**
	 * returns a string with hardware or software address describing a path of
	 * this message. I.e.: COM1, LPT2, 127.0.0.1, www.grammy.ru.....
	 * 
	 * @return string
	 */
	String getSourceAddressExact();
	
	/**
	 * returns some text describing a concrete subject of this message.
	 * 
	 * Should not ever return <b>null </b> when hasSubject() method returns
	 * <b>true </b>.
	 */
	@Override
	String getSubject();
	
	/**
	 * @return string
	 */
	String getTarget();
	
	/**
	 * @return string
	 */
	String getTargetExact();
	
	/**
	 * @return string
	 */
	String getText();
	
	/**
	 * returns a line of text shortly describing the type of the message, i.e.
	 * "ERROR", "REQUEST"... Should not ever return <b>null </b> value.
	 */
	@Override
	String getTitle();
	
	/**
	 * returns true when getBinary() method would return guaranteed non-null and
	 * seems to be non-empty instance of Transfer.Buffer class.
	 * 
	 * @return boolean
	 */
	boolean isBinary();
	
	/**
	 * returns true when any of getBinary(), getFile(), getText() or getObject()
	 * will return <b>null </b> value.
	 * 
	 * @return boolean
	 */
	boolean isEmpty();
	
	/**
	 * returns true when getFile() method would return guaranteed non-null and
	 * seems to be existent instance of java.io.File class.
	 * 
	 * @return boolean
	 */
	boolean isFile();
	
	/**
	 * returns true when getObject() method would return guaranteed non-null and
	 * seems to be not a File, not a String and not Transfer.Buffer object.
	 * 
	 * @return boolean
	 */
	boolean isObject();
	
	/**
	 * returns true when getSequence() method whould return guaranteed non-null
	 * and seems to be non-empty array of inner messages enclosed in current
	 * message.
	 * 
	 * @return boolean
	 */
	boolean isSequence();
	
	/**
	 * returns true when getSource() method would return guaranteed non-null and
	 * seems to be non-empty instance of Transfer.Source class.
	 * 
	 * @return boolean
	 */
	boolean isSource();
	
	/**
	 * returns true when getText() method would return guaranteed non-null and
	 * seems to be non-empty instance of java.lang.String class.
	 * 
	 * @return boolean
	 */
	boolean isTextual();
	
	/**
	 * Returns a response whose isEmpty(), isBinary() or isFile() method will
	 * return <b>true </b> and getBinary() or getFile() will return a binary
	 * non-null representation of a response. When a response is already meets
	 * conditions secified this method should return exactly the SAME response
	 * object. The "Character-Encoding" of "Content-Type" attributes of a
	 * current response and "Accept-Charset" or "Accept-Type" attributes of
	 * current query should be considered when possible. "UTF-8" encoding should
	 * be used by default.
	 * 
	 * @return message
	 * @throws UnsupportedEncodingException
	 */
	BaseMessage<?> toBinaryMessage() throws UnsupportedEncodingException;
	
	/**
	 * Returns a response whose isEmpty(), isTextual() or isObject() method will
	 * return <b>true </b> and getText() or getObject() will return a
	 * <b>non-null </b> stringual representation of a response. When a response
	 * is already meets conditions secified this method should return exactly
	 * the SAME response object. The "Character-Encoding" attribute of a current
	 * response and "Accept-Charset" attribute of current query should be
	 * considered when possible. "UTF-8" encoding should be used by default.
	 * 
	 * @return message
	 * @throws UnsupportedEncodingException
	 */
	BaseMessage<?> toCharacterMessage() throws UnsupportedEncodingException;
}
