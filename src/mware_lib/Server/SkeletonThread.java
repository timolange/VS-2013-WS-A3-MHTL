package mware_lib.Server;

import mware_lib.Common.MethodInvokeRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 03.12.13
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class SkeletonThread extends Thread {
    Socket socket;
    SkeletonServer skeletonServer;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public SkeletonThread(Socket socket, SkeletonServer skeletonServer){
        this.socket = socket;
        this.skeletonServer = skeletonServer;
    }

    @Override
    public void run(){
        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());

            MethodInvokeRequest methodInvokeRequest = readFromClient();
            Object servant = skeletonServer.getServant(methodInvokeRequest.getObjectIdentifier());
            Object returnVal = null;
            synchronized (servant){
                Method method = servant.getClass().getMethod(methodInvokeRequest.getCommand(), methodInvokeRequest.getParameterClasses());
                returnVal = method.invoke(servant, methodInvokeRequest.getParameter());
                //servant.notifyAll();
            }

            writeToClient(returnVal);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            skeletonServer.decreaseThreadCount();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private MethodInvokeRequest readFromClient() {
        MethodInvokeRequest methodInvokeRequest = null;
        try {
            methodInvokeRequest = (MethodInvokeRequest)inFromClient.readObject();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return methodInvokeRequest;
    }

    private void writeToClient(Object reply) {
        try {
            outToClient.writeObject(reply);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
