package mware_lib.Client;

import mware_lib.Common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.Remote;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 03.12.13
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
public class Stub {
    private static String GET_CLASS = "getClass";
    public String name;
    private InetSocketAddress reference;

    public Stub(String name, InetSocketAddress reference) {
        this.name = name;
        this.reference = reference;
    }

    public Object getResponseFromRemoteObject(MethodInvokeRequest methodInvokeRequest) throws IOException, ClassNotFoundException {
        Socket tcpSocket = new Socket(reference.getAddress(), reference.getPort());
        //ObjectOutputStream muss zuerst erzeugt werden, da ObjectInputStream sofort nach dem erzeugen blockiert bis was geschickt oder versendet wurde!!!
        ObjectOutputStream objOS = new ObjectOutputStream(tcpSocket.getOutputStream());
        ObjectInputStream objIS = new ObjectInputStream(tcpSocket.getInputStream());

        objOS.writeObject(methodInvokeRequest);
        Object methodReturn = objIS.readObject();
        tcpSocket.close();

        return methodReturn;
    }

//    public Object getProxyObject(){
//        Object proxy = null;
//        try {
//            MethodInvokeRequest req1 = new MethodInvokeRequest(name, GET_CLASS, null,null);
//            Class remoteClass = (Class)getResponseFromRemoteObject(req1);
//
//            proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), remoteClass.getInterfaces(), new InvokeHandler(this, name));
//
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        return proxy;
//    }
//
//    public class InvokeHandler implements InvocationHandler {
//        private Stub stub;
//        private String objectIdentifier;
//
//        public InvokeHandler(Stub stub, String objectIdentifier) {
//            this.stub = stub;
//            this.objectIdentifier = objectIdentifier;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            MethodInvokeRequest req = new MethodInvokeRequest(objectIdentifier, method.getName(), args, method.getParameterTypes());
//            Object response = getResponseFromRemoteObject(req);
//
//            return response;
//        }
//    }
}
