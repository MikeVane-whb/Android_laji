package top.mikevane.laji;

import org.junit.Test;

import java.util.Date;

import top.mikevane.laji.utils.DateUtil;

public class DateUtilText {
    @Test
    public void text(){
        String s = DateUtil.formateDateTime(new Date());
        System.out.println(s );
}
}
