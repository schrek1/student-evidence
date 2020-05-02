package cz.student_evidence.exception;

public class NotFound extends MyCheckedException {
    public NotFound(String resource) {
        super("Nepodarilo se nalezt -> " + resource);
    }
}
