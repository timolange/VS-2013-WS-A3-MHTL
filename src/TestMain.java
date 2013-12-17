import mware_lib.Common.Config;
import mware_lib.NameService;
import mware_lib.ObjectBroker;
import name_service.GlobalNameService;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 06.12.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class TestMain {
    public static void main(String[] args) throws UnknownHostException {
//        long starttime = System.currentTimeMillis();
//        //Globalen Name Service starten
//        GlobalNameService gns = new GlobalNameService(Config.GLOBAL_NAME_SERVICE_PORT);
//        gns.start();
//
//        //Object erzeugen und beim GNS anmelden
//        ObjectBroker ob = ObjectBroker.init(InetAddress.getLocalHost().getHostAddress(), Config.GLOBAL_NAME_SERVICE_PORT);
//        NameService ns = ob.getNameService();
    }
}
