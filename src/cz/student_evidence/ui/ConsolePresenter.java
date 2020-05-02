package cz.student_evidence.ui;

import cz.student_evidence.exception.MyRuntimeException;
import cz.student_evidence.exception.NotDeleted;
import cz.student_evidence.exception.NotFound;
import cz.student_evidence.exception.NotSaved;
import cz.student_evidence.model.ability.Ability;
import cz.student_evidence.model.ability.BornLapYearKnowledge;
import cz.student_evidence.model.ability.ZodiacKnowledge;
import cz.student_evidence.model.student.Student;
import cz.student_evidence.model.student.StudentType;
import cz.student_evidence.services.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ConsolePresenter {

    private final StudentService studentService;
    private final ScannerTool scannerTool;

    public ConsolePresenter() {
        this.studentService = new StudentService();
        this.scannerTool = new ScannerTool();
    }

    public ConsolePresenter(StudentService studentService, ScannerTool scannerTool) {
        this.studentService = studentService;
        this.scannerTool = scannerTool;
    }

    public void start() {
        while (true) {
            printMenu();

            int operation = scannerTool.readIntegerFromConsole();

            System.out.println();

            switch (operation) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    addEvaluation();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    getStudentInfo();
                    break;
                case 5:
                    performStudentOperation();
                    break;
                case 6:
                    studentsByType();
                    break;
                case 7:
                    avgByTypes();
                    break;
                case 8:
                    countStudentsByTypes();
                    break;
                case 9:
                    connectToDb();
                    break;
                case 10:
                    loadAllStudentsFromDb();
                    break;
                case 11:
                    saveStudentsToDb();
                    break;
                case 12:
                    deleteStudentFromDb();
                    break;
                case 13:
                    loadStudentFromDb();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Neznama operace opakujte vyber!");
            }

            scannerTool.promptEnterKey();
        }
    }

    private void loadStudentFromDb() {
        System.out.print("Zadejte jmeno: ");
        String firstName = scannerTool.readStringFromConsole();

        System.out.print("Zadejte prijmeni: ");
        String lastName = scannerTool.readStringFromConsole();

        try {
            studentService.loadStudentFromDb(firstName,lastName);
            System.out.println("Nacten student " + firstName + " " + lastName);
        } catch (MyRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteStudentFromDb() {
        System.out.print("Zadejte id studenta: ");
        long id = scannerTool.readLongFromConsole();

        try {
            studentService.deleteStudentFromDb(id);
            System.out.println("Smazan student id " + id);
        } catch (MyRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveStudentsToDb() {
        try {
            int count = studentService.saveStudentsToDb();
            System.out.println("Ulozeno " + count + " zaznamu");
        } catch (MyRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadAllStudentsFromDb() {
        try {
            int count = studentService.loadAllStudentsFromDb();
            System.out.println("Nacteno " + count + " zaznamu");
        } catch (MyRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void connectToDb() {
        try {
            studentService.connectToDb();
            System.out.println("Pripojeno k DB");
        } catch (MyRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void countStudentsByTypes() {
        Map<StudentType, Integer> groups = studentService.getStudentCountByType();
        Stream.of(StudentType.values()).forEach(type -> {
            Integer count = groups.get(type);
            System.out.println("Pocet studentu " + type.getDescription() + ": " + count);
        });
    }

    private void avgByTypes() {
        Map<StudentType, Double> groups = studentService.getAvgByType();
        Stream.of(StudentType.values()).forEach(type -> {
            Double average = groups.get(type);
            System.out.println("Prumer " + type.getDescription() + ": " + average);
        });
    }

    private void studentsByType() {
        Map<StudentType, List<Student>> groups = studentService.getStudentsByType();
        Stream.of(StudentType.values()).forEach(type -> {
            System.out.println("Studenti " + type.getDescription() + ":");
            List<Student> students = groups.get(type);
            if (students != null) {
                students.stream()
                        .sorted((s1, s2) -> s1.getLastName().compareToIgnoreCase(s2.getLastName()))
                        .forEach(student -> System.out.println(
                                student.getId() + " " + student.getFirstName() + " " + student.getLastName() + " " + student.getDateOfBorn().toString() + " " + student.getAvgEvaluation()
                        ));
            }
            System.out.println();
        });
    }

    private void performStudentOperation() {
        System.out.print("Zadejte id studenta: ");
        long id = scannerTool.readLongFromConsole();

        try {
            Student student = studentService.getStudent(id);
            Set<Ability> abilities = studentService.getStudentAbility(id);

            if (abilities.contains(Ability.ZODIAC_KNOWLEDGE)) {
                String zodiac = ((ZodiacKnowledge) student).getZodiac();
                System.out.println("Znameni zverokruhu: " + zodiac);
            }

            if (abilities.contains(Ability.BORN_LAP_YEAR_KNOWLEDGE)) {
                String isBorInLapYear = ((BornLapYearKnowledge) student).isBornInLapYear() ? "ano" : "ne";
                System.out.println("Narozen v prestupnem roce: " + isBorInLapYear);
            }

        } catch (NotFound e) {
            System.out.println(e.getMessage());
        }
    }

    private void getStudentInfo() {
        System.out.print("Zadejte id studenta: ");
        long id = scannerTool.readLongFromConsole();

        studentService.getStudents(id).forEach(student -> System.out.println(
                student.getFirstName() + " " + student.getLastName() + " " + student.getDateOfBorn().toString() + " " + student.getAvgEvaluation()
        ));
    }

    private void deleteStudent() {
        System.out.print("Zadejte id studenta: ");
        long id = scannerTool.readLongFromConsole();

        try {
            studentService.deleteStudent(id);
        } catch (NotDeleted e) {
            System.out.println(e.getMessage());
        }
    }

    private void addEvaluation() {
        System.out.print("Zadejte id studenta: ");
        long id = scannerTool.readLongFromConsole();

        System.out.print("Zadejte hodnoceni: ");
        int evaluation;
        boolean isValidEvaluation = false;
        do {
            evaluation = scannerTool.readIntegerFromConsole();
            if (evaluation >= 1 && evaluation <= 5) {
                isValidEvaluation = true;
            } else {
                System.out.println("Neplatna znamka");
            }
        } while (!isValidEvaluation);

        try {
            studentService.addEvaluation(id, evaluation);
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        }
    }

    private void addNewStudent() {
        StudentType[] studentTypes = StudentType.values();

        System.out.println("Vyberete typ studenta:");

        for (int i = 0; i < studentTypes.length; i++) {
            StudentType type = studentTypes[i];
            int orderNumber = i + 1;
            System.out.println(orderNumber + ") " + type.getDescription());
        }

        System.out.print("Zadejte volbu: ");

        int selectedTypeNumber;
        boolean isValidTypeNumber = false;
        do {
            selectedTypeNumber = scannerTool.readIntegerFromConsole();
            if (selectedTypeNumber >= 1 && selectedTypeNumber <= studentTypes.length) {
                isValidTypeNumber = true;
            } else {
                System.out.println("Neplatna volba");
            }
        } while (!isValidTypeNumber);

        StudentType selectedType = studentTypes[selectedTypeNumber - 1];

        System.out.print("Zadejte jmeno: ");
        String firstName = scannerTool.readStringFromConsole();

        System.out.print("Zadejte prijmeni: ");
        String lastName = scannerTool.readStringFromConsole();

        System.out.print("Zadejte den narozeni (1-31): ");
        int bornDay = scannerTool.readIntegerFromConsole();

        System.out.print("Zadejte mesic narozeni (1-12): ");
        int bornMonth = scannerTool.readIntegerFromConsole();

        System.out.print("Zadejte rok narozeni: ");
        int bornYear = scannerTool.readIntegerFromConsole();

        LocalDate bornDate = LocalDate.of(bornYear, bornMonth, bornDay);

        try {
            long id = studentService.saveStudent(firstName, lastName, bornDate, selectedType);
            System.out.println("Zaznam ulozen s id=" + id);
        } catch (NotSaved e) {
            System.out.println(e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("Student manager");
        System.out.println("-------------------");
        System.out.println("1) pridat studenta");
        System.out.println("2) zadat znamku studentovi");
        System.out.println("3) smazat studenta");
        System.out.println("4) informace o studentovi");
        System.out.println("5) vykonat akci studenta");
        System.out.println("6) vypis vsech studentu");
        System.out.println("7) vypis souhrnych prumeru dle typu studia");
        System.out.println("8) pocet studentu dle typu studia");
        System.out.println("9) pripojit se k db");
        System.out.println("10) Nacteni studentu z DB");
        System.out.println("11) Ulozeni studentu do DB");
        System.out.println("12) Smazani studenta z DB");
        System.out.println("13) Nacteni studenta z DB");
        System.out.println("0) ukoncit program");
        System.out.println("-------------------");
        System.out.print("Zadejte akci: ");
    }


}
