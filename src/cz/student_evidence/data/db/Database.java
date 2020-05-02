package cz.student_evidence.data.db;


import cz.student_evidence.data.Storage;
import cz.student_evidence.exception.DbProblem;
import cz.student_evidence.exception.NotConnected;
import cz.student_evidence.model.ExamResult;
import cz.student_evidence.model.student.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Database implements Storage<Student, Long> {


    private final DbConfig dbConfig;
    private Connection dbConnection;

    private boolean isConnected;

    public Database() {
        this.isConnected = false;
        this.dbConfig = new DbConfig();
    }

    @Override
    public void connect() throws NotConnected {
        String dbName = dbConfig.getDbName();
        String dbUrl = dbConfig.getDbUrl();
        DbConfig.Access access = dbConfig.getAccess();

        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://" + dbUrl + "/" + dbName + "?" +
                    "user=" + access.getUser() + "&password=" + access.getPassword() + "");

            dbConnection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbProblem();
        }


        isConnected = true;
    }

    @Override
    public boolean save(Long key, Student student) {
        if (!isConnected) throw new NotConnected("s databazi");
        long id = student.getId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String born = student.getDateOfBorn().toString();
        String type = StudentType.forStudent(student).orElseThrow().name();
        List<Integer> evaluations = student.getExamResults().stream().map(ExamResult::getResult).collect(Collectors.toList());

        try (Statement statement = dbConnection.createStatement()) {
            int dbId = statement.executeUpdate("INSERT INTO student (studentId, firstname, lastname,born,type)" +
                    "VALUES ('" + id + "','" + firstName + "','" + lastName + "','" + born + "','" + type + "')", Statement.RETURN_GENERATED_KEYS);

            for (Integer evaluation : evaluations) {
                statement.executeUpdate("INSERT INTO evaluation (studentId,evaluation) " + "VALUES ('" + dbId + "','" + evaluation + "')");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Student> get(Long key) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean delete(Long key) {
        if (!isConnected) throw new NotConnected("s databazi");
        try (Statement statement = dbConnection.createStatement()) {
            int affectedRows = statement.executeUpdate("DELETE FROM student WHERE studentId='" + key + "'");
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        if (!isConnected) throw new NotConnected("s databazi");
        try (Statement s1 = dbConnection.createStatement();
             ResultSet studentResult = s1.executeQuery("SELECT * FROM student")
        ) {

            if (studentResult.next()) {
                List<Student> students = new ArrayList<>();
                do {
                    int studentId = studentResult.getInt("studentId");
                    String firstname = studentResult.getString("firstname");
                    String lastname = studentResult.getString("lastname");
                    LocalDate born = studentResult.getDate("born").toLocalDate();
                    StudentType studentType = StudentType.valueOf(studentResult.getString("type"));

                    Student student;
                    switch (studentType) {
                        case TECHNICAL:
                            student = new TechnicalStudent(studentId, firstname, lastname, born);
                            break;
                        case HUMANITY:
                            student = new HumanityStudent(studentId, firstname, lastname, born);
                            break;
                        case DISTANCE:
                            student = new DistanceStudent(studentId, firstname, lastname, born);
                            break;
                        default:
                            student = null;
                    }

                    if (student != null) {
                        List<ExamResult> results = new ArrayList<>();
                        try (Statement s2 = dbConnection.createStatement();
                             ResultSet evaluationResult = s2.executeQuery("SELECT * FROM evaluation WHERE studentId='" + studentId + "'");
                        ) {
                            while (evaluationResult.next()) {
                                int evaluation = evaluationResult.getInt("evaluation");
                                results.add(new ExamResult(evaluation));
                            }
                        }

                        student.setExamResults(results);
                        students.add(student);
                    }


                } while (studentResult.next());


                return students;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        throw new

                DbProblem();

    }


    @Override
    public List<Student> getAllWithId(Long key) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Long getLastIndex() {
        if (!isConnected) return 0L;
        try (Statement statement = dbConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(studentId) AS maxId FROM student")
        ) {
            if (resultSet.next()) return resultSet.getLong("maxId");
        } catch (SQLException e) {
        }
        return 0L;
    }
}
