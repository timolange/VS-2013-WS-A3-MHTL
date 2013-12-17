package name_service;

import mware_lib.Common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 30.11.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class GlobalNameServiceTest {

    public static Reference getResponseFromServer(GNSRequest GNSRequest) throws IOException, ClassNotFoundException {
        Socket tcpSocket = new Socket(InetAddress.getLocalHost(), Config.GLOBAL_NAME_SERVICE_PORT);
        //ObjectOutputStream muss zuerst erzeugt werden, da ObjectInputStream sofort nach dem erzeugen blockiert bis was geschickt oder versendet wurde!!!
        ObjectOutputStream objOS = new ObjectOutputStream(tcpSocket.getOutputStream());
        ObjectInputStream objIS = new ObjectInputStream(tcpSocket.getInputStream());

        objOS.writeObject(GNSRequest);
        Object response = objIS.readObject();

        return (response instanceof Reference) ? ((Reference)response) : null;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        long starttime = System.currentTimeMillis();
        GlobalNameService ns = new GlobalNameService(Config.GLOBAL_NAME_SERVICE_PORT);
        ns.start();

        Reference ref = new ReferenceImpl(50001, InetAddress.getLocalHost());
        GNSRequest req1 = new GNSRequestImpl("rebind",new Object[]{"test", ref}, new Class[]{String.class, Reference.class});
        GNSRequest req2 = new GNSRequestImpl("resolve",new Object[]{"test"}, new Class[]{String.class});

        getResponseFromServer(req1);
        Reference returnVal = getResponseFromServer(req2);

        long endtime = System.currentTimeMillis();
        System.out.println("ReturnValue: "+returnVal);
        System.out.println("Benoetigte Zeit:"+(endtime-starttime)+"ms");
        ns.shutDown();


    }
}
