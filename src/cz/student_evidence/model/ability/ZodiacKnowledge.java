package cz.student_evidence.model.ability;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public interface ZodiacKnowledge extends Knowledge {
    String getZodiac();

    static String computeZodiac(LocalDate date) {
        if (date == null) return "Nezname";

        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.UK);
        int day = date.getDayOfMonth();

        switch (month) {
            case "January":
                if (day < 20)
                    return "Kozoroh";
                else
                    return "Vodnar";

            case "February":
                if (day < 19)
                    return "Vodnar";
                else
                    return "Ryby";

            case "March":
                if (day < 21)
                    return "Ryby";
                else
                    return "Beran";

            case "April":
                if (day < 20)
                    return "Beran";
                else
                    return "Byk";

            case "May":
                if (day < 21)
                    return "Byk";
                else
                    return "Blizenec";

            case "June":
                if (day < 21)
                    return "Blizenec";
                else
                    return "Rak";

            case "July":
                if (day < 23)
                    return "Rak";
                else
                    return "Lev";

            case "August":
                if (day < 23)
                    return "Lev";
                else
                    return "Panna";

            case "September":
                if (day < 23)
                    return "Panna";
                else
                    return "Vahy";

            case "October":
                if (day < 23)
                    return "Vahy";
                else
                    return "Stir";

            case "November":
                if (day < 22)
                    return "Stir";
                else
                    return "Strelec";

            case "December":
                if (day < 22)
                    return "Strelec";
                else
                    return "Kozoroh";

            default:
                return "Nezname";

        }
    }
}
