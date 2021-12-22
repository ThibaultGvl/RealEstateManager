package com.openclassrooms.realestatemanager;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private String nowDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void eurosToDollars(){
        assertEquals(Utils.convertEuroToDollar(5000), 5710);
    }

    @Test
    public void dollarsToEuros(){
        assertEquals(Utils.convertDollarToEuro(780000), 633360);
    }

    @Test
    public void getTodayDate() {
        assertEquals(nowDate, Utils.getTodayDate());
    }
}