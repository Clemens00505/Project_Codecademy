package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import java.util.Date; 

public class DateToolsTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test31DaysInMonth() {
        int day = 31;
        int month = 1;
        int year = 2020;
        assertTrue(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 && 1 <= day && day <= 31);
        
    }

    @Test
    public void test30DaysInMonth() {
        int day = 30;
        int month = 4;
        int year = 2020;
        assertTrue(month == 4 || month == 6 || month == 9 || month == 11 && 1 <= day && day <= 30);

    }

    @Test
    public void test29DaysInMonth() {
        int day = 29;
        int month = 2;
        int year = 2020;
        assertTrue( month == 2 && 1 <= day && day <= 29 && year % 4 == 0);

    }

    @Test
    public void test28DaysInMonth() {
        int day = 28;
        int month = 2;
        int year = 2019;
        assertTrue( month == 2 && 1 <= day && day <= 28 && year % 4 != 0);
    }

    @Test
    public void testBeforeCurrentYear() {
        Date d = new Date(); 
        int currentYear = d.getYear();
        int year = 2023;
        
        assertTrue( year <= currentYear);

    }
    @Test
    public boolean testAllOtherCases() {
        return false;
    }
}
