package managers;

import java.io.File;
import java.io.IOException;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBackedManager() {
        try {
            return new FileBackedTaskManager(File.createTempFile("file", ".csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

