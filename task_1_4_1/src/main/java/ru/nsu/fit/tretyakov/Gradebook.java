package ru.nsu.fit.tretyakov;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class is the student's grade book. In it user can get
 * information about his score, subjects, average grade point and
 * his honor diploma. User can also get list of all subjects with
 * specific credit type.
 */
public class Gradebook {

    private final Map<Integer, Semester> gradeBook;
    private final String name;
    private final String group;
    private final String numberOfGradeBook;
    private boolean honorDiploma;
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
        this.honorDiploma = false;
        this.subjects = this.thesis = 0;
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
        gradeBook.put(semester, new Semester());
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
    public void addSubject(Integer semester, String subject, CreditType creditType) throws IllegalStateException {
        if (gradeBook.get(semester) == null) {
            throw new IllegalStateException("This semester doesn't exist in grade book");
        }
        if (gradeBook.get(semester).semesterSubjects.get(subject) != null) {
            throw new IllegalStateException("This subject is already exist in grade book");
        }
        gradeBook.get(semester).semesterSubjects.put(subject, new Subject(creditType));
        this.subjects++;
        gradeBook.get(semester).amountOfSubjects++;
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
        validateSubject(semester, subject);
        return gradeBook.get(semester).semesterSubjects.get(subject).grade;
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
        validateSubject(semester, subject);
        var curSubject = gradeBook.get(semester).semesterSubjects.get(subject);

        if (curSubject.creditType == CreditType.UNDIF_CREDIT) {
            if (value == 2 || value == 5) {
                gradeBook.get(semester).semesterSubjects.get(subject).grade = value;
            } else {
                throw new IllegalStateException("Undifferentiated credit grade cannot be evaluated by this value");
            }
        } else if (value >= 2 && value <= 5) {
            gradeBook.get(semester).semesterSubjects.get(subject).grade = value;
        } else {
            throw new IllegalStateException("Subject cannot be evaluated by this value");
        }
        gradeBook.get(semester).computeScore();
    }

    /**
     * Getter of all subjects in specific semester with specific credit type
     *
     * @param semester is the required semester where user needs to find subjects
     * @param type     is the required credit type of subject
     * @return ArrayList of all subjects in current semester with passed credit type
     */
    public List<Subject> getSubjectsByType(Integer semester, CreditType type) {
        List<Subject> subjectList = new ArrayList<>();
        for (var subject : gradeBook.get(semester).semesterSubjects.values()) {
            if (subject.creditType == type) subjectList.add(subject);
        }
        return subjectList;
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

        System.out.printf("Student's honor diploma: %s\n", this.honorDiploma ? "YES" : "NO");

        System.out.println("-------------------------------\n");

        for (var semester : gradeBook.keySet()) {
            System.out.printf("Semester #%d\n\n", semester);
            System.out.printf("Student's average grade point: %f\n",
                    gradeBook.get(semester).averageGradePoint);
            System.out.println("-------------------------------\n");
            for (var subject : gradeBook.get(semester).semesterSubjects.keySet()) {
                var curSubject = gradeBook.get(semester).semesterSubjects.get(subject);
                System.out.printf("Subject: %s   |   Credit type: %s   |   Grade: %d\n\n", subject,
                        curSubject.creditType, curSubject.grade);
            }
        }

        System.out.println("-------------------------------\n");
    }

    private void computeDiploma() {

        var countFive = 0;
        this.honorDiploma = true;

        for (var semester : gradeBook.keySet()) {
            for (var subject : gradeBook.get(semester).semesterSubjects.keySet()) {
                var grade = gradeBook.get(semester).semesterSubjects.get(subject).grade;
                if (grade < 4) honorDiploma = false;
                else if (grade == 5) countFive++;
            }
        }
        if (this.thesis != 5 || (double) countFive / this.subjects < 0.75) {
            this.honorDiploma = false;
        }
    }

    private void validateSubject(Integer semester, String subject) throws IllegalStateException {
        if (gradeBook.get(semester) == null) {
            throw new IllegalStateException("This semester doesn't exist in grade book");
        }
        if (gradeBook.get(semester).semesterSubjects.get(subject) == null) {
            throw new IllegalStateException("This subject doesn't exist in grade book");
        }
    }

    /**
     * Getter of specific semester by its number
     *
     * @param semesterNumber is the number of semester
     * @return current semester
     */
    public Semester getSemester(Integer semesterNumber) {
        return gradeBook.get(semesterNumber);
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
     * Getter of the thesis' grade.
     *
     * @return value of the thesis.
     */
    public Integer getThesisGrade() {
        return thesis;
    }

    /**
     * Setter of the thesis' grade.
     *
     * @param thesis is the required value of the thesis.
     */
    public void setThesisGrade(Integer thesis) {
        this.thesis = thesis;
    }

    /**
     * Enum-class for credit types of subjects
     */
    enum CreditType {
        DIF_CREDIT, UNDIF_CREDIT, EXAM
    }

    /**
     * Subject-class. Contains credit type of the current subject
     * and grade of the current subject. Used in my implementation
     * to prevent many HashMaps in Gradebook-class.
     */
    static class Subject {
        private final CreditType creditType;
        private Integer grade;

        /**
         * Main constructor of the subject.
         *
         * @param creditType is the required credit
         *                   type of the subject.
         */
        public Subject(CreditType creditType) {
            this.creditType = creditType;
            this.grade = 0;
        }
    }

    /**
     * Semester-class. Contains map of all subjects which are contained
     * in this semester and scholarship and Average Grade Point
     * for this semester.
     */
    static class Semester {
        private final Map<String, Subject> semesterSubjects;
        private boolean scholarship;
        private Float averageGradePoint;
        private Integer amountOfSubjects;

        /**
         * Main constructor of Semester-class
         */
        public Semester() {
            this.semesterSubjects = new HashMap<>();
            this.scholarship = false;
            this.averageGradePoint = 0.f;
            this.amountOfSubjects = 0;
        }

        /**
         * Getter of Average Grade Point of current semester.
         *
         * @return average grade point of current semester
         */
        public Float getAverageGradePoint() {
            return this.averageGradePoint;
        }

        /**
         * Getter of scholarship of current semester.
         *
         * @return scholarship of current semester.
         */
        public boolean getScolarship() {
            return this.scholarship;
        }

        /**
         * This method computes average grade point of current semester.
         */
        private void computeScore() {

            float sumOfGrades = 0.f;
            this.scholarship = true;

            for (var subject : semesterSubjects.keySet()) {
                var grade = semesterSubjects.get(subject).grade;
                if (grade < 4) this.scholarship = false;
                sumOfGrades += grade;
            }
            this.averageGradePoint = sumOfGrades / amountOfSubjects;
        }
    }
}
