package mware_lib.Common;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 30.11.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public class ReferenceImpl implements Reference, Serializable {
    private int port;
    private InetAddress adress;

    public ReferenceImpl(int port, InetAddress adress) {
        this.port = port;
        this.adress = adress;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAdress() {
        return adress;
    }

    @Override
    public String toString() {
        return "ReferenceImpl{" +
                "port=" + port +
                ", adress=" + adress +
                '}';
    }
}
