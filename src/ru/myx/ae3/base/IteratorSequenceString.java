package ru.myx.ae3.base;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Not public - use Base.joinPropertyIterators - does it better!
 * 
 * @author myx
 */
final class IteratorSequenceString implements Iterator<String> {
	
	private final Set<String>	known;
	
	private Iterator<String>	currentIterator;
	
	private Iterator<String>	nextIterator;
	
	private String				key;
	
	/**
	 * @param iterator
	 * @param next
	 */
	IteratorSequenceString(final Iterator<String> iterator, final Iterator<String> next) {
		assert iterator != null : "Should not be NULL";
		assert iterator.hasNext() : "Should not be empty!";
		this.known = new TreeSet<String>();
		this.currentIterator = iterator;
		this.nextIterator = next;
		this.key = iterator.next();
	}
	
	@Override
	public boolean hasNext() {
		return this.key != null;
	}
	
	@Override
	public String next() {
		final String key = this.key;
		this.known.add( key );
		for (;;) {
			if (this.currentIterator.hasNext()) {
				this.key = this.currentIterator.next();
				if (!this.known.contains( this.key )) {
					break;
				}
				continue;
			}
			if (this.nextIterator == null) {
				this.key = null;
				break;
			}
			this.currentIterator = this.nextIterator;
			this.nextIterator = null;
		}
		return key;
	}
	
	@Override
	public void remove() {
		// read-only
	}
}
