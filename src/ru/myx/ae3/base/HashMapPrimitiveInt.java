/*
 * @(#)HashMap.java 1.68 06/06/27
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package ru.myx.ae3.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Hash table based implementation of the <tt>Map</tt> interface. This
 * implementation provides all of the optional map operations, and permits
 * <tt>null</tt> values and the <tt>null</tt> key. (The <tt>HashMap</tt> class
 * is roughly equivalent to <tt>Hashtable</tt>, except that it is unsynchronized
 * and permits nulls.) This class makes no guarantees as to the order of the
 * map; in particular, it does not guarantee that the order will remain constant
 * over time.
 * 
 * <p>
 * This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets. Iteration over collection
 * views requires time proportional to the "capacity" of the <tt>HashMap</tt>
 * instance (the number of buckets) plus its size (the number of key-value
 * mappings). Thus, it's very important not to set the initial capacity too high
 * (or the load factor too low) if iteration performance is important.
 * 
 * <p>
 * An instance of <tt>HashMap</tt> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>. The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created. The
 * <i>load factor</i> is a measure of how full the hash table is allowed to get
 * before its capacity is automatically increased. When the number of entries in
 * the hash table exceeds the product of the load factor and the current
 * capacity, the capacity is roughly doubled by calling the <tt>rehash</tt>
 * method.
 * 
 * <p>
 * As a general rule, the default load factor (.75) offers a good tradeoff
 * between time and space costs. Higher values decrease the space overhead but
 * increase the lookup cost (reflected in most of the operations of the
 * <tt>HashMap</tt> class, including <tt>get</tt> and <tt>put</tt>). The
 * expected number of entries in the map and its load factor should be taken
 * into account when setting its initial capacity, so as to minimize the number
 * of <tt>rehash</tt> operations. If the initial capacity is greater than the
 * maximum number of entries divided by the load factor, no <tt>rehash</tt>
 * operations will ever occur.
 * 
 * <p>
 * If many mappings are to be stored in a <tt>HashMap</tt> instance, creating it
 * with a sufficiently large capacity will allow the mappings to be stored more
 * efficiently than letting it perform automatic rehashing as needed to grow the
 * table.
 * 
 * <p>
 * <b>Note that this implementation is not synchronized.</b> If multiple threads
 * access this map concurrently, and at least one of the threads modifies the
 * map structurally, it <i>must</i> be synchronized externally. (A structural
 * modification is any operation that adds or deletes one or more mappings;
 * merely changing the value associated with a key that an instance already
 * contains is not a structural modification.) This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map. If no such
 * object exists, the map should be "wrapped" using the
 * <tt>Collections.synchronizedMap</tt> method. This is best done at creation
 * time, to prevent accidental unsynchronized access to the map:
 * 
 * <pre>
 *                                                                                         Map&lt;?,?&gt; m = Collections.synchronizedMap(new HashMap(...));
 * </pre>
 * 
 * <p>
 * The iterators returned by all of this class's "collection view methods" are
 * <i>fail-fast</i>: if the map is structurally modified at any time after the
 * iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> or <tt>add</tt> methods, the iterator will throw a
 * <tt>ConcurrentModificationException</tt>. Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 * 
 * <p>
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it
 * is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification. Fail-fast iterators throw
 * <tt>ConcurrentModificationException</tt> on a best-effort basis. Therefore,
 * it would be wrong to write a program that depended on this exception for its
 * correctness: <i>the fail-fast behavior of iterators should be used only to
 * detect bugs.</i>
 * 
 * <p>
 * This class is a member of the <a href="{@docRoot}
 * /../guide/collections/index.html"> Java Collections Framework</a>.
 * 
 * @author Doug Lea
 * @author Josh Bloch
 * @author Arthur van Hoff
 * @author Neal Gafter
 * @version 1.65, 03/03/05
 * @since 1.2
 */

class HashMapPrimitiveInt<V> {
	
