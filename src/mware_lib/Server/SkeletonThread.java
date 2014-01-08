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

        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void run(){
        Object returnVal = null;
        MethodInvokeRequest methodInvokeRequest = null;
        try {
            methodInvokeRequest = readFromClient();
            Object servant = skeletonServer.getServant(methodInvokeRequest.getObjectIdentifier());
            synchronized (servant){
                Method method = servant.getClass().getMethod(methodInvokeRequest.getCommand(), methodInvokeRequest.getParameterClasses());
                method.setAccessible(true);
                returnVal = method.invoke(servant, methodInvokeRequest.getParameter());
                //servant.notifyAll();
            }

        }  catch (NoSuchMethodException e) {
            returnVal = e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            returnVal = e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            returnVal = e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e){
            returnVal = e;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            writeToClient(returnVal);
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
