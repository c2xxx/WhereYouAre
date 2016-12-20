package com.chen.whereyouare;

import com.chen.whereyouare.net.UploadPositionHelper;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        test();
    }

    private void test() {
        UploadPositionHelper uploadPostionHelper = new UploadPositionHelper();
        log(uploadPostionHelper.getDayFileList(new Date()));
        log(uploadPostionHelper.getHourFileName(new Date()));
    }

    private void log(String dayFileList) {
        System.out.print(dayFileList + "\n");
    }
}