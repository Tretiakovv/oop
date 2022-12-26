package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class NotebookTest {

    private NotebookSerializer testSreializer;

    @BeforeEach
    public void init() {
        this.testSreializer = new NotebookSerializer();
    }

    @AfterEach
    public void clear() {
        this.testSreializer.updateFile(new TreeMap<>());
    }

    @Test
    public void addNoteTest() {
        Main.main(new String[]{"add", "First note"});
        assertEquals(1, testSreializer.getDataFromFile().size());
    }

    @Test
    public void removeNoteTest() {
        Main.main(new String[]{"add", "First note", "-b", "Body of the note"});
        assertEquals(1, testSreializer.getDataFromFile().size());
        Main.main(new String[]{"rm", "First note"});
        assertEquals(0, testSreializer.getDataFromFile().size());
    }

    @Test
    public void showNotebookTest() {
        Main.main(new String[]{"add", "First note", "-b", "Body of the note"});
        Main.main(new String[]{"show"});
    }

    @Test
    public void showNotebookByKeywordsTest() {
        Main.main(new String[]{"add", "First note", "-b", "Body of the note"});
        Main.main(new String[]{"show", "First note"});
    }

    @Test
    public void editNoteFromNotebookTest() {
        Main.main(new String[]{"add", "First note", "-b", "Body of the note"});
        Note noteBeforeEditing = testSreializer.getDataFromFile().firstEntry().getValue();
        assertEquals("First note", noteBeforeEditing.getHeader());
        Main.main(new String[]{"edit", "First note", "Testing note", "-b", "Testing body"});
        Note noteAfterEditing = testSreializer.getDataFromFile().firstEntry().getValue();
        assertEquals("Testing note", noteAfterEditing.getHeader());
        assertEquals("Testing body", noteAfterEditing.getBody());
    }

    @Test
    public void customOutputFileTest() {
        Main.main(new String[]{"file", "./test.json"});
        Main.main(new String[]{"add", "New note"});
        assertEquals(1, testSreializer.getDataFromFile().size());
    }

    @Test
    public void showNotebookByStartingAndEndingTime() {
        Main.main(new String[]{"add", "First note"});
        Main.main(new String[]{"show", "-s", "11-12-2015 13:33:43", "-e", "27-12-2022 15:44:23"});
        assertEquals(1, testSreializer.getDataFromFile().size());
    }

    @Test
    public void userFileNotebookTest() {
        Main.main(new String[]{"add", "First note"});
        Main.main(new String[]{"show", "-s", "11-12-2015 13:33:43", "-e", "27-12-2022 15:44:23"});
        assertEquals(1, testSreializer.getDataFromFile().size());
    }

    // ERROR TESTS

    @Test
    public void pullDataFromIncorrectFile() {
        assertEquals(new TreeMap<>(), testSreializer.getDataFromFile());
        testSreializer.updateFile(null);
    }

    @Test
    public void showNotebookByKeywordsExceptionTest() {
        Main.main(new String[]{"add", "First note", "-b", "Body of the note"});
        try {
            Main.main(new String[]{"show", "First"});
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void showNotebookByStartingAndEndingTimeException() {
        Main.main(new String[]{"add", "First note"});
        try {
            Main.main(new String[]{"show", "-s",
                    "11-12-2015 13:33:43", "-e", "27-12-2022 15:44:23"});
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    @Test
    public void removeNonexistentNoteTest() {
        Main.main(new String[]{"add", "First note", "-b", "Body of the note"});
        try {
            Main.main(new String[]{"rm", "First"});
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void editNonexistentNoteFromNotebookTest() {
        Main.main(new String[]{"add", "First note"});
        Note noteBeforeEditing = testSreializer.getDataFromFile().firstEntry().getValue();
        assertEquals("First note", noteBeforeEditing.getHeader());
        try {
            Main.main(new String[]{"edit", "Second note", "Testing note"});
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

}
