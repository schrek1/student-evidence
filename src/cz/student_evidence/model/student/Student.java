package cz.student_evidence.model.student;

import cz.student_evidence.model.ExamResult;
import cz.student_evidence.model.ability.BornLapYearKnowledge;
import cz.student_evidence.model.ability.ZodiacKnowledge;
import cz.student_evidence.services.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Student {
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBorn;
    protected List<ExamResult> examResults = new ArrayList<>();
    private long id;

    public Student(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBorn() {
        return dateOfBorn;
    }

    public double getAvgEvaluation() {
        return examResults.stream()
                .mapToDouble(ExamResult::getResult)
                .average().orElse(0);
    }

    public List<ExamResult> getExamResults() {
        return examResults;
    }

    public void setExamResults(List<ExamResult> examResults) {
        this.examResults = examResults;
    }

    public void addExamResult(ExamResult result) {
        if (result != null) {
            examResults.add(result);
        }
    }

    protected boolean isBornInLapYear() {
        return BornLapYearKnowledge.isLapYear(dateOfBorn.getYear());
    }

    protected String getZodiac() {
        return ZodiacKnowledge.computeZodiac(dateOfBorn);
    }

}
