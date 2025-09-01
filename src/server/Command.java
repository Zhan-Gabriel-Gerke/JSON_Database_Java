package server;

import java.io.IOException;

public interface Command {
    PackageDataServer execute() throws IOException;
}

class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public PackageDataServer execute() throws IOException {
        return command.execute();
    }
}

class GetCommand implements Command{

    JsonDatabase db;
    String key;

    public GetCommand(JsonDatabase db, String  key) {
        this.db = db;
        this.key = key;
    }

    @Override
    public PackageDataServer execute() {
        String value = db.getValue(key);
        if (value == null) {
            return new PackageDataServer("ERROR", null, "No such key");
        }
        return new PackageDataServer("OK", value, null);
    }
}

class SetCommand implements Command{

    JsonDatabase db;
    String value;
    String key;

    public SetCommand(JsonDatabase db, String value, String key) {
        this.db = db;
        this.value = value;
        this.key = key;
    }

    @Override
    public PackageDataServer execute() {

        db.setValue(key, value);
        FileManager.writeToDB(db.getMap(), false);

        return new PackageDataServer("OK", null, null);
    }
}

class DeleteCommand implements Command{
    JsonDatabase db;
    String key;

    public DeleteCommand(JsonDatabase db, String key) {
        this.db = db;
        this.key = key;
    }

    @Override
    public PackageDataServer execute() {
        if (!db.delete(key)) {
            return new PackageDataServer("ERROR", null, "No such key");
        }
        FileManager.writeToDB(db.getMap(), false);
        return new PackageDataServer("OK", null, null);
    }
}

class ExitCommand implements Command{

    @Override
    public PackageDataServer execute() {
            ServerConnection.stopServer();
            try {
                Main.stop();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        return new PackageDataServer("OK", null, null);
    }
}