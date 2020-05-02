package cz.student_evidence.model.student;

import cz.student_evidence.model.ability.BornLapYearKnowledge;

import java.time.LocalDate;

public class TechnicalStudent extends Student implements BornLapYearKnowledge {

    public TechnicalStudent(long id, String firstName, String lastName, LocalDate bornDate) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBorn = bornDate;
    }

    @Override
    public boolean isBornInLapYear() {
        return super.isBornInLapYear();
    }
}
