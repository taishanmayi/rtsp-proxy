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
 * $Id: RtspFilters.java 336 2005-12-08 20:48:05Z merlimat $
 * 
 * $URL: http://svn.berlios.de/svnroot/repos/rtspproxy/tags/3.0-ALPHA2/src/main/java/rtspproxy/filter/RtspFilters.java $
 * 
 */
package rtspproxy.filter;

import org.apache.mina.common.IoFilter;
import org.apache.mina.common.IoFilterChain;
import org.apache.mina.common.IoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import rtspproxy.Config;
import rtspproxy.Reactor;
import rtspproxy.filter.authentication.AuthenticationFilter;
import rtspproxy.filter.ipaddress.IpAddressFilter;
import rtspproxy.filter.rewrite.RequestUrlRewritingImpl;
import rtspproxy.rtsp.RtspDecoder;
import rtspproxy.rtsp.RtspEncoder;

/**
 * Base class for filter chains based on configuration settings.
 * 
 * @author Matteo Merli
 */
public class RtspFilters {

    private static ProtocolCodecFactory codecFactory = new ProtocolCodecFactory() {

        // Decoders can be shared
        private ProtocolEncoder rtspEncoder = new RtspEncoder();
        private ProtocolDecoder rtspDecoder = new RtspDecoder();

        public ProtocolEncoder getEncoder() {
            return rtspEncoder;
        }

        public ProtocolDecoder getDecoder() {
            return rtspDecoder;
        }
    };
    private static IoFilter codecFilter = new ProtocolCodecFilter(codecFactory);

    // TODO this is damn ugly
    public static IoFilter getCodecFilter() {
        return codecFilter;
    }
}
