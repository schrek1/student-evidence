package cz.student_evidence.exception;

public class NotSaved extends MyCheckedException {
    public NotSaved(String resource) {
        super("Nepodarilo se ulozit -> " + resource);
    }
}
