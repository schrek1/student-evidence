package cz.student_evidence.exception;

public class DbProblem extends MyRuntimeException {
    public DbProblem() {
        super("Problem s databazi");
    }
}
