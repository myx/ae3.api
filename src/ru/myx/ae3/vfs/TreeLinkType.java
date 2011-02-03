package ru.myx.ae3.vfs;

/**
 * @author myx Legend:<br>
 *         <li>B - blocks garbage collection</li> <li>N - share with
 *         neighbours????</li> <li>C - send in default contents</li> <li>S -
 *         indexed for and used in search</li>
 */
public enum TreeLinkType {
	/**
	 * methods: evaluateField<br>
	 * <li>B N C S</li> <li>- - - -</li>
	 */
	NO_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return false;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return false;
		}
		
		@Override
		public boolean isHidden() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return false;
		}
	},
	/**
	 * methods: evaluateField<br>
	 * <li>B N C S</li> <li>- - - -</li>
	 */
	CACHE_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return false;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return false;
		}
		
		@Override
		public boolean isHidden() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return false;
		}
	},
	/**
	 * methods: evaluateField<br>
	 * <li>B N C S</li> <li>- + - -</li>
	 */
	PRODUCT_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return false;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return false;
		}
		
		@Override
		public boolean isHidden() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return true;
		}
	},
	/**
	 * methods: evaluateField<br>
	 * <li>B N C S</li> <li>+ + - -</li>
	 */
	LOCAL_PRIVATE_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return false;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return true;
		}
		
		@Override
		public boolean isHidden() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return false;
		}
	},
	/**
	 * methods: evaluateField<br>
	 * <li>B N C S</li> <li>+ + - -</li>
	 */
	HIDDEN_FIELD_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return false;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return true;
		}
		
		@Override
		public boolean isHidden() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return true;
		}
	},
	/**
	 * methods: evaluateField / getChildByName<br>
	 * <li>B N C S</li> <li>+ + - -</li>
	 */
	HIDDEN_TREE_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return false;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return true;
		}
		
		@Override
		public boolean isHidden() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return true;
		}
	},
	/**
	 * methods: evaluateField<br>
	 * <li>B N C S</li> <li>+ + + +</li>
	 */
	PUBLIC_FIELD_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return true;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return true;
		}
		
		@Override
		public boolean isHidden() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return true;
		}
	},
	/**
	 * methods: evaluateField / getChildren / getChildByName<br>
	 * <li>B N C S</li> <li>+ + + +</li>
	 */
	PUBLIC_TREE_REFERENCE {
		@Override
		public boolean allowsSearchIndexing() {
			return true;
		}
		
		@Override
		public boolean blocksGarbageCollection() {
			return true;
		}
		
		@Override
		public boolean isHidden() {
			return false;
		}
		
		@Override
		public boolean sentOutsideInDefaultQuery() {
			return true;
		}
		
		@Override
		public boolean sentOutsideInExactQuery() {
			return true;
		}
	},
	;
	
	private static final TreeLinkType[]	ALL	= TreeLinkType.values();
	
	/**
	 * @return
	 */
	public static final int valueCount() {
		return TreeLinkType.ALL.length;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public static final TreeLinkType valueForIndex(final int index) {
		return TreeLinkType.ALL[index];
	}
	
	/**
	 * @return
	 */
	public abstract boolean allowsSearchIndexing();
	
	/**
	 * @return
	 */
	public abstract boolean blocksGarbageCollection();
	
	/**
	 * @return
	 */
	public abstract boolean isHidden();
	
	/**
	 * @return
	 */
	public abstract boolean sentOutsideInDefaultQuery();
	
	/**
	 * @return
	 */
	public abstract boolean sentOutsideInExactQuery();
}
