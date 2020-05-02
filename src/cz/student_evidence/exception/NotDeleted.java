package cz.student_evidence.exception;

public class NotDeleted extends MyCheckedException {
    public NotDeleted(String resource) {
        super("Nepodarilo se smazat -> " + resource);
    }
}
