package bank_access;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public class OverdraftException extends Exception {
    public OverdraftException(String msg) {super(msg);}
}
