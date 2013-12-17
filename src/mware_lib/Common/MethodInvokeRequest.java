package mware_lib.Common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 04.12.13
 * Time: 00:18
 * To change this template use File | Settings | File Templates.
 */
public class MethodInvokeRequest implements Serializable {
    private String objectIdentifier;
    private String command;
    private Object[] parameter;
    private Class<?>[] parameterClasses;

    public MethodInvokeRequest(String objectIdentifier, String command, Object[] parameter, Class<?>[] parameterClasses) {
        this.objectIdentifier = objectIdentifier;
        this.command = command;
        this.parameter = parameter;
        this.parameterClasses = parameterClasses;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public String getCommand() {
        return command;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public Class<?>[] getParameterClasses() {
        return parameterClasses;
    }

    @Override
    public String toString() {
        return "MethodInvokeRequest{" +
                "objectIdentifier='" + objectIdentifier + '\'' +
                ", command='" + command + '\'' +
                ", parameter=" + Arrays.toString(parameter) +
                ", parameterClasses=" + Arrays.toString(parameterClasses) +
                '}';
    }
}
