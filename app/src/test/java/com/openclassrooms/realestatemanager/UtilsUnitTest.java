package com.openclassrooms.realestatemanager;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {

    private final String nowDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

    @Test
    public void eurosToDollars() {
        assertEquals(Utils.convertEuroToDollar(5000), 5686);
    }

    @Test
    public void dollarsToEuros() {
        assertEquals(Utils.convertDollarToEuro(15000),
                13188);
    }

    @Test
    public void getTodayDate() {
        assertEquals(nowDate, Utils.getTodayDate());
    }

    @Test
    public void transformDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(format.parse(nowDate), Utils.stringToDate(nowDate));
    }

}