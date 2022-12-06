package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GradebookTest {
    private Gradebook gb;

    @BeforeEach
    public void initGradebook() {
        this.gb = new Gradebook("Tretyakov Artem", "21214", "210630");
        gb.addSemester(1);
    }

    @Test
    public void checkGPATest() {

        gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "OOP", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "English", Gradebook.CreditType.EXAM);

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "English", 4);

        assertEquals((float) 14 / 3, gb.getSemester(1).getAverageGradePoint());
    }

    @Test
    public void getThesisTest() {
        gb.addSemester(2);
        gb.addSubject(2, "Math", Gradebook.CreditType.EXAM);
        gb.setThesisGrade(5);
        assertEquals(5, gb.getThesisGrade());
    }

    @Test
    public void checkScholarship() {

        gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "OOP", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "English", Gradebook.CreditType.EXAM);

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "English", 3);

        assertFalse(gb.getSemester(1).getScolarship());
    }

    @Test
    public void checkHonorDiplomaTrue() {

        gb.setThesisGrade(5);

        gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "OOP", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "Physics", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "English", Gradebook.CreditType.EXAM);

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "English", 4);
        gb.setGrade(1, "Physics", 5);

        assertTrue(gb.getHonorDiploma());
    }

    @Test
    public void checkHonorDiplomaFalse() {

        gb.setThesisGrade(5);

        gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "OOP", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "Physics", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "English", Gradebook.CreditType.EXAM);

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
            gb.addSubject(2, "Math", Gradebook.CreditType.EXAM);
        });
    }

    @Test
    public void addExistingCourse() {
        assertFalse(gb.addSemester(1));
    }

    @Test
    public void addExistingSemester() {
        gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        assertThrows(IllegalStateException.class, () -> {
            gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        });
    }

    @Test
    public void creditsExceptions() {
        assertThrows(IllegalStateException.class, () -> {
            gb.addSubject(1, "Math", Gradebook.CreditType.UNDIF_CREDIT);
            gb.setGrade(1, "Math", 3);
        });
        assertThrows(IllegalStateException.class, () -> {
            gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
            gb.setGrade(1, "Math", 6);
        });
    }

    @Test
    public void printGradebook() {
        gb.addSubject(1, "Math", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "OOP", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "Theory of probability", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "Imperative programming", Gradebook.CreditType.EXAM);
        gb.addSubject(1, "History", Gradebook.CreditType.DIF_CREDIT);

        gb.setGrade(1, "Math", 5);
        gb.setGrade(1, "Theory of probability", 4);
        gb.setGrade(1, "Imperative programming", 5);
        gb.setGrade(1, "OOP", 5);
        gb.setGrade(1, "History", 4);

        gb.addSemester(2);

        gb.addSubject(2, "Declarative programming", Gradebook.CreditType.DIF_CREDIT);
        gb.addSubject(2, "English", Gradebook.CreditType.DIF_CREDIT);
        gb.addSubject(2, "Team project", Gradebook.CreditType.DIF_CREDIT);
        gb.addSubject(2, "Physics", Gradebook.CreditType.DIF_CREDIT);
        gb.addSubject(2, "PE", Gradebook.CreditType.UNDIF_CREDIT);

        gb.setGrade(2, "Declarative programming", 5);
        gb.setGrade(2, "English", 4);
        gb.setGrade(2, "Team project", 5);
        gb.setGrade(2, "Physics", 4);
        gb.setGrade(2, "PE", 5);

        gb.printGradebook();

        List<Gradebook.Subject> difCreditSubjects =
                gb.getSubjectsByType(1, Gradebook.CreditType.DIF_CREDIT);
        List<Gradebook.Subject> examSubjects =
                gb.getSubjectsByType(1, Gradebook.CreditType.EXAM);
        assertEquals(1,difCreditSubjects.size());
        assertEquals(4,examSubjects.size());
    }
}
