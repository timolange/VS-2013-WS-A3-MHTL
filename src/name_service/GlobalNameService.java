package name_service;

import mware_lib.Common.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 22:42
 * To change this template use File | Settings | File Templates.
 */
public class GlobalNameService extends Thread{
    public static final int MAX_Clients = 10;
    ServerSocket welcomeSocket;
    private ConcurrentHashMap<String, InetSocketAddress> reverenceList;
    ExecutorService executor;
    private int port;
    private int threadCount = 0;

    public GlobalNameService(int port){
        this.port = port;
        this.reverenceList = new ConcurrentHashMap<String, InetSocketAddress>();
        this.executor = Executors.newFixedThreadPool(MAX_Clients);
    }

    public void shutDown(){
        Thread.currentThread().interrupt();
        try {
            welcomeSocket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    //getter zum testen
    public ConcurrentHashMap<String, InetSocketAddress> getReverenceList() {
        return reverenceList;
    }

    public synchronized void decreaseThreadCount() {
        threadCount--;
    }

    public synchronized void increaseThreadCount() {
        threadCount++;
    }

    public void rebind(String name, InetSocketAddress ref){
        reverenceList.put(name,ref);
    }

    public InetSocketAddress resolve(String name){
        return reverenceList.get(name);
    }

    @Override
    public void run(){
        System.out.println("Global Name Service gestartet!");
        Socket connectionSocket;
        try {
            welcomeSocket = new ServerSocket(port);
            while (!isInterrupted()) {
                if (threadCount < MAX_Clients) {
                    if (welcomeSocket.isClosed()) {
                        welcomeSocket = new ServerSocket(port);
                    }
                    connectionSocket = welcomeSocket.accept();
                    increaseThreadCount();
                    executor.execute(new GlobalNameServiceThread(connectionSocket, this));
                } else if(!welcomeSocket.isClosed()){
                    welcomeSocket.close();
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            executor.shutdown();
            try {
                welcomeSocket.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println("Global Name Service beendet!");
        }
    }

    public static void main(String[] args)  {
        int port = (args.length ==1)? Integer.parseInt(args[0]) : Config.GLOBAL_NAME_SERVICE_PORT;
        GlobalNameService ns = new GlobalNameService(port);
        ns.start();
    }
}
