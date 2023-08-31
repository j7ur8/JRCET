package jrcet.help;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final String EMPTY = "";
    private static final String NULL = "null";

    public StringUtil() {
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    public static boolean isBlank(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isBlank(Collection<? extends Object> array) {
        return array == null || array.size() == 0;
    }

    public static boolean isNotBlank(Collection<? extends Object> array) {
        return !isBlank(array);
    }

    public static boolean isBlankAll(Collection<?>... collections) {
        Collection[] var1 = collections;
        int var2 = collections.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Collection<?> c = var1[var3];
            if (!isBlank(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBlankAll(String... strings) {
        String[] var1 = strings;
        int var2 = strings.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            if (!isBlank(s)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBlankAtLeastOne(Collection<?>... collections) {
        Collection[] var1 = collections;
        int var2 = collections.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Collection<?> c = var1[var3];
            if (isBlank(c)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isBlankAtLeastOne(String... strings) {
        String[] var1 = strings;
        int var2 = strings.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            if (isBlank(s)) {
                return true;
            }
        }

        return false;
    }

    public static char[] sortCharArray(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return chars;
    }

    public static String joiner(int[] ints, String split) {
        if (ints.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(String.valueOf(ints[0]));

            for(int i = 1; i < ints.length; ++i) {
                sb.append(split);
                sb.append(ints[i]);
            }

            return sb.toString();
        }
    }

    public static String joiner(double[] doubles, String split) {
        if (doubles.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(String.valueOf(doubles[0]));

            for(int i = 1; i < doubles.length; ++i) {
                sb.append(split);
                sb.append(doubles[i]);
            }

            return sb.toString();
        }
    }

    public static String joiner(float[] floats, String split) {
        if (floats.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(String.valueOf(floats[0]));

            for(int i = 1; i < floats.length; ++i) {
                sb.append(split);
                sb.append(floats[i]);
            }

            return sb.toString();
        }
    }

    public static String joiner(long[] longs, String split) {
        if (longs.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(String.valueOf(longs[0]));

            for(int i = 1; i < longs.length; ++i) {
                sb.append(split);
                sb.append(longs[i]);
            }

            return sb.toString();
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    public static String joiner(Collection<?> c, String split) {
        Iterator<?> iterator = c.iterator();
        if (!iterator.hasNext()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(iterator.next().toString());

            while(iterator.hasNext()) {
                sb.append(split);
                sb.append(toString(iterator.next()).toString());
            }

            return sb.toString();
        }
    }

    public static boolean isBlank(char[] chars) {
        int strLen;
        if (chars != null && (strLen = chars.length) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(chars[i])) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String matcherFirst(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.find() ? input.substring(matcher.start(), matcher.end()) : null;
    }

    public static String trim(String value) {
        if (value == null) {
            return value;
        } else {
            int len = value.length();

            int st;
            for(st = 0; st < len && (Character.isWhitespace(value.charAt(st)) || value.charAt(st) == '\ufeff' || value.charAt(st) == 160 || value.charAt(st) == 12288); ++st) {
            }

            while(st < len && (Character.isWhitespace(value.charAt(len - 1)) || value.charAt(st) == 160 || value.charAt(st) == 12288)) {
                --len;
            }

            return st <= 0 && len >= value.length() ? value : value.substring(st, len);
        }
    }

    public static List<String> matcherAll(String regex, String input) {
        List<String> result = new ArrayList();
        Matcher matcher = Pattern.compile(regex).matcher(input);

        while(matcher.find()) {
            result.add(input.substring(matcher.start(), matcher.end()));
        }

        return result;
    }

    public static Map<Integer, String> matcherAll2Map(String regex, String input) {
        Map<Integer, String> result = new HashMap();
        Matcher matcher = Pattern.compile(regex).matcher(input);

        for(int i = 0; matcher.find(); ++i) {
            result.put(i, matcher.group());
        }

        return result;
    }

    public static String matcherLast(String regex, String input) {
        List<String> result = matcherAll(regex, input);
        return result.size() == 0 ? null : (String)result.get(result.size() - 1);
    }

    public static String getLongString(String word1, String word2) {
        return word1.length() >= word2.length() ? word1 : word2;
    }

    public static String getShortString(String word1, String word2) {
        return word1.length() < word2.length() ? word1 : word2;
    }
}
