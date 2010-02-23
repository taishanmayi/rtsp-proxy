/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rtspproxy.filter;

import org.apache.mina.transport.socket.nio.SocketConnectorConfig;

/**
 *
 * @author spd
 */
public class RtspSocketConnectorConfig extends SocketConnectorConfig {

    public RtspSocketConnectorConfig() {
        addRtspCodecFilter();
    }

    protected void addRtspCodecFilter() {
        getFilterChain().addLast("codec", RtspFilters.getCodecFilter());
    }
}
