package ru.myx.ae3.vfs;

/**
 * @author myx Legend:<br>
 *         <li>B - blocks garbage collection</li> <li>N - share with
 *         neighbours????</li> <li>C - send in default contents</li> <li>S -
 *         indexed for and used in search</li>
 */
public enum TreeReadType {
	/**
	 */
	ANY {
		@Override
		public boolean isValid(final TreeLinkType type) {
			return true;
		}
	},
	/**
	 */
	ITERABLE {
		@Override
		public boolean isValid(final TreeLinkType type) {
			return !type.isHidden();
		}
	},
	/**
	 */
	SEARCHABLE {
		@Override
		public boolean isValid(final TreeLinkType type) {
			return type.allowsSearchIndexing();
		}
	},
	/**
	 */
	PERSISTENT {
		@Override
		public boolean isValid(final TreeLinkType type) {
			return type.blocksGarbageCollection();
		}
	},
	;
	
	private static final TreeReadType[]	ALL	= TreeReadType.values();
	
	/**
	 * @return
	 */
	public static final int valueCount() {
		return TreeReadType.ALL.length;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public static final TreeReadType valueForIndex(final int index) {
		return TreeReadType.ALL[index];
	}
	
	/**
	 * @param type
	 * @return
	 */
	public abstract boolean isValid(final TreeLinkType type);
}
