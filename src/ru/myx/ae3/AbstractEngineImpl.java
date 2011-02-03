package ru.myx.ae3;

import java.util.Date;

/**
 * Extension methods and interfaces
 * 
 * @author myx
 */
abstract class AbstractEngineImpl {
	/**
	 * GUID generator implementation
	 * 
	 * @author myx
	 */
	public abstract static class GuidFactory {
		/**
		 * called on replace - to allow to stop any activities.
		 */
		public void destroy() {
			// empty
		}
		
		/**
		 * @return new guid
		 */
		public abstract String guid();
		
		/**
		 * toString method must always return new GUID
		 * 
		 * @return
		 */
		public abstract Object producer();
		
		/**
		 * 
		 */
		public void start() {
			// empty
		}
		
		@Override
		public abstract String toString();
	}
	
	/**
	 * RAND generator implementation
	 * 
	 * @author myx
	 * 
	 */
	public abstract static class RandFactory {
		/**
		 * called on replace - to allow to stop any activities.
		 */
		public void destroy() {
			// empty
		}
		
		/**
		 * @return new random
		 */
		public abstract long rand();
		
		/**
		 * 
		 */
		public void start() {
			// empty
		}
		
		@Override
		public abstract String toString();
	}
	
	/**
	 * RAND generator implementation
	 * 
	 * @author myx
	 * 
	 */
	public abstract static class TimeFactory {
		/**
		 * @return current time and this date should be always current
		 */
		public abstract Date date();
		
		/**
		 * called on replace - to allow to stop any activities.
		 */
		public void destroy() {
			// empty
		}
		
		/**
		 * 
		 */
		public void start() {
			// empty
		}
		
		@Override
		public abstract String toString();
	}
	
	/**
	 * @return current guid generator name
	 */
	public static final String getGuidGeneratorName() {
		return String.valueOf( Engine.GUID_GENERATOR );
	}
	
	/**
	 * @return current rand generator name
	 */
	public static final String getRandGeneratorName() {
		return String.valueOf( Engine.RAND_GENERATOR );
	}
	
	/**
	 * @return current time retriever name
	 */
	public static final String getTimeRetrieverName() {
		return String.valueOf( Engine.TIME_RETRIEVER );
	}
	
	AbstractEngineImpl.GuidFactory	GUID_GENERATOR;
	
	AbstractEngineImpl.TimeFactory	TIME_RETRIEVER;
	
	AbstractEngineImpl.RandFactory	RAND_GENERATOR;
	
	AbstractEngineImpl() {
		// empty
	}
	
	abstract String getVersion();
	
	abstract void start();
}
