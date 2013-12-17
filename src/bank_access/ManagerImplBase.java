package bank_access;

import mware_lib.Client.Stub;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class ManagerImplBase {
    public abstract String createAccount(String owner,String branch);
    public static ManagerImplBase narrowCast(Object gor) {
        return new ManagerImpl((Stub)gor);
    }
}
