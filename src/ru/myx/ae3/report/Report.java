/**
 * Created on 22.11.2002
 * 
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of file
 * comments go to Window>Preferences>Java>Code Generation.
 */
package ru.myx.ae3.report;

import java.io.File;

import ru.myx.ae3.AbstractSAPI;
import ru.myx.ae3.Engine;
import ru.myx.ae3.base.Base;
import ru.myx.ae3.exec.Exec;
import ru.myx.ae3.exec.ExecProcess;

/**
 * @author barachta
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Report extends AbstractSAPI {
	
	/**
	 * email recipient to route system messages to if needs to be delivered
	 */
	public static final String				MAILTO			= System.getProperty( "ru.myx.ae3.properties.log.level", "" )
																	.trim();
	
	/**
	 * 
	 */
	public static final String[]			LEVEL_NAMES		= { "MINIMAL", "NORMAL", "DEBUG", "DEVEL" };
	
	/**
	 * This level assumes that any messages should be passed as broadcast trough
	 * eventing system.
	 */
	public static final int					LEVEL_DEVEL		= 3;
	
	/**
	 * This level assumes that any messages should be passed as broadcast trough
	 * eventing system.
	 */
	public static final int					LEVEL_DEBUG		= 2;
	
	/**
	 * This level assumes that only WARNING, ERROR or application interchange
	 * messages should be passed as broadcast trough eventing system.
	 */
	public static final int					LEVEL_MIN		= 0;
	
	/**
	 * This level assumes that only unknown, INFO, WARNING, ERROR or application
	 * interchange messages should be passed as broadcast trough eventing
	 * system.
	 */
	public static final int					LEVEL_NORM		= 1;
	
	/**
	 * 
	 */
	public static final File				LOG_ROOT_FILE	= Engine.PATH_LOGS;
	
	/**
	 * 
	 */
	public static final String				LOG_ROOT_PATH	= Report.LOG_ROOT_FILE.getAbsolutePath();
	
	/**
	 * Indicates current reporting level - lesser level means lesser logging.
	 */
	public static final int					LEVEL;
	
	/**
	 * 
	 */
	public static final String				LEVEL_NAME;
	
	/**
	 * 
	 */
	public static final boolean				MODE_ASSERT;
	
	/**
	 * 
	 */
	public static final boolean				MODE_DEBUG;
	
	/**
	 * 
	 */
	public static final boolean				MODE_DEVEL;
	
	private static final AbstractReportImpl	REPORT_IMPL;
	
	static {
		{
			boolean debug = false;
			try {
				assert false;
			} catch (final AssertionError e) {
				// !!! ADD && REPORT.DEVEL here - long code dumps are unusable
				// for any other purpose
				debug = true;
			}
			MODE_ASSERT = debug;
		}
		
		REPORT_IMPL = AbstractSAPI.createObject( "ru.myx.ae3.report.ImplementReport" );
		
		LEVEL = Report.REPORT_IMPL.getLevel();
		
		LEVEL_NAME = Report.LEVEL_NAMES[Report.LEVEL];
		MODE_DEBUG = Report.LEVEL >= Report.LEVEL_DEBUG;
		MODE_DEVEL = Report.LEVEL >= Report.LEVEL_DEVEL;
	}
	
	/**
	 * @param eOwner
	 * @param eTitle
	 * @param eSubject
	 */
	public static final void audit(final String eOwner, final String eTitle, final String eSubject) {
		final ExecProcess process = Exec.currentProcess();
		Report.currentRecieverAudit( process ).event( eOwner, eTitle, eSubject );
	}
	
	/**
	 * Creates (or obtains existing one) reciever bus.
	 * 
	 * @param busName
	 * @return reciever
	 */
	public static final ReportReciever createReciever(final String busName) {
		return Report.REPORT_IMPL.createReciever( busName == null
				? "default"
				: busName );
	}
	
	/**
	 * PROTOCOL: report: $reportAudit and $reportLog context properties are the
	 * references to respectable 'audit events' and 'other events'
	 * ReportRecievers.
	 * 
	 * @return reciever
	 */
	public static final ReportReciever currentRecieverAudit() {
		return (ReportReciever) Base.getJava( Exec.currentProcess(), "$reportAudit", Report.createReciever( null ) );
	}
	
	/**
	 * PROTOCOL: report: $reportAudit and $reportLog context properties are the
	 * references to respectable 'audit events' and 'other events'
	 * ReportRecievers.
	 * 
	 * @param ctx
	 * @return reciever
	 */
	public static final ReportReciever currentRecieverAudit(final ExecProcess ctx) {
		final ReportReciever reciever = (ReportReciever) Base.getJava( Exec.currentProcess(), "$reportAudit", null );
		/**
		 * most likely goes first
		 */
		return reciever != null
				? reciever
				: Report.createReciever( null );
	}
	
	/**
	 * PROTOCOL: report: $reportAudit and $reportLog context properties are the
	 * references to respectable 'audit events' and 'other events'
	 * ReportRecievers.
	 * 
	 * @return reciever
	 */
	public static final ReportReciever currentRecieverLog() {
		final ReportReciever reciever = (ReportReciever) Base.getJava( Exec.currentProcess(), "$reportLog", null );
		/**
		 * most likely goes first
		 */
		return reciever != null
				? reciever
				: Report.createReciever( null );
	}
	
	/**
	 * PROTOCOL: report: $reportAudit and $reportLog context properties are the
	 * references to respectable 'audit events' and 'other events'
	 * ReportRecievers.
	 * 
	 * @param ctx
	 * @return reciever
	 */
	public static final ReportReciever currentRecieverLog(final ExecProcess ctx) {
		final ReportReciever reciever = (ReportReciever) Base.getJava( ctx, "$reportLog", null );
		/**
		 * most likely goes first
		 */
		return reciever != null
				? reciever
				: Report.createReciever( null );
	}
	
	/**
	 * Equivalent to:<br>
	 * <code>
		if (Report.MODE_DEBUG) {
			Report.currentRecieverLog().event( eOwner, "DEBUG", eSubject );
		}
	 * </code>
	 * 
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean debug(final String eOwner, final String eSubject) {
		return !Report.MODE_DEBUG || Report.currentRecieverLog().event( eOwner, "DEBUG", eSubject );
	}
	
	/**
	 * Equivalent to:<br>
	 * <code>
		if (Report.MODE_DEVEL) {
			Report.currentRecieverLog().event( eOwner, "DEVEL", eSubject );
		}
	 * </code>
	 * 
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean devel(final String eOwner, final String eSubject) {
		return !Report.MODE_DEVEL || Report.currentRecieverLog().event( eOwner, "DEVEL", eSubject );
	}
	
	/**
	 * Equivalent to:<br>
	 * <code>
		Report.currentRecieverLog().event( eOwner, "ERROR", eSubject );
	 * </code>
	 * 
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean error(final String eOwner, final String eSubject) {
		return Report.currentRecieverLog().event( eOwner, "ERROR", eSubject );
	}
	
	/**
	 * @param eOwner
	 * @param eSubject
	 * @param eAttachment
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean error(final String eOwner, final String eSubject, final String eAttachment) {
		return Report.currentRecieverLog().event( eOwner, "ERROR", eSubject + "\r\n" + eAttachment + "\r\n" );
	}
	
	/**
	 * Equivalent to: Report.currentRecieverLog( process ).event( eOwner,
	 * eTitle, eSubject );
	 * 
	 * @param process
	 * @param eOwner
	 * @param eTitle
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean event(
			final ExecProcess process,
			final String eOwner,
			final String eTitle,
			final String eSubject) {
		return Report.currentRecieverLog( process ).event( eOwner, eTitle, eSubject );
	}
	
	/**
	 * Equivalent to:<br>
	 * <code>
	    Report.currentRecieverLog().event( eOwner, eTitle, eSubject );
	 * </code>
	 * 
	 * @param eOwner
	 * @param eTitle
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean event(final String eOwner, final String eTitle, final String eSubject) {
		return Report.currentRecieverLog().event( eOwner, eTitle, eSubject );
	}
	
	/**
	 * @param reciever
	 * @param eOwner
	 * @param eSubject
	 * @param text
	 * @param t
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean exception(
			final ReportReciever reciever,
			final String eOwner,
			final String eSubject,
			final String text,
			final Throwable t) {
		Report.REPORT_IMPL.reportException( reciever, eOwner, eSubject, text, t );
		return true;
	}
	
	/**
	 * @param reciever
	 * @param eOwner
	 * @param eSubject
	 * @param t
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean exception(
			final ReportReciever reciever,
			final String eOwner,
			final String eSubject,
			final Throwable t) {
		if (eSubject != null) {
			final int pos = eSubject.indexOf( '\n' );
			if (pos != -1) {
				return Report.exception( reciever, eOwner, eSubject.substring( 0, pos ), eSubject, t );
			}
		}
		return Report.exception( reciever, eOwner, eSubject, null, t );
	}
	
	/**
	 * @param eOwner
	 * @param eSubject
	 * @param text
	 * @param t
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean exception(
			final String eOwner,
			final String eSubject,
			final String text,
			final Throwable t) {
		Report.REPORT_IMPL.reportException( null, eOwner, eSubject, text, t );
		return true;
	}
	
	/**
	 * @param eOwner
	 * @param eSubject
	 * @param t
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean exception(final String eOwner, final String eSubject, final Throwable t) {
		if (eSubject != null) {
			final int pos = eSubject.indexOf( '\n' );
			if (pos != -1) {
				return Report.exception( null, eOwner, eSubject.substring( 0, pos ), eSubject, t );
			}
		}
		return Report.exception( null, eOwner, eSubject, null, t );
	}
	
	/**
	 * Equivalent to: <code>
		if (Report.LEVEL > Report.LEVEL_MIN) {
			Report.currentRecieverLog().event( eOwner, "INFO", eSubject );
		}
	 * </code>
	 * 
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean info(final String eOwner, final String eSubject) {
		return Report.LEVEL <= Report.LEVEL_MIN || Report.currentRecieverLog().event( eOwner, "INFO", eSubject );
	}
	
	/**
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean started(final String eOwner, final String eSubject) {
		return !Report.MODE_DEBUG || Report.currentRecieverLog().event( eOwner, "STARTED", eSubject );
	}
	
	/**
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean success(final String eOwner, final String eSubject) {
		return Report.LEVEL <= Report.LEVEL_MIN || Report.currentRecieverLog().event( eOwner, "SUCCESS", eSubject );
	}
	
	/**
	 * equvalent to:<br>
	 * Report.currentRecieverLog().event( eOwner, "WARNING", eSubject );
	 * 
	 * @param eOwner
	 * @param eSubject
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean warning(final String eOwner, final String eSubject) {
		return Report.currentRecieverLog().event( eOwner, "WARNING", eSubject );
	}
	
	/**
	 * @param eOwner
	 * @param eSubject
	 * @param eAttachment
	 * @return 'true' to be compatible with assertions
	 */
	public static final boolean warning(final String eOwner, final String eSubject, final String eAttachment) {
		return Report.warning( eOwner, eSubject + "\r\n" + eAttachment + "\r\n" );
	}
	
	private Report() {
		// empty
	}
}
