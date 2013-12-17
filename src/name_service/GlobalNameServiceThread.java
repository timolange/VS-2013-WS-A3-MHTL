package name_service;

import mware_lib.Common.GNSRequest;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 30.11.13
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class GlobalNameServiceThread extends Thread {
    Socket socket;
    GlobalNameService globalNameService;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public GlobalNameServiceThread(Socket socket, GlobalNameService globalNameService){
        this.socket = socket;
        this.globalNameService = globalNameService;
    }

    @Override
    public void run(){
        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());

            GNSRequest GNSRequest = readFromClient();
            Method method = globalNameService.getClass().getMethod(GNSRequest.getCommand(), GNSRequest.getParameterClasses());
            Object returnVal = method.invoke(globalNameService, GNSRequest.getParameter());

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
            globalNameService.decreaseThreadCount();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private GNSRequest readFromClient() {
        GNSRequest gnsRequest = null;
        try {
            gnsRequest = (GNSRequest)inFromClient.readObject();
            //System.out.println("GNS erhalten:"+gnsRequest);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return gnsRequest;
    }

    private void writeToClient(Object reply) {
        try {
            outToClient.writeObject(reply);
            //System.out.println("GNS gesendet"+reply);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
