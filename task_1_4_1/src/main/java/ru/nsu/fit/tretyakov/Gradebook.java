package ru.nsu.fit.tretyakov;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is the student's grade book. In it user can get
 * information about his score, subjects, GPA (Average Grade Point) and
 * his honor diploma.
 */
public class Gradebook {

    private final HashMap<Integer, HashMap<String, Integer>> gradeBook;
    private Float GPA;
    private boolean scholarship, honorDiploma;
    private Integer subjects, thesis;

    /**
     * Constructor of the grade book.
     */
    public Gradebook() {
        this.gradeBook = new HashMap<>();
        this.scholarship = this.honorDiploma = false;
        this.GPA = 0.f;
        this.subjects = this.thesis = 0;
    }

    /**
     * This method adds course with a specific subject to the grade book.
     *
     * @param course  is the course to which will be
     *                contained new subject in the grade book.
     * @param subject is the required subject that will be added to the grade book.
     * @return true if the course with subject was successfully added.
     * Otherwise, return false.
     */
    public boolean addCourse(Integer course, String subject) {
        var hasCourse = addCourse(course);
        if (!hasCourse) return false;
        gradeBook.get(course).put(subject, 0);
        return true;
    }

    /**
     * This method adds a course to the grade book.
     *
     * @param course is the required course.
     * @return true if a course was successfully added.
     * Otherwise, return false.
     */
    public boolean addCourse(Integer course) {
        if (gradeBook.get(course) != null) {
            return false;
        }
        gradeBook.put(course, new HashMap<>());
        return true;
    }

    /**
     * This method adds a specific subject with value
     * to the specific course in the grade book.
     *
     * @param course  is the required course that contains current course.
     * @param subject is the subject which will be evaluated by current value.
     * @param value   is the value of the specific subject in the grade book.
     * @return true if the subject with grade was added successfully.
     * Otherwise, return false.
     * @throws IllegalStateException if there is already exist a course
     *                               or subject with specific value.
     */
    public boolean addSubject(Integer course, String subject, Integer value) throws IllegalStateException {
        boolean hasCourse = addSubject(course, subject);
        if (!hasCourse) return false;
        setGrade(course, subject, value);
        return true;
    }

    /**
     * This method adds a specific subject WITHOUT a value
     * to the course in grade book.
     *
     * @param course  is the required course that contains current course.
     * @param subject is the subject which will be evaluated by current value.
     * @return true if the subject with grade was added successfully.
     * Otherwise, return false.
     * @throws IllegalStateException
     */
    public boolean addSubject(Integer course, String subject) throws IllegalStateException {
        if (gradeBook.get(course) == null) {
            throw new IllegalStateException("This course doesn't exist in grade book");
        }
        if (gradeBook.get(course).get(subject) != null) {
            return false;
        }
        gradeBook.get(course).put(subject, 0);
        this.subjects++;
        return true;
    }

    /**
     * Getter of the subject's grade.
     *
     * @param course  is the specific course in the grade book.
     * @param subject is the required subject in specific course in the grade book.
     * @return value of the required subject in course.
     * @throws IllegalStateException if required course or grade are don't exist in the grade book.
     */
    public Integer getGrade(Integer course, String subject) throws IllegalStateException {
        checkGrade(course, subject);
        return gradeBook.get(course).get(subject);
    }

    /**
     * Setter of the subject's grade.
     *
     * @param course  is the required course in the grade book.
     * @param subject is the required subject in the specific course.
     * @param value   is the required value of the subject in the grade book.
     */
    public void setGrade(Integer course, String subject, Integer value) {
        checkGrade(course, subject);
        gradeBook.get(course).put(subject, value);
        computeScore();
    }

    private void computeDiploma() {

        var countFive = 0;
        this.honorDiploma = true;

        for (var courses : gradeBook.keySet()) {
            for (var subjects : gradeBook.get(courses).keySet()) {
                var grade = gradeBook.get(courses).get(subjects);
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
                var grade = gradeBook.get(courses).get(subjects);
                if (grade < 4) scholarship = false;
                sumOfGrades += grade;
            }
        }
        GPA = sumOfGrades / subjects;
    }

    private void checkGrade(Integer course, String subject) throws IllegalStateException {
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
        return GPA;
    }
}
