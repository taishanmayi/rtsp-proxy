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
 * $Id: RtspEncoder.java 290 2005-11-24 19:43:03Z merlimat $
 * 
 * $URL: http://svn.berlios.de/svnroot/repos/rtspproxy/tags/3.0-ALPHA2/src/main/java/rtspproxy/rtsp/RtspEncoder.java $
 * 
 */

package rtspproxy.rtsp;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderException;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Encode a RTSP message into a buffer for sending.
 */
public class RtspEncoder implements ProtocolEncoder
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.mina.protocol.ProtocolEncoder#encode(org.apache.mina.protocol.ProtocolSession,
	 *      java.lang.Object, org.apache.mina.protocol.ProtocolEncoderOutput)
	 */
	public void encode( IoSession session, Object message, ProtocolEncoderOutput out )
			throws ProtocolEncoderException
	{
		// Serialization to string is already provided in RTSP messages.
		String val = ( (RtspMessage) message ).toString();
		/*
		ByteBuffer buf = ByteBuffer.allocate( val.length() );
		for ( int i = 0; i < val.length(); i++ ) {
			buf.put( (byte) val.charAt( i ) );
		}

		buf.flip();
		*/
		
		// TODO: Alternative implementation, should be better.
		ByteBuffer buf = ByteBuffer.wrap( val.getBytes() );
		
		out.write( buf );
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#dispose(org.apache.mina.common.IoSession)
	 */
	public void dispose( IoSession arg0 ) throws Exception
	{
		// Don't need to do nothing
	}
}
