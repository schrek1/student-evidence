package cz.student_evidence.model.student;

import cz.student_evidence.model.ability.ZodiacKnowledge;

import java.time.LocalDate;

public class HumanityStudent extends Student implements ZodiacKnowledge {

    public HumanityStudent(long id, String firstName, String lastName, LocalDate bornDate) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBorn = bornDate;
    }

    @Override
    public String getZodiac() {
        return super.getZodiac();
    }
}
