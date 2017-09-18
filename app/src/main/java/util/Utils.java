package util;

import java.text.DecimalFormat;

/**
 * Created by Ofer Dan-On on 9/14/2017.
 */

public class Utils {

    public static String formatNumber(int val){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(val);
        return formatted;
    }
}
