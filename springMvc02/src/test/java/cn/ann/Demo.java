package cn.ann;

import org.junit.Test;

import java.io.File;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 16:42
 */
public class Demo {
    @Test
    public void demo01() {
        File file = new File("upload");
        System.out.println(file.exists());

    }
}
