package mware_lib;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 29.11.13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public abstract class NameService {
    /**
     * Registers a remote object / service for name
     * @param servant object, processing remote methods
     * @param name a global unique name of the object / service
     */
    public abstract void rebind(Object servant, String name);
    /**
     * Resolves name to a generic object reference
     * @param name
     * @return a generic object reference
     */
    public abstract Object resolve(String name);
}
