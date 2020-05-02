package cz.student_evidence.exception;

public class NotConnected extends MyRuntimeException {
    public NotConnected(String resource) {
        super("Neni navazano spojeni -> " + resource);
    }
}
