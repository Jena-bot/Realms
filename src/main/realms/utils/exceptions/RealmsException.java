package main.realms.utils.exceptions;

public class RealmsException extends Exception{
    public String task;

    public RealmsException(String s) {
        this.task = s;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Plugin Realms had an error while executing task " + task;
    }
}
