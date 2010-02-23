/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   Copyright (C) 2005 - Matteo Merli - matteo.merli@gmail.com            *
 *                                                                         *
 ***************************************************************************/

/*
 * $Id: RtspMessage.java 318 2005-12-07 22:53:57Z merlimat $
 * 
 * $URL: http://svn.berlios.de/svnroot/repos/rtspproxy/tags/3.0-ALPHA2/src/main/java/rtspproxy/rtsp/RtspMessage.java $
 * 
 */

package rtspproxy.rtsp;

import java.nio.CharBuffer;
import java.util.Properties;

import rtspproxy.Config;

/**
 * Base abstract class for RTSP messages. 
 * 
 * @author mat
 */
public abstract class RtspMessage
{

	/**
	 * RTSP Message Type
	 */
	public enum Type {
		/** Generic message (internal use) */
		TypeNone,
		/** Request message */
		TypeRequest,
		/** Response message */
		TypeResponse
	};

	private int sequenceNumber;
	private Properties headers;
	private StringBuffer buffer;

	/**
	 * Constructor.
	 */
	public RtspMessage()
	{
		sequenceNumber = 0;
		headers = new Properties();
		buffer = new StringBuffer();
	}

	/**
	 * @return the RTSP type of the message
	 */
	public Type getType()
	{
		return Type.TypeNone;
	}

	/**
	 * Adds a new header to the RTSP message.
	 * 
	 * @param key
	 *        The name of the header
	 * @param value
	 *        Its value
	 */
	public void setHeader( String key, String value )
	{
		// Handle some bad formatted headers
		if ( key.compareToIgnoreCase( "content-length" ) == 0 ) {
			headers.setProperty( "Content-Length", value );
		} else {
			headers.setProperty( key, value );
		}
	}

	/**
	 * @param key
	 *        Header name
	 * @return the value of the header
	 */
	public String getHeader( String key )
	{
		return headers.getProperty( key );
	}

	/**
	 * 
	 * @param key
	 *        Header name
	 * @param defaultValue
	 *        the default value
	 * @return the value of the header of <i>defaultValue</i> if header is not
	 *         found
	 */
	public String getHeader( String key, String defaultValue )
	{
		String value = getHeader( key );
		if ( value == null )
			return defaultValue;
		else
			return value;
	}

	/**
	 * Remove an header from the message headers collection
	 * 
	 * @param key
	 *        the name of the header
	 */
	public void removeHeader( String key )
	{
		headers.remove( key );
	}

	/**
	 * Formats all the headers into a string ready to be sent in a RTSP message.
	 * 
	 * <pre>
	 * Header1: Value1
	 * Header2: value 2
	 * ... 
	 * </pre>
	 * 
	 * @return a string containing the serialzed headers
	 */
	public String getHeadersString()
	{
		StringBuilder buf = new StringBuilder();
		for ( Object key : headers.keySet() ) {
			String value = headers.getProperty( (String) key );
			buf.append( key + ": " + value + CRLF );
		}
		return buf.toString();
	}

	/**
	 * 
	 * @return the number of headers owned by the message
	 */
	public int getHeadersCount()
	{
		return headers.size();
	}

	/**
	 * Sets common headers like <code>Server</code> and <code>Via</code>.
	 */
	public void setCommonHeaders()
	{
		String proxy = Config.getProxySignature();
		if ( getHeader( "Server" ) != null )
			setHeader( "Via", proxy );
		else
			setHeader( "Server", proxy );
	}

	/**
	 * 
	 * @param buffer StringBuffer containing the contents
	 */
	public void setBuffer( StringBuffer buffer )
	{
		this.buffer = buffer;
	}

	/**
	 * @param other buffer with content to be appended
	 */
	public void appendToBuffer( StringBuffer other )
	{
		this.buffer.append( other );
	}
	
	/**
	 * @param other buffer with content to be appended
	 */
	public void appendToBuffer( CharBuffer other )
	{
		this.buffer.append(other);
	}

	/**
	 * @return the content buffer
	 */
	public StringBuffer getBuffer()
	{
		return buffer;
	}

	/**
	 * @return the size of the content buffer
	 */
	public int getBufferSize()
	{
		return buffer.length();
	}

	// CRLF
	public static final String CRLF = "\r\n";

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber()
	{
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *        The sequenceNumber to set.
	 */
	public void setSequenceNumber( int sequenceNumber )
	{
		this.sequenceNumber = sequenceNumber;
	}
}
