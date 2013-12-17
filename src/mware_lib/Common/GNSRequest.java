package mware_lib.Common;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 06.12.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public interface GNSRequest {

    public String getCommand();

    public Object[] getParameter();

    public Class<?>[] getParameterClasses();

    public String toString();
}
