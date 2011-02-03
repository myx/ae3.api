/**
 * Created on 23.12.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package ru.myx.ae3.help;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author myx
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
final class DateFormatterEcma {
	static final class Formatter {
		private final SimpleDateFormat	format;
		
		private final Date				date;
		
		Formatter() {
			this.format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.sssZ", Locale.ENGLISH );
			this.format.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
			this.date = new Date( 0 );
		}
		
		final String format(final Date date) {
			synchronized (this) {
				return this.format.format( date );
			}
		}
		
		final String format(final long time) {
			synchronized (this) {
				this.date.setTime( time );
				return this.format.format( this.date );
			}
		}
		
		final long parse(final String date) throws ParseException {
			synchronized (this) {
				return this.format.parse( date ).getTime();
			}
		}
	}
	
	private static final int	CAPACITY	= 32;
	
	private static final int	MASK		= DateFormatterEcma.CAPACITY - 1;
	
	private int					counter		= 0;
	
	private final Formatter[]	formatters	= new Formatter[DateFormatterEcma.CAPACITY];
	
	DateFormatterEcma() {
		for (int i = DateFormatterEcma.MASK; i >= 0; i--) {
			this.formatters[i] = new Formatter();
		}
	}
	
	final String format(final Date date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterEcma.MASK];
		return format.format( date );
	}
	
	final String format(final long date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterEcma.MASK];
		return format.format( date );
	}
	
	long parse(final String date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterEcma.MASK];
		try {
			return format.parse( date );
		} catch (final Throwable t) {
			return -1L;
		}
	}
	
	@Override
	public String toString() {
		return "ae2core Ecma Date formatter, capacity=" + DateFormatterEcma.CAPACITY;
	}
	
}
