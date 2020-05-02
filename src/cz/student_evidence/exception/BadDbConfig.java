package cz.student_evidence.exception;

public class BadDbConfig extends MyRuntimeException {
    public BadDbConfig() {
        super("Chybny soubor konfigurace db");
    }
}
