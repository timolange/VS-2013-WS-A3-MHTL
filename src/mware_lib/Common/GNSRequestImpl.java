package mware_lib.Common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: timey
 * Date: 30.11.13
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class GNSRequestImpl implements GNSRequest, Serializable {
    public static final String REBIND = "rebind";
    public static final String RESOLVE = "resolve";
    private String command;
    private Object[] parameter;
    private Class<?>[] parameterClasses;

    public GNSRequestImpl(String command, Object[] parameter, Class<?>[] parameterClasses) {
        this.command = command;
        this.parameter = parameter;
        this.parameterClasses = parameterClasses;
    }

    public String getCommand() {
        return command;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public Class<?>[] getParameterClasses(){
        return parameterClasses;
    }

    @Override
    public String toString() {
        return "GNSRequestImpl{" +
                "command='" + command + '\'' +
                ", parameter=" + Arrays.toString(parameter) +
                ", parameterClasses=" + Arrays.toString(parameterClasses) +
                '}';
    }
}
