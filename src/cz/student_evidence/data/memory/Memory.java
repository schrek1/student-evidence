package cz.student_evidence.data.memory;


import cz.student_evidence.data.Storage;
import cz.student_evidence.exception.NotConnected;
import cz.student_evidence.model.student.DistanceStudent;
import cz.student_evidence.model.student.HumanityStudent;
import cz.student_evidence.model.student.Student;
import cz.student_evidence.model.student.TechnicalStudent;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Memory implements Storage<Student, Long> {

    private final DataStructure<Student, Long> dataStructure = new DataStructure<>();

    public Memory() {
        save(1L, new HumanityStudent(1, "Jan", "Novak", LocalDate.of(1990, 1, 2)));
        save(2L, new TechnicalStudent(2, "Karel", "Cermak", LocalDate.of(1991, 2, 25)));
        save(3L, new DistanceStudent(3, "Radek", "Vesely", LocalDate.of(2000, 7, 30)));
        save(4L, new DistanceStudent(4, "Karel", "Mares", LocalDate.of(2000, 7, 30)));
    }

    @Override
    public void connect() throws NotConnected {
    }

    @Override
    public boolean save(Long id, Student student) {
        return dataStructure.addItem(id, student);
    }

    @Override
    public Optional<Student> get(Long id) {
        return dataStructure.getFirst(id);
    }

    @Override
    public boolean delete(Long id) {
        return dataStructure.deleteAllWithId(id);
    }

    @Override
    public List<Student> getAll() {
        return dataStructure.getAll();
    }

    @Override
    public List<Student> getAllWithId(Long id) {
        return dataStructure.getAllWithId(id);
    }

    @Override
    public Long getLastIndex() {
        return dataStructure.getAll().stream()
                .mapToLong(Student::getId)
                .max().orElse(0);
    }
}
