package cn.ann.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 17:30
 */
public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        try {
            if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$", s)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                return format.parse(s);
            } else if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$", s)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return format.parse(s);
            } else if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{1,2}:\\d{1,2}$", s)) {
                s = s.replace('T', ' ');
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return format.parse(s);
            } else {
                throw new RuntimeException("输入格式不正确");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
