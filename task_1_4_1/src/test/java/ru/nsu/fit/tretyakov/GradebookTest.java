package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradebookTest {
    private Gradebook gb;

    @BeforeEach
    public void initGradebook() {
        this.gb = new Gradebook("Tretyakov Artem","21214","210630");
        gb.addSemester(1);
    }

    @Test
    public void checkGPATest() {

        gb.addSubject(1, "Math", "Exam");
        gb.addSubject(1, "OOP", "Exam");
        gb.addSubject(1, "English", "Exam");

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "English", 4);

        assertEquals((float) 14 / 3, gb.getGPA());
    }

    @Test
    public void getThesisTest() {
        gb.addSemester(2);
        gb.addSubject(2, "Math", "Exam");
        gb.setThesis(5);
        assertEquals(5, gb.getThesis());
    }

    @Test
    public void checkScholarship() {

        gb.addSubject(1, "Math", "Exam");
        gb.addSubject(1, "OOP", "Exam");
        gb.addSubject(1, "English", "Exam");

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "English", 3);

        assertFalse(gb.getScholarShip());
    }

    @Test
    public void checkHonorDiplomaTrue() {

        gb.setThesis(5);

        gb.addSubject(1, "Math", "Exam");
        gb.addSubject(1, "OOP", "Exam");
        gb.addSubject(1, "Physics", "Exam");
        gb.addSubject(1, "English", "Exam");

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "English", 4);
        gb.setGrade(1, "Physics", 5);

        assertTrue(gb.getHonorDiploma());
    }

    @Test
    public void checkHonorDiplomaFalse() {

        gb.setThesis(5);

        gb.addSubject(1, "Math", "Exam");
        gb.addSubject(1, "OOP", "Exam");
        gb.addSubject(1, "Physics", "Exam");
        gb.addSubject(1, "English", "Exam");

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "Physics", 4);
        gb.setGrade(1, "English", 4);

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
            gb.addSubject(2, "Math", "Exam");
        });
    }

    @Test
    public void addExistingCourse() {
        assertFalse(gb.addSemester(1));
    }

    @Test
    public void addExistingSubject() {
        gb.addSubject(1, "Math", "Exam");
        assertThrows(IllegalStateException.class, () -> {
            gb.addSubject(1, "Math", "Exam");
        });
    }

    @Test
    public void creditsExceptions(){
        assertThrows(IllegalStateException.class,()->{
            gb.addSubject(1,"Math","AAA");
        });
        assertThrows(IllegalStateException.class,()->{
            gb.addSubject(1,"Math","UndifCredit");
            gb.setGrade(1,"Math",3);
        });
        assertThrows(IllegalStateException.class,()->{
            gb.addSubject(1,"Math","Exam");
            gb.setGrade(1,"Math",6);
        });
    }

    @Test
    public void printGradebook(){
        gb.addSubject(1,"Math","Exam");
        gb.addSubject(1,"OOP","Exam");
        gb.addSubject(1,"Theory of probability","Exam");
        gb.addSubject(1,"Imperative programming","Exam");
        gb.addSubject(1,"History","DifCredit");

        gb.setGrade(1,"Math",5);
        gb.setGrade(1,"Theory of probability",4);
        gb.setGrade(1,"Imperative programming",5);
        gb.setGrade(1,"OOP",5);
        gb.setGrade(1,"History",4);

        gb.addSemester(2);

        gb.addSubject(2,"Declarative programming","DifCredit");
        gb.addSubject(2,"English","DifCredit");
        gb.addSubject(2,"Team project","DifCredit");
        gb.addSubject(2,"Physics","DifCredit");
        gb.addSubject(2,"PE","UndifCredit");

        gb.setGrade(2,"Declarative programming",5);
        gb.setGrade(2,"English",4);
        gb.setGrade(2,"Team project",5);
        gb.setGrade(2,"Physics",4);
        gb.setGrade(2,"PE",5);

        gb.printGradebook();
    }
}
