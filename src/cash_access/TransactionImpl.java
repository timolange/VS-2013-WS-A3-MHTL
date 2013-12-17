package cash_access;

import mware_lib.Client.Stub;
import mware_lib.Common.MethodInvokeRequest;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 07.12.13
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class TransactionImpl extends TransactionImplBase implements Serializable {
    Stub stub;

    public TransactionImpl(Stub stub) {
        this.stub = stub;
    }

    @Override
    public void deposit(String accountId, double amount) throws InvalidParamException {
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "deposit",new Object[]{accountId,amount}, new Class[]{accountId.getClass(),double.class});
            stub.getResponseFromRemoteObject(req1);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void withdraw(String accountId, double amount) throws InvalidParamException, OverdraftException {
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "withdraw",new Object[]{accountId,amount}, new Class[]{accountId.getClass(),double.class});
            stub.getResponseFromRemoteObject(req1);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public double getBalance(String accountId) throws InvalidParamException {
        Double res = null;
        try {
            MethodInvokeRequest req1 = new MethodInvokeRequest(stub.name, "getBalance",new Object[]{accountId}, new Class[]{accountId.getClass()});
            res =(Double) stub.getResponseFromRemoteObject(req1);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return res;
    }
}
