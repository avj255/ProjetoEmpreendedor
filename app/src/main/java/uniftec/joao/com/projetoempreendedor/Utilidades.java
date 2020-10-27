package uniftec.joao.com.projetoempreendedor;

import android.graphics.Color;

import java.util.Calendar;

public class Utilidades {

    public static String getDescricaoSituacao(int situacao)
    {
        switch (situacao)
        {
            case 1: return "Aberto";
            case 2: return "Em Produção";
            case 3: return "Finalizado";
            case 4: return "Cancelado";
        }
        return null;
    }

    public static Integer getCorSituacao(int situacao)
    {
        switch (situacao)
        {
            case 1: return Color.parseColor("#fc7b03");
            case 2: return Color.parseColor("#56ad3e");
            case 3: return Color.parseColor("#911f1d");
        }
        return null;
    }

    public static String WeekDay() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                return "1";
            case Calendar.TUESDAY:
                return "2";
            case Calendar.WEDNESDAY:
                return "3";
            case Calendar.THURSDAY:
                return "4";
            case Calendar.FRIDAY:
                return "5";
            case Calendar.SATURDAY:
                return "6";
            case Calendar.SUNDAY:
                return "7";
        }
        return "";
    }
}
