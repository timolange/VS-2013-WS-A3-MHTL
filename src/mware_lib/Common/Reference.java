package mware_lib.Common;

import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 06.12.13
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public interface Reference {

    public int getPort() ;

    public InetAddress getAdress();

    public String toString();
}
