package ru.myx.s4;

/**
 * @author myx
 * 
 */
public enum LinkageMode {
	/**
	 * lower: <br>
	 * evaluate field access
	 * 
	 * upper: <br>
	 * get parent
	 */
	WEAK_REFERENCE {
		@Override
		boolean blocksGarbageCollection() {
			return false;
		}
	},
	/**
	 * lower: <br>
	 * evaluate field access
	 */
	CACHE_REFERENCE {
		@Override
		boolean blocksGarbageCollection() {
			return false;
		}
	},
	/**
	 * lower: <br>
	 * evaluate field access
	 */
	STRONG_REFERENCE {
		@Override
		boolean blocksGarbageCollection() {
			return true;
		}
	},
	/**
	 * lower: <br>
	 * get children<br>
	 * get child by name<br>
	 * evaluate field access
	 */
	TREE_HIERARCHY {
		@Override
		boolean blocksGarbageCollection() {
			return true;
		}
	},
	;
	
	abstract boolean blocksGarbageCollection();
}
