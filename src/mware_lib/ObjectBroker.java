package mware_lib;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */

import mware_lib.Server.SkeletonServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * core of the middleware:
 * Maintains a ReferenceImpl to the NameService
 * Singleton
 */
public class ObjectBroker {
    NameServiceImpl nameService;
    SkeletonServer skeletonServer;
    ServerSocket serverSocket;
    public ObjectBroker(String serviceName, int port){
        try {
            this.serverSocket = new ServerSocket(0);    //bei 0 wird automatisch ein port ausgewaehlt
            this.nameService = new NameServiceImpl(serviceName, port, serverSocket.getLocalPort());
            this.skeletonServer = new SkeletonServer(serverSocket, nameService);
            this.skeletonServer.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * @return an Implementation for a local NameService
     */
    public NameService getNameService() {
        return nameService;
    }
    /**
     * shuts down the process, the OjectBroker is running in
     * terminates process
     */
    public void shutDown() {
        skeletonServer.interrupt();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * Initializes the ObjectBroker / creates the local NameService
     * @param serviceName
     * hostname or IP of Nameservice
     * @param port
     * port NameService is listening at
     * @return an ObjectBroker Interface to Nameservice
     */
    public static ObjectBroker init(String serviceName, int port) {
        return new ObjectBroker(serviceName,port);
    }
}
