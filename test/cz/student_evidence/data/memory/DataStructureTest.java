package cz.student_evidence.data.memory;

import cz.student_evidence.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataStructureTest {

    @Test
    void addItem_emptyKeyParam_shouldBeAdded() {
        DataStructure<String, String> structure = new DataStructure<>();
        structure.addItem("1", "pes");
        structure.addItem("2", "morce");

        boolean isAdded = structure.addItem("", "kocka");

        Assertions.assertTrue(isAdded);
    }

    @Test
    void addItem_nullKeyParam_shouldBeAdded() {
        DataStructure<String, String> structure = new DataStructure<>();
        structure.addItem("1", "pes");
        structure.addItem("2", "morce");

        boolean isAdded = structure.addItem(null, "kocka");

        Assertions.assertTrue(isAdded);
    }

    @Test
    void addItem_toEmptyStructure_shouldBeAdded() {
        DataStructure<String, String> structure = new DataStructure<>();

        boolean isAdded = structure.addItem("1", "kocka");
        Optional<String> value = structure.getFirst("1");

        Assertions.assertTrue(isAdded);
        Assertions.assertTrue(value.isPresent());
        Assertions.assertEquals("kocka", value.get());
    }

    @Test
    void addItem_toStructure_shouldBeAdded() {
        DataStructure<String, String> structure = new DataStructure<>();
        structure.addItem("1", "pes");
        structure.addItem("2", "morce");

        boolean isAdded = structure.addItem("3", "kocka");
        Optional<String> value = structure.getFirst("3");

        Assertions.assertTrue(isAdded);
        Assertions.assertTrue(value.isPresent());
        Assertions.assertEquals("kocka", value.get());
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllWithId() {
    }

    @Test
    void getFirst() {
    }

    @Test
    void deleteAllWithId() {
    }
}
