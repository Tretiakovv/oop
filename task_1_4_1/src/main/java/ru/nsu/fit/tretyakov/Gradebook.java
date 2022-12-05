package ru.nsu.fit.tretyakov;

import java.util.*;

/**
 * This class is the student's grade book. In it user can get
 * information about his score, subjects, average grade point and
 * his honor diploma.
 */
public class Gradebook {

    private final Map<Integer, HashMap<String, Subject>> gradeBook;
    /**
     * Allowed credit types of grade book. Used to determine values of credits.
     */
    private final Set<String> typesOfCredit;
    private final String name;
    private final String group;
    private final String numberOfGradeBook;
    private Float averageGradePoint;
    private boolean scholarship, honorDiploma;
    private Integer subjects, thesis;

    /**
     * Constructor of the grade book, which gets
     *
     * @param name              is the required name of student's grade book
     * @param group             is the required student's group
     * @param numberOfGradeBook is the required number of student's grade book
     */
    public Gradebook(String name, String group, String numberOfGradeBook) {
        this.name = name;
        this.group = group;
        this.numberOfGradeBook = numberOfGradeBook;
        this.gradeBook = new HashMap<>();
        this.scholarship = this.honorDiploma = false;
        this.averageGradePoint = 0.f;
        this.subjects = this.thesis = 0;
        String[] creditTypes = new String[]{"Exam", "DifCredit", "UndifCredit"};
        this.typesOfCredit = new HashSet<>(List.of(creditTypes));
    }

    /**
     * This method adds a semester to the grade book.
     *
     * @param semester is the required semester.
     * @return true if a semester was successfully added.
     * Otherwise, return false.
     */
    public boolean addSemester(Integer semester) {
        if (gradeBook.get(semester) != null) {
            return false;
        }
        gradeBook.put(semester, new HashMap<>());
        return true;
    }

    /**
     * This method adds a specific subject WITHOUT a value
     * to the semester in grade book.
     *
     * @param semester   is the required semester that contains current semester.
     * @param subject    is the subject which will be evaluated by current value.
     * @param creditType is the credit type of the current subject
     * @throws IllegalStateException if there is already exist a semester
     *                               or subject with specific value.
     */
    public void addSubject(Integer semester, String subject, String creditType) throws IllegalStateException {
        if (!typesOfCredit.contains(creditType)) {
            throw new IllegalStateException("This credit type doesn't contained in grade book");
        }
        if (gradeBook.get(semester) == null) {
            throw new IllegalStateException("This semester doesn't exist in grade book");
        }
        if (gradeBook.get(semester).get(subject) != null) {
            throw new IllegalStateException("This subject is already exist in grade book");
        }
        gradeBook.get(semester).put(subject, new Subject(creditType));
        this.subjects++;
    }

    /**
     * Getter of the subject's grade.
     *
     * @param semester is the specific semester in the grade book.
     * @param subject  is the required subject in specific semester in the grade book.
     * @return value of the required subject in semester.
     * @throws IllegalStateException if required semester or grade are don't exist in the grade book.
     */
    public Integer getGrade(Integer semester, String subject) throws IllegalStateException {
        handleSubject(semester, subject);
        return gradeBook.get(semester).get(subject).grade;
    }

    /**
     * Setter of the subject's grade.
     *
     * @param semester is the required semester in the grade book.
     * @param subject  is the required subject in the specific semester.
     * @param value    is the required value of the subject in the grade book.
     * @throws IllegalStateException if the passed value isn't validated.
     */
    public void setGrade(Integer semester, String subject, Integer value) throws IllegalStateException {
        handleSubject(semester, subject);
        var curSubject = gradeBook.get(semester).get(subject);

        if (Objects.equals(curSubject.creditType, "UndifCredit")) {
            if (value == 2 || value == 5) {
                gradeBook.get(semester).get(subject).grade = value;
            } else {
                throw new IllegalStateException("Undifferentiated credit grade cannot be evaluated by this value");
            }
        } else if (value >= 2 && value <= 5) {
            gradeBook.get(semester).get(subject).grade = value;
        } else {
            throw new IllegalStateException("Subject cannot be evaluated by this value");
        }
        computeScore();
    }

