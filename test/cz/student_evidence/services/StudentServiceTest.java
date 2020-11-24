package cz.student_evidence.services;

import cz.student_evidence.data.Storage;
import cz.student_evidence.data.memory.Memory;
import cz.student_evidence.exception.NotConnected;
import cz.student_evidence.exception.NotDeleted;
import cz.student_evidence.exception.NotFound;
import cz.student_evidence.exception.NotSaved;
import cz.student_evidence.model.ExamResult;
import cz.student_evidence.model.student.HumanityStudent;
import cz.student_evidence.model.student.Student;
import cz.student_evidence.model.student.StudentType;
import cz.student_evidence.model.student.TechnicalStudent;
import cz.student_evidence.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    @Test
    void init_noParams_shouldHaveSetProperties() {
        StudentService instance = new StudentService();

        Assertions.assertNotNull(instance.database);
        Assertions.assertNotNull(instance.memory);
    }


    @Test
    void saveStudent() throws NotSaved, NotFound {
        String firstname = "Jan";
        String lastname = "Novak";
        LocalDate bornDate = LocalDate.of(2000, 1, 30);

        long studentId = studentService.saveStudent(firstname, lastname, bornDate, StudentType.TECHNICAL);
        Student student = studentService.getStudent(studentId);

        assertNotNull(student);

        assertTrue(student instanceof TechnicalStudent);

        assertEquals(studentId, student.getId());
        assertEquals(firstname, student.getFirstName());
        assertEquals(lastname, student.getLastName());
        assertEquals(bornDate, student.getDateOfBorn());

        List<ExamResult> examResults = student.getExamResults();
        assertNotNull(examResults);
        assertTrue(examResults.isEmpty());
        assertEquals(0.0, student.getAvgEvaluation());
    }

    @Test
    void deleteStudent() throws NotSaved, NotFound, NotDeleted {
        String firstname = "Jan";
        String lastname = "Novak";
        LocalDate bornDate = LocalDate.of(2000, 1, 30);

        long studentId = studentService.saveStudent(firstname, lastname, bornDate, StudentType.TECHNICAL);
        Student student = studentService.getStudent(studentId);

        assertNotNull(student);

        studentService.deleteStudent(studentId);

        assertThrows(NotFound.class, () -> studentService.getStudent(studentId));
    }
}
