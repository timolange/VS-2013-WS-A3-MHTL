package bank_access;

import mware_lib.Client.Stub;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class AccountImplBase {
    public abstract void transfer(double amount) throws OverdraftException;
    public abstract double getBalance();
    public static AccountImplBase narrowCast(Object o) {
        return new AccountImpl((Stub)o);
    }
}
