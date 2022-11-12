package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradebookTest {
    private Gradebook gb;

    @BeforeEach
    public void initGradebook() {
        this.gb = new Gradebook();
        gb.addCourse(1);
    }

    @Test
    public void checkGPATest() {
        gb.addSubject(1, "Math", 5);
        gb.addSubject(1, "OOP", 5);
        gb.addSubject(1, "English", 4);
        assertEquals((float) 14 / 3, gb.getGPA());
    }

    @Test
    public void getThesisTest() {
        gb.addCourse(2, "Math");
        gb.setThesis(5);
        assertEquals(5, gb.getThesis());
    }

    @Test
    public void checkScholarship() {
        gb.addSubject(1, "Math", 5);
        gb.addSubject(1, "OOP", 5);
        gb.addSubject(1, "English", 3);
        assertFalse(gb.getScholarShip());
    }

    @Test
    public void checkHonorDiplomaTrue() {
        gb.setThesis(5);
        gb.addSubject(1, "Math", 5);
        gb.addSubject(1, "OOP", 5);
        gb.addSubject(1, "Physics", 5);
        gb.addSubject(1, "English", 4);
        assertTrue(gb.getHonorDiploma());
    }

    @Test
    public void checkHonorDiplomaFalse() {
        gb.setThesis(5);
        gb.addSubject(1, "Math");
        gb.setGrade(1, "Math", 5);
        gb.addSubject(1, "OOP", 5);
        gb.addSubject(1, "Physics", 4);
        gb.addSubject(1, "English", 4);
        assertEquals(5, gb.getGrade(1, "Math"));
        assertFalse(gb.getHonorDiploma());
    }

    @Test
    public void exceptionTest() {
        assertThrows(IllegalStateException.class, () -> {
            gb.getGrade(1, "Math");
        });
        assertThrows(IllegalStateException.class, () -> {
            gb.getGrade(2, "Math");
        });
        assertThrows(IllegalStateException.class, () -> {
            gb.addSubject(2, "Math");
        });
    }

    @Test
    public void addExistingCourse() {
        assertFalse(gb.addCourse(1));
    }

    @Test
    public void addExistingSubject() {
        gb.addSubject(1, "Math");
        assertFalse(gb.addSubject(1, "Math"));
    }

}
