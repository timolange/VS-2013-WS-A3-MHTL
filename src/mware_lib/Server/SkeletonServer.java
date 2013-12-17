package mware_lib.Server;

import mware_lib.Common.Config;
import mware_lib.NameServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 03.12.13
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
public class SkeletonServer extends Thread{
    private ExecutorService executor;
    private ServerSocket welcomeSocket;
    private NameServiceImpl nameService;
    private int port;
    private int threadCount = 0;

    public SkeletonServer(ServerSocket socket, NameServiceImpl nameService){
        this.welcomeSocket = socket;
        this.port = socket.getLocalPort();
        this.nameService = nameService;
        this.executor = Executors.newFixedThreadPool(Config.MAX_CLIENTS_PER_OBJECT);
    }

    public synchronized void decreaseThreadCount() {
        threadCount--;
    }

    public synchronized void increaseThreadCount() {
        threadCount++;
    }

    public Object getServant(String ObjectIdentifier){
        return this.nameService.getRegistredObject(ObjectIdentifier);
    }

    @Override
    public void run(){
        Socket connectionSocket;
        try {
            while (!isInterrupted()) {
                if (threadCount < Config.MAX_CLIENTS_PER_OBJECT) {
                    if (welcomeSocket.isClosed()) {
                        welcomeSocket = new ServerSocket(port);
                    }
                    connectionSocket = welcomeSocket.accept();
                    increaseThreadCount();
                    executor.execute(new SkeletonThread(connectionSocket, this));
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
            System.out.println("Skeleton Server beendet!");
        }
    }
}
