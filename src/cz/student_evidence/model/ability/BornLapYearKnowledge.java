package cz.student_evidence.model.ability;

import java.time.Year;

public interface BornLapYearKnowledge extends Knowledge {
    boolean isBornInLapYear();

    static boolean isLapYear(int year) {
        return Year.of(year).isLeap();
    }
}
