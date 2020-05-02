package cz.student_evidence.model.student;

import java.util.Optional;
import java.util.stream.Stream;

public enum StudentType {
    TECHNICAL(TechnicalStudent.class, "Technicky obor"),
    HUMANITY(HumanityStudent.class, "Humanitni obor"),
    DISTANCE(DistanceStudent.class, "Kombinovane studium");

    private final Class<? extends Student> implementation;
    private final String name;

    StudentType(Class<? extends Student> implementation, String name) {
        this.implementation = implementation;
        this.name = name;
    }

    public Class<? extends Student> getImplementation() {
        return implementation;
    }

    public String getDescription() {
        return name;
    }

    public static Optional<StudentType> forStudent(Student student) {
        if (student == null) return Optional.empty();
        return Stream.of(StudentType.values())
                .filter(type -> type.getImplementation().isInstance(student))
                .findFirst();
    }
}
