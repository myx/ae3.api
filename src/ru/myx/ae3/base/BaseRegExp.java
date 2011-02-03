package ru.myx.ae3.base;

import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author myx
 * 
 */
public class BaseRegExp extends BaseNative<Matcher> {
	/**
	 * 
	 */
	public static final BaseObject<?>	PROTOTYPE	= new BaseNativeObject();
	
	private final Pattern				pattern;
	
	private final Matcher				matcher;
	
	/**
	 * @param pattern
	 */
	public BaseRegExp(final Pattern pattern) {
		super( BaseRegExp.PROTOTYPE );
		this.pattern = pattern;
		this.matcher = pattern.matcher( "" );
	}
	
	@Override
	public String baseClass() {
		return "RegExp";
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDelete(final String name) {
		return false;
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		return null;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		return null;
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	/**
	 * Tests for a regular expression match and returns a MatchResult value (see
	 * section 15.10.2.1).
	 * 
	 * @return match result
	 */
	public MatchResult baseMatch() {
		return this.matcher.toMatchResult();
	}
	
	@Override
	public Matcher baseValue() {
		return this.matcher;
	}
	
	@Override
	public boolean equals(final Object o) {
		return o instanceof BaseRegExp
				&& this.pattern.equals( ((BaseRegExp) o).pattern )
				&& this.matcher.equals( ((BaseRegExp) o).matcher );
	}
	
	@Override
	public int hashCode() {
		return this.pattern.hashCode() ^ this.matcher.hashCode();
	}
}
