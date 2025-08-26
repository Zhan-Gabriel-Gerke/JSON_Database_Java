package server;

import java.util.Arrays;

public interface Command {
    String execute();
}

class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public String execute() {
        return command.execute();
    }
}

class GetCommand implements Command{

    private final String[] arrayJSON;
    private final int index;

    public GetCommand(String[] arrayJSON, int  index) {
        this.arrayJSON = arrayJSON;
        this.index = index;
    }

    @Override
    public String execute() {
        if (index < 0 || index >= arrayJSON.length || arrayJSON[index] == null || arrayJSON[index].isEmpty()) {
            return "ERROR";
        }
        return arrayJSON[index];
    }
}

class SetCommand implements Command{

    private final String[] arrayJSON;
    private final String[] dataArray;
    private final int index;

    public SetCommand(String[] arrayJSON, String[] dataArray, int index) {
        this.arrayJSON = arrayJSON;
        this.dataArray = dataArray;
        this.index = index;
    }

    @Override
    public String execute() {
        if  (index < 0 || index >= arrayJSON.length) {
            return "ERROR";
        }
        arrayJSON[index] = String.join(" ", Arrays.copyOfRange(dataArray, 2, dataArray.length));
        return "OK";
    }
}

class DeleteCommand implements Command{
    private final String[] arrayJSON;
    private final int index;

    public DeleteCommand(String[] arrayJSON, int index) {
        this.arrayJSON = arrayJSON;
        this.index = index;
    }

    @Override
    public String execute() {
        if (index < 0 || index >= arrayJSON.length) {
            return "ERROR";
        }
        arrayJSON[index] = "";
        return "OK";
    }
}