package ru.myx.ae3.base;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

final class IteratorHierarchy implements Iterator<String> {
	
	private final Set<String>	known;
	
	private Iterator<String>	currentIterator;
	
	private BaseObject<?>		nextObject;
	
	private String				key;
	
	IteratorHierarchy(final Iterator<String> iterator, final BaseObject<?> prototype) {
		assert iterator != null : "Should not be NULL";
		assert iterator.hasNext() : "Should not be empty!";
		this.known = new TreeSet<String>();
		this.currentIterator = iterator;
		this.nextObject = prototype;
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
			if (this.nextObject == null) {
				this.key = null;
				break;
			}
			this.currentIterator = this.nextObject.baseGetOwnIterator();
			assert this.currentIterator != null : "NULL iterator - use BaseObject.ITERATOR_EMPTY, class="
					+ this.nextObject.getClass().getName();
			this.nextObject = this.nextObject.basePrototype();
		}
		return key;
	}
	
	@Override
	public void remove() {
		// read-only
	}
}
