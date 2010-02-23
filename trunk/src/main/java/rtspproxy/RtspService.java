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
 * $Id: RtspService.java 314 2005-12-04 13:56:30Z merlimat $
 * 
 * $URL: http://svn.berlios.de/svnroot/repos/rtspproxy/tags/3.0-ALPHA2/src/main/java/rtspproxy/RtspService.java $
 * 
 */

package rtspproxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import rtspproxy.filter.RtspSocketAcceptorConfig;

import rtspproxy.proxy.ClientSide;
import rtspproxy.rtsp.Handler;

/**
 * @author Matteo Merli
 */
public class RtspService implements ProxyService
{

	private static Logger log = Logger.getLogger( RtspService.class );

        IoAcceptor acceptor = new SocketAcceptor();

	public void start() throws IOException
	{
		// get port and network interface from config file
		int[] ports = Config.getIntArray( "proxy.rtsp.port", Handler.DEFAULT_RTSP_PORT );
		String netInterface = Config.get( "proxy.rtsp.interface", null );

		for ( int port : ports ) {
			try {

                            SocketAddress sa;
                            if (netInterface == null) // is this test needed?
                                sa = new InetSocketAddress(port);
                            else
                                sa = new InetSocketAddress(netInterface, port);

                            // check if we need per - bind config - it is the same
                            SocketAcceptorConfig cfg = new RtspSocketAcceptorConfig();

                            acceptor.bind(sa, new ClientSide(), cfg);
/*
				Service service;
				if ( netInterface == null )
					service = new Service( "RtspService", TransportType.SOCKET, port );
				else
					service = new Service( "RtspService", TransportType.SOCKET,
							new InetSocketAddress( netInterface, port ) );

				Reactor.getRegistry().bind( service, new ClientSide(), new RtspClientFilters() );
*/
				log.info( "RtspService Started - Listening on: "
						+ InetAddress.getByName( netInterface ) + ":" + port );

			} catch ( IOException e ) {
				log.fatal( e.getMessage() + " (port = " + port + ")" );
				throw e;
			}
		}
	}

	public void stop() throws Exception
	{
		acceptor.unbindAll();
/*		for ( Object service : Reactor.getRegistry().getServices( "RtspService" ) ) {
			Reactor.getRegistry().unbind( (Service) service );
		}
*/
		log.info( "RtspService Stopped" );
	}
}
