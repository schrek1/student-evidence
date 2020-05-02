package cz.student_evidence.services;

import cz.student_evidence.data.Storage;
import cz.student_evidence.data.db.Database;
import cz.student_evidence.data.memory.Memory;
import cz.student_evidence.exception.NotDeleted;
import cz.student_evidence.exception.NotFound;
import cz.student_evidence.exception.NotSaved;
import cz.student_evidence.model.ExamResult;
import cz.student_evidence.model.ability.Ability;
import cz.student_evidence.model.student.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    Storage<Student, Long> memory;
    Storage<Student, Long> database;

    public StudentService() {
        memory = new Memory();
        database = new Database();
    }

    public StudentService(Storage<Student, Long> memory, Storage<Student, Long> database) {
        this.memory = memory;
        this.database = database;
    }

    public long saveStudent(String firstName, String lastName, LocalDate bornDate, StudentType selectedType) throws NotSaved {
        System.out.println("> " + firstName + " " + lastName + " " + bornDate.toString() + " " + selectedType.name());
        long id = getStudentId();

        Student student;
        switch (selectedType) {
            case TECHNICAL:
                student = new TechnicalStudent(id, firstName, lastName, bornDate);
                break;
            case HUMANITY:
                student = new HumanityStudent(id, firstName, lastName, bornDate);
                break;
            case DISTANCE:
                student = new DistanceStudent(id, firstName, lastName, bornDate);
                break;
            default:
                throw new IllegalStateException();
        }

        boolean isSaved = memory.save(id, student);

        if (!isSaved) throw new NotSaved("zaznam do pameti");

        return id;
    }

    public void addEvaluation(long id, int evaluation) throws NotFound {
        Student student = memory.get(id).orElseThrow(() -> new NotFound("studenta id=" + id));
        student.addExamResult(new ExamResult(evaluation));
    }

    public void deleteStudent(long id) throws NotDeleted {
        boolean isDeleted = memory.delete(id);
        if (!isDeleted) throw new NotDeleted("studenta id=" + id);
    }

    public Student getStudent(long id) throws NotFound {
        return memory.get(id).orElseThrow(() -> new NotFound("studenta id=" + id));
    }

    public List<Student> getStudents(long id) {
        return memory.getAllWithId(id);
    }

    public Set<Ability> getStudentAbility(long id) {
        Student student = memory.get(id).orElse(null);
        if (student == null) return Collections.emptySet();
        return Ability.getForStudent(student);
    }

    public Map<StudentType, List<Student>> getStudentsByType() {
        List<Student> students = memory.getAll();
        return students.stream().collect(Collectors.groupingBy(student -> StudentType.forStudent(student).orElseThrow()));
    }

    public Map<StudentType, Double> getAvgByType() {
        return getStudentsByType().entrySet().stream().map(record -> {
            List<Student> students = record.getValue();
            double average = 0;
            if (students != null) {
                average = students.stream()
                        .mapToDouble(Student::getAvgEvaluation)
                        .filter(it -> it > 0)
                        .average().orElse(0);
            }
            return new AbstractMap.SimpleEntry<>(record.getKey(), average);
        }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public Map<StudentType, Integer> getStudentCountByType() {
        return getStudentsByType().entrySet().stream().map(record -> {
            List<Student> students = record.getValue();
            int count = students != null ? students.size() : 0;
            return new AbstractMap.SimpleEntry<>(record.getKey(), count);
        }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public void connectToDb() {
        database.connect();
    }

    public int loadAllStudentsFromDb() {
        List<Student> students = database.getAll();
        students.forEach(student -> memory.save(student.getId(), student));
        return students.size();
    }

    public int saveStudentsToDb() {
        List<Student> students = memory.getAll();
        students.forEach(student -> database.save(student.getId(), student));
        return students.size();
    }

    public void deleteStudentFromDb(long id) {
        database.delete(id);
    }

    public void loadStudentFromDb(String firstName, String lastName) {
        List<Student> students = database.getAll();
        students.stream()
                .filter(student -> student.getFirstName().equals(firstName) && student.getLastName().equals(lastName))
                .forEach(student -> memory.save(student.getId(), student));
    }

    private Long getStudentId() {
        Long dbLastIndex = database.getLastIndex();
        Long memoryLastIndex = memory.getLastIndex();

        long index = 0;

        boolean isDbIndexSet = dbLastIndex != null && dbLastIndex > 0;
        boolean isMemoryIndexSet = memoryLastIndex != null && memoryLastIndex > 0;

        if (isDbIndexSet && isMemoryIndexSet) {
            index = dbLastIndex > memoryLastIndex ? dbLastIndex : memoryLastIndex; // pokracuj s vyssim indexem
        } else if (isDbIndexSet) {
            index = dbLastIndex;
        } else if (isMemoryIndexSet) {
            index = memoryLastIndex;
        }

        return index + 1;
    }
}
