package managers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultHistory() {
        assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void getDefault() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    void getFileBacked() {
        assertNotNull(Managers.getFileBackedManager());
    }
}
