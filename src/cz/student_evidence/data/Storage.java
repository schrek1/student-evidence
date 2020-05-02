package cz.student_evidence.data;

import cz.student_evidence.exception.NotConnected;
import cz.student_evidence.model.student.Student;

import java.util.List;
import java.util.Optional;

public interface Storage<ENTITY, ID> {

    void connect() throws NotConnected;

    boolean save(ID key, ENTITY ENTITY);

    Optional<Student> get(ID key);

    boolean delete(ID key);

    List<ENTITY> getAll();

    List<ENTITY> getAllWithId(ID key);

    ID getLastIndex();
}
