package top.mikevane.laji.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MikeV
 */
public class StringUtil {

    private static class LayHolder {
        private static final StringUtil INSTANCE = new StringUtil();
    }

    private StringUtil() {
    }

    public static StringUtil getInstance() {
        return LayHolder.INSTANCE;
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public String getRandomChar() {
        Random random = new Random();
        String s = "qw2ert1yui6opa7s3df9ghj5klz0x4cv8bnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = s.toCharArray();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(chars[random.nextInt(s.length())]);
        }
        return stringBuffer.toString();
    }

    public String DateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = simpleDateFormat.format(date);
        return result;
    }

    public boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public boolean isNullOrEmpty(Object obj) {
        if (obj == null){
            return true;
        }
        if (obj instanceof CharSequence){
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    public ArrayList readTxt() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        String encoding = "GBK";
        File file = new File("D:\\image\\txt\\all.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String txt;
        while ((txt = bufferedReader.readLine()) != null) {
            list.add(txt);
        }
        reader.close();
        return list;
    }

    public String txtReplace(String test) {
        try {
            ArrayList<String> list = readTxt();
            test = test.replaceAll("\\s*|\t|\r|\n", "");
            for (String aList : list) {
                test = test.replaceAll(aList, "**");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return test;
    }
}
