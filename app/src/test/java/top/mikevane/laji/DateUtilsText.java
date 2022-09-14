package top.mikevane.laji;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import top.mikevane.laji.utils.DateUtils;

public class DateUtilsText {
    @Test
    public void text(){
        String s = DateUtils.formateDateTime(new Date());
        System.out.println(s );
}
}
