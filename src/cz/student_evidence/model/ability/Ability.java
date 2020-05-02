package cz.student_evidence.model.ability;

import cz.student_evidence.model.student.Student;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Ability {
    ZODIAC_KNOWLEDGE(ZodiacKnowledge.class),
    BORN_LAP_YEAR_KNOWLEDGE(BornLapYearKnowledge.class);

    private final Class<? extends Knowledge> implementation;

    Ability(Class<? extends Knowledge> cls) {
        this.implementation = cls;
    }

    public Class<? extends Knowledge> getImplementation() {
        return implementation;
    }

    public static Set<Ability> getForStudent(Student student) {
        if (student == null) return Collections.emptySet();
        return Stream.of(Ability.values())
                .filter(ability -> ability.getImplementation().isInstance(student))
                .collect(Collectors.toSet());
    }
}
