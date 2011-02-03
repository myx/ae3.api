/*
 * Created on 22.03.2006
 */
package ru.myx.ae3.status;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author myx
 * 
 */
public class StatusInfo {
	class StatusMap extends AbstractMap<String, Object> {
		class StatusSet extends AbstractSet<Map.Entry<String, Object>> {
			@Override
			public boolean contains(final Object o) {
				return StatusInfo.this.statusList.contains( o );
			}
			
			@Override
			public Iterator<Map.Entry<String, Object>> iterator() {
				return StatusInfo.this.statusList.iterator();
			}
			
			@Override
			public boolean remove(final Object o) {
				return StatusInfo.this.statusList.remove( o );
			}
			
			@Override
			public int size() {
				return StatusInfo.this.statusList.size();
			}
		}
		
		Set<Map.Entry<String, Object>>	ss	= null;
		
		@Override
		public Set<Map.Entry<String, Object>> entrySet() {
			return this.ss == null
					? this.ss = new StatusSet()
					: this.ss;
		}
		
		@Override
		public Object put(final String key, final Object o) {
			StatusInfo.this.put( key, o );
			return null;
		}
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	protected String							title;
	
	/**
	 * 
	 */
	protected Map<String, Object>				statusMap;
	
	/**
	 * 
	 */
	protected List<Map.Entry<String, Object>>	statusList;
	
	/**
	 * 
	 */
	protected Map.Entry<String, Object>[]		statusData;
	
	StatusProvider[]							childProviders;
	
	/**
	 * @param title
	 */
	public StatusInfo(final String title) {
		this.title = title;
	}
	
	/**
	 * @return providers
	 */
	public StatusProvider[] childProviders() {
		return this.childProviders;
	}
	
	/**
	 * @return maps
	 */
	@SuppressWarnings("unchecked")
	public Map.Entry<String, Object>[] getStatusAsArray() {
		if (this.statusData == null) {
			final List<Map.Entry<String, Object>> result = this.getStatusAsList();
			this.statusData = result.toArray( new Map.Entry[result.size()] );
		}
		return this.statusData;
	}
	
	/**
	 * @return list
	 */
	public List<Map.Entry<String, Object>> getStatusAsList() {
		if (this.statusList == null) {
			this.statusList = new ArrayList<Map.Entry<String, Object>>();
			if (this.statusData != null) {
				for (final Map.Entry<String, Object> element : this.statusData) {
					this.statusList.add( element );
				}
			} else if (this.statusMap != null) {
				this.statusList.addAll( this.statusMap.entrySet() );
			}
		}
		return this.statusList;
	}
	
	/**
	 * @return map
	 */
	public Map<?, ?> getStatusAsMap() {
		if (this.statusMap == null) {
			this.getStatusAsList();
			this.statusMap = new StatusMap();
		}
		return this.statusMap;
	}
	
	/**
	 * @return string
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * @param entries
	 */
	public void put(final List<Map.Entry<String, Object>> entries) {
		this.getStatusAsList();
		this.getStatusAsMap();
		this.statusList.addAll( entries );
		if (!(this.statusMap instanceof StatusMap)) {
			for (int i = 0; i < entries.size(); i++) {
				final Map.Entry<String, Object> entry = entries.get( i );
				this.statusMap.put( entry.getKey(), entry.getValue() );
			}
		}
		this.statusData = null;
	}
	
	/**
	 * @param entry
	 */
	public void put(final Map.Entry<String, Object> entry) {
		this.getStatusAsList();
		this.getStatusAsMap();
		this.statusList.add( entry );
		if (!(this.statusMap instanceof StatusMap)) {
			this.statusMap.put( entry.getKey(), entry.getValue() );
		}
		this.statusData = null;
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final boolean value) {
		this.put( name, value
				? Boolean.TRUE
				: Boolean.FALSE );
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final double value) {
		this.put( name, new Double( value ) );
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final float value) {
		this.put( name, new Float( value ) );
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final int value) {
		this.put( name, new Integer( value ) );
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final long value) {
		this.put( name, new Long( value ) );
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final Object value) {
		this.put( new Map.Entry<String, Object>() {
			@Override
			public boolean equals(final Object o) {
				return name.equals( o );
			}
			
			@Override
			public String getKey() {
				return name;
			}
			
			@Override
			public Object getValue() {
				return value;
			}
			
			@Override
			public int hashCode() {
				return name.hashCode();
			}
			
			@Override
			public Object setValue(final Object value) {
				return this.getValue();
			}
		} );
	}
	
	/**
	 * @param title
	 * @return same status
	 */
	public StatusInfo setTitle(final String title) {
		this.title = title;
		return this;
	}
	
	@Override
	public String toString() {
		return this.toString( "" );
	}
	
	/**
	 * @param prefix
	 * @return string
	 */
	public String toString(final String prefix) {
		final Map.Entry<?, ?>[] status = this.getStatusAsArray();
		final StringBuilder result = new StringBuilder( 128 );
		result.append( prefix ).append( this.getTitle() ).append( ":\r\n" );
		int maxFieldName = 0;
		for (final Map.Entry<?, ?> current : status) {
			final Object value = current.getValue();
			if (!(value instanceof StatusInfo || value instanceof StatusProvider)) {
				final String name = current.getKey().toString();
				if (name.length() > maxFieldName) {
					maxFieldName = name.length();
				}
			}
		}
		final char[] tmp = new char[maxFieldName];
		for (int i = tmp.length - 1; i >= 0; i--) {
			tmp[i] = ' ';
		}
		for (final Map.Entry<?, ?> current : status) {
			Object value = current.getValue();
			if (value instanceof StatusProvider) {
				value = ((StatusProvider) value).getStatus();
			}
			if (value instanceof StatusInfo) {
				result.append( "\r\n" ).append( ((StatusInfo) value).toString( prefix + '\t' ) );
			} else {
				final String name = current.getKey().toString();
				result.append( prefix ).append( '\t' ).append( name ).append( tmp, 0, maxFieldName - name.length() + 1 )
						.append( "= " ).append( value ).append( "\r\n" );
			}
		}
		if (this.childProviders != null && this.childProviders.length > 0) {
			for (final StatusProvider childProvider : this.childProviders) {
				final StatusInfo current = childProvider.getStatus();
				result.append( "\r\n" ).append( current.toString( prefix + '\t' ) );
			}
		}
		return result.toString();
	}
}
