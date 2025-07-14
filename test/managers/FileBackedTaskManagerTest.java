package managers;

import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{
    @BeforeEach
    void createManager() {
        try {
            File file = File.createTempFile("file",".csv");
            tm = new FileBackedTaskManager(file);
        } catch (IOException exp) {
            System.out.println(exp.getMessage());
        }
    }


}