/*
 * Created on 28.03.2006
 */
package ru.myx.ae3.report;

import ru.myx.ae3.base.BaseNativeObject;
import ru.myx.ae3.base.BaseObject;

/**
 * Event's baseValue() method MUST return 'this'
 * 
 * @author myx
 * @param <T>
 * 
 */
public interface Event<T extends Event<?>> extends BaseObject<T> {
	/**
	 * FIXME: no Reflect accessible
	 */
	public static final BaseObject<?>	PROTOTYPE	= new BaseNativeObject( BaseObject.PROTOTYPE );
	
	/**
	 * The date of this event creation/registration.
	 * 
	 * @return date
	 */
	long getDate();
	
	/**
	 * returns a string describing an author of this event. Should not ever
	 * return <b>null </b> value.
	 * 
	 * @return string
	 */
	String getOwner();
	
	/**
	 * @return process number
	 */
	long getProcess();
	
	/**
	 * returns some text describing a concrete subject of this event.
	 * 
	 * Should not ever return <b>null </b> when hasSubject() method returns
	 * <b>true </b>.
	 * 
	 * @return string
	 */
	String getSubject();
	
	/**
	 * returns a line of text shortly describing the type of the event, i.e.
	 * "ERROR", "REQUEST", "DEBUG"... Should not ever return <b>null </b> value.
	 * 
	 * @return string
	 */
	String getTitle();
}
