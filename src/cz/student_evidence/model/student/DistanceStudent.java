package cz.student_evidence.model.student;

import cz.student_evidence.model.ability.BornLapYearKnowledge;
import cz.student_evidence.model.ability.ZodiacKnowledge;

import java.time.LocalDate;

public class DistanceStudent extends Student implements BornLapYearKnowledge, ZodiacKnowledge {

    public DistanceStudent(long id, String firstName, String lastName, LocalDate bornDate) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBorn = bornDate;
    }

    @Override
    public boolean isBornInLapYear() {
        return super.isBornInLapYear();
    }

    @Override
    public String getZodiac() {
        return super.getZodiac();
    }
}
