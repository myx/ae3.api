/*
 * Created on 28.03.2006
 */
package ru.myx.ae3.report;

/**
 * @author myx
 * 
 */
public interface ReportReciever {
	/**
	 * @param event
	 * @return 'true' to be compatible with assertions
	 */
	boolean event(final Event<?> event);
	
	/**
	 * @param owner
	 * @param title
	 * @param subject
	 * @return 'true' to be compatible with assertions
	 */
	boolean event(final String owner, final String title, final String subject);
}
