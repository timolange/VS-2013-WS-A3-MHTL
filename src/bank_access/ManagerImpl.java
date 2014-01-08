package bank_access;

import mware_lib.Client.Stub;
import mware_lib.Common.MethodInvokeRequest;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 07.12.13
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class ManagerImpl extends ManagerImplBase implements Serializable {
    Stub stub;

    public ManagerImpl(Stub stub) {
        this.stub = stub;
    }

    @Override
    public String createAccount(String owner, String branch) {
        Object res = null;
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "createAccount",new Object[]{owner,branch}, new Class[]{String.class,String.class});
            res =(String) stub.getResponseFromRemoteObject(req1);
            if(res instanceof Exception){ throw new RuntimeException((Throwable)res);}
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return (String)res;
    }
}
