package info.itsthesky.DiSky.tools;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String getPrefixName() {
        return "disky";
    }

    public static Number[] toNumeric(final Object ...array) {
        if (array == null || array.length == 0) return null;
        final List<Number> result = new ArrayList<>();
        for (Object o : array) {
            if (o instanceof Number) {
                result.add((Number) o);
                continue;
            }
            return null;
        }
        return result.toArray(result.toArray(new Number[0]));
    }

}