    /**
     * This method prints student's grade book as table with semesters, subjects,
     * credit types of all subjects and grades of these subjects.
     */
    public void printGradebook() {

        System.out.printf("Student's name: %s\n", this.name);
        System.out.printf("Student's group: %s\n", this.group);
        System.out.printf("Student's grade book number: %s\n\n", this.numberOfGradeBook);

        System.out.println("-------------------------------\n");

        System.out.printf("Student's average grade point: %f\n", this.averageGradePoint);
        if (this.honorDiploma) System.out.println("Student's honor diploma: YES");
        else System.out.println("Student's honor diploma: NO");

        System.out.println("-------------------------------\n");

        for (var semester : gradeBook.keySet()) {
            System.out.printf("Semester #%d\n\n", semester);
            System.out.println("-------------------------------\n");
            for (var subject : gradeBook.get(semester).keySet()) {
                var curSubject = gradeBook.get(semester).get(subject);
                System.out.printf("Subject: %s   |   Credit type: %s   |   Grade: %d\n\n", subject, curSubject.creditType, curSubject.grade);
            }
        }

        System.out.println("-------------------------------\n");
    }

    private void computeDiploma() {

        var countFive = 0;
        this.honorDiploma = true;

        for (var courses : gradeBook.keySet()) {
            for (var subjects : gradeBook.get(courses).keySet()) {
                var grade = gradeBook.get(courses).get(subjects).grade;
                if (grade < 4) honorDiploma = false;
                else if (grade == 5) countFive++;
            }
        }
        if (this.thesis != 5 || (double) countFive / this.subjects < 0.75) {
            this.honorDiploma = false;
        }
    }

    private void computeScore() {

        float sumOfGrades = 0.f;
        this.scholarship = true;

        for (var courses : gradeBook.keySet()) {
            for (var subjects : gradeBook.get(courses).keySet()) {
                var grade = gradeBook.get(courses).get(subjects).grade;
                if (grade < 4) scholarship = false;
                sumOfGrades += grade;
            }
        }
        averageGradePoint = sumOfGrades / subjects;
    }

    private void handleSubject(Integer course, String subject) throws IllegalStateException {
        if (gradeBook.get(course) == null) {
            throw new IllegalStateException("This course doesn't exist in grade book");
        }
        if (gradeBook.get(course).get(subject) == null) {
            throw new IllegalStateException("This subject doesn't exist in grade book");
        }
    }

    /**
     * Getter of the honor diploma. It computes the possibility
     * of getting the honor diploma.
     *
     * @return true if user can get the honor diploma. Otherwise, return false.
     */
    public boolean getHonorDiploma() {
        computeDiploma();
        return honorDiploma;
    }

    /**
     * Getter of the thesis.
     *
     * @return value of the thesis.
     */
    public Integer getThesis() {
        return thesis;
    }

    /**
     * Setter of the thesis.
     *
     * @param thesis is the required value of the thesis.
     */
    public void setThesis(Integer thesis) {
        this.thesis = thesis;
    }

    /**
     * Getter of the scholarship.
     *
     * @return value of the scholarship.
     */
    public boolean getScholarShip() {
        return scholarship;
    }

    /**
     * Getter of the Average Grade Point.
     *
     * @return value of the GPA.
     */
    public Float getGPA() {
        return averageGradePoint;
    }

    /**
     * Subject-class. Contains credit type of the current subject
     * and grade of the current subject. Used in my implementation
     * to prevent many HashMaps in Gradebook-class.
     */
    private static class Subject {
        private final String creditType;
        private Integer grade;

        public Subject(String creditType) {
            this.creditType = creditType;
            this.grade = 0;
        }
    }
}
