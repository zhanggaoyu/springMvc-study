package cn.ann;

import cn.ann.utils.StringToDateConverter;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 18:32
 */
public class Demo {

    @Test
    public void convert() {
        System.out.println(new StringToDateConverter().convert("1997-12-30 12112:31:02"));
    }

}