	/**
	 * @author myx
	 */
	static final class Entry<V> {
		final int	key;
		
		Entry<V>	next;
		
		V			value;
		
		/**
		 * Create new entry.
		 */
		Entry(final int k, final V v, final Entry<V> n) {
			this.value = v;
			this.next = n;
			this.key = k;
		}
		
		@Override
		public final boolean equals(final Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof Entry)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			final Entry<V> e = (Entry<V>) o;
			final int k1 = this.key;
			final int k2 = e.key;
			if (k1 == k2) {
				final V v1 = this.value;
				final V v2 = e.value;
				if (v1 == v2 || v1 != null && v1.equals( v2 )) {
					return true;
				}
			}
			return false;
		}
		
		final int getKey() {
			return this.key;
		}
		
		final V getValue() {
			return this.value;
		}
		
		@Override
		public final int hashCode() {
			return this.key;
		}
		
		final V setValue(final V newValue) {
			final V oldValue = this.value;
			this.value = newValue;
			return oldValue;
		}
		
		@Override
		public final String toString() {
			return this.key + "=" + this.value;
		}
	}
	
	/**
	 * The default initial capacity - MUST be a power of two.
	 */
	private static final int	DEFAULT_INITIAL_CAPACITY	= 16;
	
	/**
	 * The load factor used when none specified in constructor.
	 */
	private static final float	DEFAULT_LOAD_FACTOR			= 0.75f;
	
	/**
	 * The maximum capacity, used if a higher value is implicitly specified by
	 * either of the constructors with arguments. MUST be a power of two <=
	 * 1<<30.
	 */
	private static final int	MAXIMUM_CAPACITY			= 1 << 30;
	
	/**
	 * Applies a supplemental hash function to a given hashCode, which defends
	 * against poor quality hash functions. This is critical because HashMap
	 * uses power-of-two length hash tables, that otherwise encounter collisions
	 * for hashCodes that do not differ in lower bits.
	 */
	private static final int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= h >>> 20 ^ h >>> 12;
		return h ^ h >>> 7 ^ h >>> 4;
	}
	
	/**
	 * The load factor for the hash table.
	 * 
	 * @serial
	 */
	private final float				loadFactor;
	
	/**
	 * The number of key-value mappings contained in this identity hash map.
	 */
	private transient int			size;
	
	/**
	 * The table, resized as necessary. Length MUST Always be a power of two.
	 */
	private transient Entry<V>[]	table;
	
	/**
	 * The next size value at which to resize (capacity * load factor).
	 * 
	 * @serial
	 */
	private int						threshold;
	
	/**
	 * Constructs an empty <tt>HashMap</tt> with the default initial capacity
	 * (16) and the default load factor (0.75).
	 */
	@SuppressWarnings("unchecked")
	HashMapPrimitiveInt() {
		this.loadFactor = HashMapPrimitiveInt.DEFAULT_LOAD_FACTOR;
		this.threshold = (int) (HashMapPrimitiveInt.DEFAULT_INITIAL_CAPACITY * HashMapPrimitiveInt.DEFAULT_LOAD_FACTOR);
		this.table = new Entry[HashMapPrimitiveInt.DEFAULT_INITIAL_CAPACITY];
	}
	
	/**
	 * Constructs an empty <tt>HashMap</tt> with the specified initial capacity
	 * and the default load factor (0.75).
	 * 
	 * @param initialCapacity
	 *            the initial capacity.
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative.
	 */
	HashMapPrimitiveInt(final int initialCapacity) {
		this( initialCapacity, HashMapPrimitiveInt.DEFAULT_LOAD_FACTOR );
	}
	
	/**
	 * Constructs an empty <tt>HashMap</tt> with the specified initial capacity
	 * and load factor.
	 * 
	 * @param initialCapacity
	 *            The initial capacity.
	 * @param loadFactor
	 *            The load factor.
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative or the load factor is
	 *             nonpositive.
	 */
	@SuppressWarnings("unchecked")
	HashMapPrimitiveInt(int initialCapacity, final float loadFactor) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException( "Illegal initial capacity: " + initialCapacity );
		}
		if (initialCapacity > HashMapPrimitiveInt.MAXIMUM_CAPACITY) {
			initialCapacity = HashMapPrimitiveInt.MAXIMUM_CAPACITY;
		}
		if (loadFactor <= 0 || Float.isNaN( loadFactor )) {
			throw new IllegalArgumentException( "Illegal load factor: " + loadFactor );
		}
		
		// Find a power of 2 >= initialCapacity
		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity <<= 1;
		}
		
		this.loadFactor = loadFactor;
		this.threshold = (int) (capacity * loadFactor);
		this.table = new Entry[capacity];
	}
	
	/**
	 * Removes all mappings from this map.
	 */
	public final void clear() {
		final Entry<V>[] tab = this.table;
		for (int i = 0; i < tab.length; i++) {
			tab[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified
	 * key.
	 * 
	 * @param key
	 *            The key whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map contains a mapping for the specified
	 *         key.
	 */
	public final boolean containsKey(final int key) {
		final int hash = HashMapPrimitiveInt.hash( key );
		final int i = hash & this.table.length - 1;
		Entry<V> e = this.table[i];
		while (e != null) {
			if (key == e.key) {
				return true;
			}
			e = e.next;
		}
		return false;
	}
	
	/**
	 * Special-case code for containsValue with null argument
	 */
	private final boolean containsNullValue() {
		final Entry<V>[] tab = this.table;
		for (final Entry<V> element : tab) {
			for (Entry<V> e = element; e != null; e = e.next) {
				if (e.value == null) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the specified
	 * value.
	 * 
	 * @param value
	 *            value whose presence in this map is to be tested.
	 * @return <tt>true</tt> if this map maps one or more keys to the specified
	 *         value.
	 */
	public final boolean containsValue(final Object value) {
		if (value == null) {
			return this.containsNullValue();
		}
		final Entry<V>[] tab = this.table;
		for (final Entry<V> element : tab) {
			for (Entry<V> e = element; e != null; e = e.next) {
				if (value.equals( e.value )) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the value to which the specified key is mapped in this identity
	 * hash map, or <tt>null</tt> if the map contains no mapping for this key. A
	 * return value of <tt>null</tt> does not <i>necessarily</i> indicate that
	 * the map contains no mapping for the key; it is also possible that the map
	 * explicitly maps the key to <tt>null</tt>. The <tt>containsKey</tt> method
	 * may be used to distinguish these two cases.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned.
	 * @return the value to which this map maps the specified key, or
	 *         <tt>null</tt> if the map contains no mapping for this key.
	 */
	public final V get(final int key) {
		for (Entry<V> e = this.table[(HashMapPrimitiveInt.hash( key ) & this.table.length - 1)]; e != null; e = e.next) {
			if (e.key == key) {
				return e.value;
			}
		}
		return null;
	}
	
	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 * 
	 * @return <tt>true</tt> if this map contains no key-value mappings.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Associates the specified value with the specified key in this map. If the
	 * map previously contained a mapping for this key, the old value is
	 * replaced.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated.
	 * @param value
	 *            value to be associated with the specified key.
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key. A <tt>null</tt> return can also
	 *         indicate that the HashMap previously associated <tt>null</tt>
	 *         with the specified key.
	 */
	public final V put(final int key, final V value) {
		final int i = HashMapPrimitiveInt.hash( key ) & this.table.length - 1;
		for (Entry<V> e = this.table[i]; e != null; e = e.next) {
			if (e.key == key) {
				final V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
		}
		/**
		 * Add a new entry with the specified key, value and hash code to the
		 * specified bucket. It is the responsibility of this method to resize
		 * the table if appropriate.
		 * 
		 * Subclass overrides this to alter the behavior of put method.
		 */
		final Entry<V> e = this.table[i];
		this.table[i] = new Entry<V>( key, value, e );
		if (this.size++ >= this.threshold) {
			this.resize( 2 * this.table.length );
		}
		return null;
	}
	
	/**
	 * Removes the mapping for this key from this map if present.
	 * 
	 * @param key
	 *            key whose mapping is to be removed from the map.
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key. A <tt>null</tt> return can also
	 *         indicate that the map previously associated <tt>null</tt> with
	 *         the specified key.
	 */
	public final V remove(final int key) {
		final int i = HashMapPrimitiveInt.hash( key ) & this.table.length - 1;
		Entry<V> prev = this.table[i];
		Entry<V> e = prev;
		
		while (e != null) {
			final Entry<V> next = e.next;
			if (key == e.key) {
				this.size--;
				if (prev == e) {
					this.table[i] = next;
				} else {
					prev.next = next;
				}
				return e.value;
			}
			prev = e;
			e = next;
		}
		
		return null;
	}
	
	/**
	 * Removes and returns the entry associated with the specified key in the
	 * HashMap. Returns null if the HashMap contains no mapping for this key.
	 * 
	 * for iterators
	 * 
	 */
	@SuppressWarnings("unused")
	private final Entry<V> removeEntryForKey(final int hash, final int key) {
		final int i = hash & this.table.length - 1;
		Entry<V> prev = this.table[i];
		Entry<V> e = prev;
		
		while (e != null) {
			final Entry<V> next = e.next;
			if (key == e.key) {
				this.size--;
				if (prev == e) {
					this.table[i] = next;
				} else {
					prev.next = next;
				}
				return e;
			}
			prev = e;
			e = next;
		}
		
		return e;
	}
	
	/**
	 * Rehashes the contents of this map into a new array with a larger
	 * capacity. This method is called automatically when the number of keys in
	 * this map reaches its threshold.
	 * 
	 * If current capacity is MAXIMUM_CAPACITY, this method does not resize the
	 * map, but sets threshold to Integer.MAX_VALUE. This has the effect of
	 * preventing future calls.
	 * 
	 * @param newCapacity
	 *            the new capacity, MUST be a power of two; must be greater than
	 *            current capacity unless current capacity is MAXIMUM_CAPACITY
	 *            (in which case value is irrelevant).
	 */
	private final void resize(final int newCapacity) {
		final Entry<V>[] oldTable = this.table;
		final int oldCapacity = oldTable.length;
		if (oldCapacity == HashMapPrimitiveInt.MAXIMUM_CAPACITY) {
			this.threshold = Integer.MAX_VALUE;
			return;
		}
		
		@SuppressWarnings("unchecked")
		final Entry<V>[] newTable = new Entry[newCapacity];
		this.transfer( newTable );
		this.table = newTable;
		this.threshold = (int) (newCapacity * this.loadFactor);
	}
	
	/**
	 * Returns the number of key-value mappings in this map.
	 * 
	 * @return the number of key-value mappings in this map.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Transfer all entries from current table to newTable.
	 */
	private void transfer(final Entry<V>[] newTable) {
		final Entry<V>[] src = this.table;
		final int newCapacity = newTable.length;
		for (int j = 0; j < src.length; j++) {
			Entry<V> e = src[j];
			if (e != null) {
				src[j] = null;
				do {
					final Entry<V> next = e.next;
					final int i = HashMapPrimitiveInt.hash( e.key ) & newCapacity - 1;
					e.next = newTable[i];
					newTable[i] = e;
					e = next;
				} while (e != null);
			}
		}
	}
	
	/**
	 * @return
	 */
	public Collection<V> values() {
		final List<V> result = new ArrayList<V>( this.size );
		final Entry<V>[] tab = this.table;
		for (final Entry<V> element : tab) {
			for (Entry<V> e = element; e != null; e = e.next) {
				result.add( e.value );
			}
		}
		return result;
	}
}
