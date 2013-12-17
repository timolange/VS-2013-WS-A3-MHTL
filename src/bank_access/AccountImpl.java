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
            stub.getResponseFromRemoteObject(req1);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public double getBalance() {
        double res = 0.0;
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "getBalance",null,null);
            res =(Double) stub.getResponseFromRemoteObject(req1);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return res;
    }
}
