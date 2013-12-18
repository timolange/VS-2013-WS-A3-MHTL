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
public class AccountImpl extends AccountImplBase implements Serializable {
    Stub stub;

    public AccountImpl(Stub stub) {
        this.stub = stub;
    }

    @Override
    public void transfer(double amount) throws OverdraftException {
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "transfer",new Object[]{amount},new Class[]{double.class});
            Object res = stub.getResponseFromRemoteObject(req1);
            if(res instanceof OverdraftException){throw  (OverdraftException)res;}
            if(res instanceof Exception){ throw new RuntimeException((Throwable)res);}
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public double getBalance() {
        Object res = null;
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "getBalance",null,null);
            res = stub.getResponseFromRemoteObject(req1);
            if(res instanceof Exception){ throw new RuntimeException((Throwable)res);}
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return (Double)res;
    }
}
