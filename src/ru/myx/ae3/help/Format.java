/**
 * Created on 09.11.2002
 * 
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of file
 * comments go to Window>Preferences>Java>Code Generation.
 */
package ru.myx.ae3.help;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author barachta
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Format {
	/**
	 * Inexact, Human readable
	 */
	public static final class Compact {
		private static final class NumberFormatter {
			private static final int			CAPACITY	= 32;
			
			private static volatile int			counter		= 0;
			
			private static final NumberFormat[]	POOL1		= new NumberFormat[NumberFormatter.CAPACITY];
			
			private static final NumberFormat[]	POOL2		= new NumberFormat[NumberFormatter.CAPACITY];
			
			private static final NumberFormat[]	POOL3		= new NumberFormat[NumberFormatter.CAPACITY];
			
			NumberFormatter() {
				for (int i = 0; i < NumberFormatter.CAPACITY; i++) {
					NumberFormatter.POOL1[i] = NumberFormat.getInstance( Locale.ROOT );
					NumberFormatter.POOL1[i].setMaximumFractionDigits( 1 );
					NumberFormatter.POOL1[i].setMinimumFractionDigits( 0 );
					NumberFormatter.POOL1[i].setMinimumIntegerDigits( 1 );
					NumberFormatter.POOL1[i].setGroupingUsed( false );
					NumberFormatter.POOL1[i].setRoundingMode( RoundingMode.HALF_UP );
					
					NumberFormatter.POOL2[i] = NumberFormat.getInstance( Locale.ROOT );
					NumberFormatter.POOL2[i].setMaximumFractionDigits( 2 );
					NumberFormatter.POOL2[i].setMinimumFractionDigits( 0 );
					NumberFormatter.POOL2[i].setMinimumIntegerDigits( 1 );
					NumberFormatter.POOL2[i].setGroupingUsed( false );
					NumberFormatter.POOL2[i].setRoundingMode( RoundingMode.HALF_UP );
					
					NumberFormatter.POOL3[i] = NumberFormat.getInstance( Locale.ROOT );
					NumberFormatter.POOL3[i].setMaximumFractionDigits( 3 );
					NumberFormatter.POOL3[i].setMinimumFractionDigits( 0 );
					NumberFormatter.POOL3[i].setMinimumIntegerDigits( 0 );
					NumberFormatter.POOL3[i].setGroupingUsed( false );
					NumberFormatter.POOL3[i].setRoundingMode( RoundingMode.HALF_UP );
				}
			}
			
			String format1(final double d) {
				final int index = NumberFormatter.counter++;
				final NumberFormat current = NumberFormatter.POOL1[index % NumberFormatter.CAPACITY];
				synchronized (current) {
					return current.format( d );
				}
			}
			
			String format2(final double d) {
				final int index = NumberFormatter.counter++;
				final NumberFormat current = NumberFormatter.POOL2[index % NumberFormatter.CAPACITY];
				synchronized (current) {
					return current.format( d );
				}
			}
			
			String format3(final double d) {
				final int index = NumberFormatter.counter++;
				final NumberFormat current = NumberFormatter.POOL3[index % NumberFormatter.CAPACITY];
				synchronized (current) {
					return current.format( d );
				}
			}
		}
		
		private static final DateFormatterCompact	DATE		= new DateFormatterCompact();
		
		private static final NumberFormatter		FORMATTER	= new NumberFormatter();
		
		/**
		 * @param date
		 * @return string
		 */
		public static final String date(final Date date) {
			return Compact.DATE.format( date );
		}
		
		/**
		 * @param time
		 * @return string
		 */
		public static final String date(final long time) {
			return Compact.DATE.format( time );
		}
		
		/**
		 * @param date
		 * @return string
		 */
		public static final String dateRelative(final Date date) {
			return Compact.DATE.formatRelative( date );
		}
		
		/**
		 * @param time
		 * @return string
		 */
		public static final String dateRelative(final long time) {
			return Compact.DATE.formatRelative( time );
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toBytes(final double value) {
			if (value < 1000L) {
				if (value < 0) {
					return '-' + Format.Compact.toBytes( -value );
				}
				if (value >= 1) {
					return Compact.FORMATTER.format2( value ) + ' ';
				}
				if (value >= Format.DOUBLE_MILLI) {
					return Compact.FORMATTER.format2( value / Format.DOUBLE_MILLI_BYTES ) + " ml";
				}
				if (value >= Format.DOUBLE_MICRO) {
					return Compact.FORMATTER.format2( value / Format.DOUBLE_MICRO_BYTES ) + " mk";
				}
				return Compact.FORMATTER.format2( value / Format.DOUBLE_NANO_BYTES ) + " n";
			}
			if (value >= 1000L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_TERA_BYTES ) + " T";
			}
			if (value >= 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_GIGA_BYTES ) + " G";
			}
			if (value >= 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MEGA_BYTES ) + " M";
			}
			if (value >= 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_KILO_BYTES ) + " k";
			}
			return "n/a";
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toBytes(final long value) {
			if (value < 0) {
				return '-' + Format.Compact.toBytes( -value );
			}
			if (value < 1000L) {
				return String.valueOf( value ) + ' ';
			}
			if (value >= 1000L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_TERA_BYTES ) + " T";
			}
			if (value >= 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_GIGA_BYTES ) + " G";
			}
			if (value >= 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MEGA_BYTES ) + " M";
			}
			return Compact.FORMATTER.format2( value / Format.DOUBLE_KILO_BYTES ) + " k";
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toDecimal(final double value) {
			if (Double.isInfinite( value )) {
				return value > 0
						? "+inf"
						: "-inf";
			}
			if (Double.isNaN( value )) {
				return "NaN";
			}
			if (value < 0) {
				return '-' + Format.Compact.toDecimal( -value );
			}
			if (value < Format.DOUBLE_NANO) {
				return String.valueOf( value );
			}
			if (value >= 100L * 1000L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_TERA ) + "T";
			}
			if (value >= 1000L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_TERA ) + "T";
			}
			if (value >= 100L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_GIGA ) + "G";
			}
			if (value >= 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_GIGA ) + "G";
			}
			if (value >= 100L * 1000L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_MEGA ) + "M";
			}
			if (value >= 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MEGA ) + "M";
			}
			if (value >= 100L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_KILO ) + "k";
			}
			if (value >= 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_KILO ) + "k";
			}
			if (value >= 20L) {
				return Compact.FORMATTER.format1( value );
			}
			if (value >= 1L) {
				return Compact.FORMATTER.format2( value );
			}
			if (value >= Format.DOUBLE_MILLI) {
				if (value >= Format.DOUBLE_MILLI * 10) {
					return Compact.FORMATTER.format3( (int) (value * 1000) / 1000.0 );
				}
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MILLI ) + "ml";
			}
			if (value >= Format.DOUBLE_MICRO) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MICRO ) + "mk";
			}
			return Compact.FORMATTER.format2( value / Format.DOUBLE_NANO ) + "n";
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toDecimal(final long value) {
			if (value < 0) {
				return '-' + Format.Compact.toDecimal( -value );
			}
			if (value >= 100L * 1000L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_TERA ) + "T";
			}
			if (value >= 1000L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_TERA ) + "T";
			}
			if (value >= 100L * 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_GIGA ) + "G";
			}
			if (value >= 1000L * 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_GIGA ) + "G";
			}
			if (value >= 100L * 1000L * 1000L) {
				return Compact.FORMATTER.format1( value / Format.DOUBLE_MEGA ) + "M";
			}
			if (value >= 1000L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MEGA ) + "M";
			}
			if (value >= 100L * 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_KILO ) + "k";
			}
			if (value >= 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_KILO ) + "k";
			}
			if (value >= 100L) {
				return Compact.FORMATTER.format1( value );
			}
			return Compact.FORMATTER.format2( value );
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toPeriod(final double value) {
			if (value <= 0) {
				return String.valueOf( value );
			}
			if (value >= Format.DOUBLE_WEEK_PERIOD) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_WEEK_PERIOD ) + " week(s)";
			}
			if (value >= Format.DOUBLE_DAY_PERIOD) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_DAY_PERIOD ) + " day(s)";
			}
			if (value >= Format.DOUBLE_HOUR_PERIOD) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_HOUR_PERIOD ) + " hour(s)";
			}
			if (value >= Format.DOUBLE_MINUTE_PERIOD) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MINUTE_PERIOD ) + " minute(s)";
			}
			if (value >= Format.DOUBLE_SECOND_PERIOD) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_SECOND_PERIOD ) + " second(s)";
			}
			if (value >= Format.DOUBLE_MILLISECOND_PERIOD) {
				// return formatter.format(value / dMILLISECOND_PERIOD) + " ms";
				return Compact.FORMATTER.format2( value ) + " ms";
			}
			if (value >= Format.DOUBLE_MICROSECOND_PERIOD) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MICROSECOND_PERIOD ) + " mks";
			}
			return Compact.FORMATTER.format2( value / Format.DOUBLE_NANOSECOND_PERIOD ) + " nanos";
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toPeriod(final long value) {
			if (value <= 0) {
				return String.valueOf( value );
			}
			if (value >= 1000L * 60L * 60L * 24L * 7L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_WEEK_PERIOD ) + " week(s)";
			}
			if (value >= 1000L * 60L * 60L * 24L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_DAY_PERIOD ) + " day(s)";
			}
			if (value >= 1000L * 60L * 60L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_HOUR_PERIOD ) + " hour(s)";
			}
			if (value >= 1000L * 60L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_MINUTE_PERIOD ) + " minute(s)";
			}
			if (value >= 1000L) {
				return Compact.FORMATTER.format2( value / Format.DOUBLE_SECOND_PERIOD ) + " second(s)";
			}
			return Compact.FORMATTER.format2( value ) + " ms";
		}
		
		private Compact() {
			// empty
		}
		
	}
	
	/**
	 * Common web formatting
	 */
	public static final class Ecma {
		private static final DateFormatterEcma	DATE	= new DateFormatterEcma();
		
		/**
		 * @param date
		 * @return string
		 */
		public static final String date(final Date date) {
			return Ecma.DATE.format( date );
		}
		
		/**
		 * @param time
		 * @return string
		 */
		public static final String date(final long time) {
			return Ecma.DATE.format( time );
		}
		
		/**
		 * Returns -1 on error or parsed HTTP date.
		 * 
		 * @param string
		 * @return date
		 */
		public static final long parse(final String string) {
			return Ecma.DATE.parse( string );
		}
		
		private Ecma() {
			// empty
		}
	}
	
	/**
	 * Exact, Machine/Human readable
	 */
	public static final class Exact {
		/**
		 * @param value
		 * @return string
		 */
		public static final String toBytes(double value) {
			if (value <= 0) {
				return String.valueOf( value );
			}
			final StringBuilder result = new StringBuilder( 64 );
			if (value > 1024L * 1024L * 1024L * 1024L) {
				final int ml = (int) (value / (1024L * 1024L * 1024L * 1024L));
				value -= 1024L * 1024L * 1024L * 1024L * ml;
				result.append( ml ).append( 'T' );
			}
			if (value > 1024L * 1024L * 1024L) {
				final int ml = (int) (value / (1024L * 1024L * 1024L));
				value -= 1024L * 1024L * 1024L * ml;
				result.append( ml ).append( 'G' );
			}
			if (value > 1024L * 1024L) {
				final int ml = (int) (value / (1024L * 1024L));
				value -= 1024L * 1024L * ml;
				result.append( ml ).append( 'M' );
			}
			if (value > 1024L) {
				final int ml = (int) (value / 1024L);
				value -= 1024L * ml;
				result.append( ml ).append( 'k' );
			}
			if (value > 0) {
				result.append( value );
			}
			return result.toString();
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toBytes(long value) {
			if (value <= 0) {
				return String.valueOf( value );
			}
			final StringBuilder result = new StringBuilder( 64 );
			if (value > 1024L * 1024L * 1024L * 1024L) {
				final int ml = (int) (value / (1024L * 1024L * 1024L * 1024L));
				value -= 1024L * 1024L * 1024L * 1024L * ml;
				result.append( ml ).append( 'T' );
			}
			if (value > 1024L * 1024L * 1024L) {
				final int ml = (int) (value / (1024L * 1024L * 1024L));
				value -= 1024L * 1024L * 1024L * ml;
				result.append( ml ).append( 'G' );
			}
			if (value > 1024L * 1024L) {
				final int ml = (int) (value / (1024L * 1024L));
				value -= 1024L * 1024L * ml;
				result.append( ml ).append( 'M' );
			}
			if (value > 1024L) {
				final int ml = (int) (value / 1024L);
				value -= 1024L * ml;
				result.append( ml ).append( 'k' );
			}
			if (value > 0) {
				result.append( value );
			}
			return result.toString();
		}
		
		/**
		 * @param value
		 * @return string
		 */
		public static final String toPeriod(long value) {
			if (value <= 0) {
				return String.valueOf( value );
			}
			final StringBuilder result = new StringBuilder( 64 );
			if (value > 1000L * 60L * 60L * 24L * 7L) {
				final int ml = (int) (value / (1000L * 60L * 60L * 24L * 7L));
				value -= 1000L * 60L * 60L * 24L * 7L * ml;
				result.append( ml ).append( 'w' );
			}
			if (value > 1000L * 60L * 60L * 24L) {
				final int ml = (int) (value / (1000L * 60L * 60L * 24L));
				value -= 1000L * 60L * 60L * 24L * ml;
				result.append( ml ).append( 'd' );
			}
			if (value > 1000L * 60L * 60L) {
				final int ml = (int) (value / (1000L * 60L * 60L));
				value -= 1000L * 60L * 60L * ml;
				result.append( ml ).append( 'h' );
			}
			if (value > 1000L * 60L) {
				final int ml = (int) (value / (1000L * 60L));
				value -= 1000L * 60L * ml;
				result.append( ml ).append( 'm' );
			}
			if (value > 1000L) {
				final int ml = (int) (value / 1000L);
				value -= 1000L * ml;
				result.append( ml ).append( 's' );
			}
			if (value > 0) {
				result.append( value );
			}
			return result.toString();
		}
		
		private Exact() {
			// empty
		}
	}
	
	/**
	 * Common web formatting
	 */
	public static final class Web {
		private static final DateFormatterWeb	DATE	= new DateFormatterWeb();
		
		/**
		 * @param date
		 * @return string
		 */
		public static final String date(final Date date) {
			return Web.DATE.format( date );
		}
		
		/**
		 * @param time
		 * @return string
		 */
		public static final String date(final long time) {
			return Web.DATE.format( time );
		}
		
		/**
		 * Returns -1 on error or parsed HTTP date.
		 * 
		 * @param string
		 * @return date
		 */
		public static final long parse(final String string) {
			return Web.DATE.parse( string );
		}
		
		private Web() {
			// empty
		}
	}
	
	/**
	 * 
	 */
	public static final double	DOUBLE_KILO_BYTES			= 1024L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MEGA_BYTES			= 1024L * 1024L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_GIGA_BYTES			= 1024L * 1024L * 1024L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_TERA_BYTES			= 1024L * 1024L * 1024L * 1024L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MILLI_BYTES			= Format.DOUBLE_KILO_BYTES / Format.DOUBLE_MEGA_BYTES;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MICRO_BYTES			= Format.DOUBLE_KILO_BYTES / Format.DOUBLE_GIGA_BYTES;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_NANO_BYTES			= Format.DOUBLE_KILO_BYTES / Format.DOUBLE_TERA_BYTES;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_KILO					= 1000L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MEGA					= 1000L * 1000L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_GIGA					= 1000L * 1000L * 1000L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_TERA					= 1000L * 1000L * 1000L * 1000L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MILLI				= 1000L / Format.DOUBLE_MEGA;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MICRO				= 1000L / Format.DOUBLE_GIGA;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_NANO					= 1000L / Format.DOUBLE_TERA;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_SECOND_PERIOD		= 1000L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MINUTE_PERIOD		= 1000L * 60L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_HOUR_PERIOD			= 1000L * 60L * 60L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_DAY_PERIOD			= 1000L * 60L * 60L * 24L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_WEEK_PERIOD			= 1000L * 60L * 60L * 24L * 7L;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MILLISECOND_PERIOD	= 1.0;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_MICROSECOND_PERIOD	= Format.DOUBLE_MILLISECOND_PERIOD / 1000.0;
	
	/**
	 * 
	 */
	public static final double	DOUBLE_NANOSECOND_PERIOD	= Format.DOUBLE_MICROSECOND_PERIOD / 1000.0;
	
	private Format() {
		// empty
	}
}
