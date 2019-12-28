package aom.pokeapi.util;

import android.text.Html;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Utils {

    public static String capitalizeFirstLetter(String text){
        return text.substring(0,1).toUpperCase() + text.substring(1);
    }

    public static String formatPrettyInt(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        String formatted = formatter.format(number);
        return formatted.replaceAll(",", ".");
    }

    public static Spanned html(String htmlTextFormatted){
        return Html.fromHtml(htmlTextFormatted);
    }
}
