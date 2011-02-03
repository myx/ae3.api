/**
 * Created on 23.12.2002
 * 
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of file
 * comments go to Window>Preferences>Java>Code Generation.
 */
package ru.myx.ae3.help;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.myx.ae3.Engine;

/**
 * @author myx
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
final class DateFormatterCompact {
	static final class Formatter {
		private final SimpleDateFormat	formatFull;
		
		private final SimpleDateFormat	formatDate;
		
		private final SimpleDateFormat	formatTime;
		
		private final Calendar			calendar;
		
		private final Date				date;
		
		Formatter() {
			this.formatFull = new SimpleDateFormat( "yyyy.MM.dd HH:mm Z", Locale.ENGLISH );
			this.formatDate = new SimpleDateFormat( "yyyy.MM.dd Z", Locale.ENGLISH );
			this.formatTime = new SimpleDateFormat( "HH:mm Z", Locale.ENGLISH );
			this.calendar = Calendar.getInstance();
			this.date = new Date( 0 );
		}
		
		final String format(final Date date) {
			synchronized (this) {
				return this.formatFull.format( date );
			}
		}
		
		final String format(final long time) {
			synchronized (this) {
				this.date.setTime( time );
				return this.formatFull.format( this.date );
			}
		}
		
		final String formatRelative(final Date date) {
			if (date == null) {
				return "--";
			}
			return this.formatRelative( date.getTime() );
		}
		
		final String formatRelative(final long date) {
			if (date == -1L) {
				return "--";
			}
			final long current = Engine.fastTime();
			if (current > date) {
				/**
				 * <pre>
				 * final long time = current - date;
				 * if (time &lt; (1000L * 60L)) {
				 * 	return MultivariantString.getString( (time / 1000L) + &quot; sec.&quot;,
				 * 			Collections.singletonMap( &quot;ru&quot;, (time / 1000L) + &quot; сек.&quot; ) ).toString();
				 * }
				 * if (time &lt; (1000L * 60L * 60L) / 2) {
				 * 	return MultivariantString.getString( (time / (1000L * 60L)) + &quot; min.&quot;,
				 * 			Collections.singletonMap( &quot;ru&quot;, (time / (1000L * 60L)) + &quot; мин.&quot; ) ).toString();
				 * }
				 * </pre>
				 */
				synchronized (this) {
					this.calendar.setTimeInMillis( current );
					this.calendar.set( Calendar.HOUR_OF_DAY, 0 );
					this.calendar.set( Calendar.MINUTE, 0 );
					this.calendar.set( Calendar.SECOND, 0 );
					this.calendar.set( Calendar.MILLISECOND, 0 );
					final long dayStart = this.calendar.getTime().getTime();
					if (date >= dayStart) {
						this.date.setTime( date );
						return this.formatTime.format( this.date );
					}
					/**
					 * <pre>
					 * if (date &gt;= dayStart) {
					 * 	this.date.setTime( date );
					 * 	return Formatter.TODAY.toString() + &quot;, &quot; + this.formatTime.format( this.date );
					 * }
					 * if (date &gt;= dayStart - (1000L * 60L * 60L * 24L)) {
					 * 	this.date.setTime( date );
					 * 	return Formatter.YESTERDAY.toString() + &quot;, &quot; + this.formatTime.format( this.date );
					 * }
					 * </pre>
					 */
					this.date.setTime( date );
					return this.formatDate.format( this.date );
				}
			}
			synchronized (this) {
				this.date.setTime( date );
				return this.formatFull.format( this.date );
			}
		}
	}
	
	private static final int	CAPACITY	= 32;
	
	private static final int	MASK		= DateFormatterCompact.CAPACITY - 1;
	
	private int					counter		= 0;
	
	private final Formatter[]	formatters	= new Formatter[DateFormatterCompact.CAPACITY];
	
	DateFormatterCompact() {
		for (int i = DateFormatterCompact.MASK; i >= 0; i--) {
			this.formatters[i] = new Formatter();
		}
	}
	
	final String format(final Date date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterCompact.MASK];
		return format.format( date );
	}
	
	final String format(final long date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterCompact.MASK];
		return format.format( date );
	}
	
	final String formatRelative(final Date date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterCompact.MASK];
		return format.formatRelative( date );
	}
	
	final String formatRelative(final long date) {
		final Formatter format = this.formatters[--this.counter & DateFormatterCompact.MASK];
		return format.formatRelative( date );
	}
	
	@Override
	public String toString() {
		return "ae2core Compact Date formatter, capacity=" + DateFormatterCompact.CAPACITY;
	}
}
