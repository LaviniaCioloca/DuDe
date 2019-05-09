//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

/**
 * The statute for an element can be:
 * 1) NORMAL - it is in the source code
 * 2) LIBRARY - it belongs to the library
 * 3) FAILEDDEP - it isn't present neither in the source code nor in the library.
 */
public class Statute {
    public static final int NORMAL = 1;
    public static final int LIBRARY = 2;
    public static final int FAILEDDEP = 3;

    public static String convertToString(int statute) {
        String result = "unknown";
        switch(statute) {
            case NORMAL:
                result = "NORMAL";
                break;
            case LIBRARY:
                result = "LIBRARY";
                break;
            case FAILEDDEP:
                result = "FAILEDDEP";
                break;
        }
        return result;
    }
}
