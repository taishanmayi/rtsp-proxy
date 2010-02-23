/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtspproxy.filter;

import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import rtspproxy.Config;
import rtspproxy.filter.authentication.AuthenticationFilter;
import rtspproxy.filter.ipaddress.IpAddressFilter;

/**
 * TODO: this needs heavy refactonring
 * @author spd
 */
public class RtspSocketAcceptorConfig extends SocketAcceptorConfig {

    // These filters are instanciated only one time, when requested
    private static IpAddressFilter ipAddressFilter = null;
    private static AuthenticationFilter authenticationFilter = null;

    public RtspSocketAcceptorConfig() {
        addIpAddressFilter();
        addRtspCodecFilter();
        addAuthenticationFilter();
    }

    protected void addIpAddressFilter() {
        boolean enableIpAddressFilter = Config.getBoolean(
                "proxy.filter.ipaddress.enable", false);

        if (enableIpAddressFilter) {
            if (ipAddressFilter == null) {
                ipAddressFilter = new IpAddressFilter();
            }
            getFilterChain().addLast("ipAddressFilter", ipAddressFilter);
        }
    }

    protected void addRtspCodecFilter() {
        getFilterChain().addLast("codec", RtspFilters.getCodecFilter());
    }

    protected void addAuthenticationFilter() {
        boolean enableAuthenticationFilter = Config.getBoolean(
                "proxy.filter.authentication.enable", false);

        if (enableAuthenticationFilter) {
            if (authenticationFilter == null) {
                authenticationFilter = new AuthenticationFilter();
            }
            getFilterChain().addLast("authentication", authenticationFilter);
        }
    }
}
