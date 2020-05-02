package cz.student_evidence.data.db;

import cz.student_evidence.exception.BadDbConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DbConfig {

    private static final String CONFIG_PATH = "resources/db_access.txt";

    private static final String DB_URL = "localhost:3306";

    private static final String DB_NAME = "student_evidence";

    public Access getAccess() {
        try {
            List<String> lines = Files.readAllLines(Path.of(CONFIG_PATH));
            if (lines.size() >= 2) {
                String username = lines.get(0);
                String password = lines.get(1);
                return new Access(username, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new BadDbConfig();
    }

    public String getDbUrl() {
        return DB_URL;
    }

    public String getDbName() {
        return DB_NAME;
    }

    public static class Access {
        private String user, password;

        public Access(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }
    }

}
