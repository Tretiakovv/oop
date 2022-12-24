package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.*;

import java.io.IOException;
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
        MyNotebook.main(new String[]{"add", "First note"});
        assertEquals(1, testSreializer.getDataFromFile().size());
    }

    @Test
    public void removeNoteTest() {
        MyNotebook.main(new String[]{"add", "First note", "-b", "Body of the note"});
        assertEquals(1, testSreializer.getDataFromFile().size());
        MyNotebook.main(new String[]{"rm", "First note"});
        assertEquals(0, testSreializer.getDataFromFile().size());
    }

    @Test
    public void showNotebookTest() {
        MyNotebook.main(new String[]{"add", "First note", "-b", "Body of the note"});
        MyNotebook.main(new String[]{"show"});
    }

    @Test
    public void showNotebookByKeywordsTest() {
        MyNotebook.main(new String[]{"add", "First note", "-b", "Body of the note"});
        MyNotebook.main(new String[]{"show", "First note"});
    }

    @Test
    public void editNoteFromNotebookTest() {
        MyNotebook.main(new String[]{"add", "First note", "-b", "Body of the note"});
        Note noteBeforeEditing = testSreializer.getDataFromFile().firstEntry().getValue();
        assertEquals("First note", noteBeforeEditing.getHeader());
        MyNotebook.main(new String[]{"edit", "First note", "Testing note", "-b", "Testing body"});
        Note noteAfterEditing = testSreializer.getDataFromFile().firstEntry().getValue();
        assertEquals("Testing note", noteAfterEditing.getHeader());
        assertEquals("Testing body", noteAfterEditing.getBody());
    }

    @Test
    public void customOutputFileTest(){
        testSreializer = new NotebookSerializer("./tstNotebook.json");
        MyNotebook.main(new String[]{"add", "First note"});
        assertEquals(1, testSreializer.getDataFromFile().size());
    }

    // ERROR TESTS

    @Test
    public void pullDataFromIncorrectFile(){
        testSreializer = new NotebookSerializer(null);
        assertEquals(new TreeMap<>(),testSreializer.getDataFromFile());
        testSreializer.updateFile(null);
    }

    @Test
    public void showNotebookByKeywordsExceptionTest() {
        MyNotebook.main(new String[]{"add", "First note", "-b", "Body of the note"});
        try {
            MyNotebook.main(new String[]{"show", "First"});
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void removeNonexistentNoteTest() {
        MyNotebook.main(new String[]{"add", "First note", "-b", "Body of the note"});
        try {
            MyNotebook.main(new String[]{"rm", "First"});
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void editNonexistentNoteFromNotebookTest() {
        MyNotebook.main(new String[]{"add", "First note"});
        Note noteBeforeEditing = testSreializer.getDataFromFile().firstEntry().getValue();
        assertEquals("First note", noteBeforeEditing.getHeader());
        try {
            MyNotebook.main(new String[]{"edit", "Second note", "Testing note"});
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

}
