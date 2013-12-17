package mware_lib;

import mware_lib.Client.Stub;
import mware_lib.Common.GNSRequest;
import mware_lib.Common.GNSRequestImpl;
import mware_lib.Common.Reference;
import mware_lib.Common.ReferenceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 03.12.13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class NameServiceImpl extends NameService {
    private Map<String, Object> registredObjects = new HashMap<String, Object>();
    private String globalNameServiceHost;
    private int globalNameServicePort;
    private int localServerPort;

    public NameServiceImpl(String GNSHost, int GSNPort, int localServerPort){
        this.globalNameServiceHost = GNSHost;
        this.globalNameServicePort = GSNPort;
        this.localServerPort = localServerPort;
    }

    public Object getRegistredObject(String ObjectIdentifier) {
        return registredObjects.get(ObjectIdentifier);
    }

    @Override
    public synchronized void rebind(Object servant, String name) {
        try {
            registredObjects.put(name, servant);

            Reference ref = new ReferenceImpl(localServerPort, InetAddress.getLocalHost());
            GNSRequest req = new GNSRequestImpl(GNSRequestImpl.REBIND,new Object[]{name, ref}, new Class[]{name.getClass(), Reference.class});
            Object response = getResponseFromGlobalNameService(req);

        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Override
    public Object resolve(String name) {
        Object res = null;
        try {
            GNSRequest req = new GNSRequestImpl(GNSRequestImpl.RESOLVE,new Object[]{name}, new Class[]{name.getClass()});
            Reference ref = getResponseFromGlobalNameService(req);

            res = new Stub(name, ref);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return res;
    }

    private Reference getResponseFromGlobalNameService(GNSRequest GNSRequest) throws IOException, ClassNotFoundException {
        Socket tcpSocket = new Socket(globalNameServiceHost, globalNameServicePort);
        //ObjectOutputStream muss zuerst erzeugt werden, da ObjectInputStream sofort nach dem erzeugen blockiert bis was geschickt oder versendet wurde!!!
        ObjectOutputStream objOS = new ObjectOutputStream(tcpSocket.getOutputStream());
        ObjectInputStream objIS = new ObjectInputStream(tcpSocket.getInputStream());

        objOS.writeObject(GNSRequest);
        Object response = objIS.readObject();
        tcpSocket.close();

        return (response instanceof Reference) ? ((Reference)response) : null;
    }
}
