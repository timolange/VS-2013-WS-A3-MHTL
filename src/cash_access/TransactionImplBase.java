package cash_access;

import mware_lib.Client.Stub;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public abstract class TransactionImplBase {
    public abstract void deposit(String accountId,double amount)
            throws InvalidParamException;
    public abstract void withdraw(String accountId,double amount)
            throws InvalidParamException,OverdraftException;
    public abstract double getBalance(String accountId)
            throws InvalidParamException;
    public static TransactionImplBase narrowCast(Object o) {
        return new TransactionImpl((Stub)o);
    }
}
