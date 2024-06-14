package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void beforeEach(){
        demoUtils = new DemoUtils();

        System.out.println("@BeforeEach executes before the execution of each test method");
    }


    @Test
    @DisplayName("Equals And Not Equals")
    void testEqualsAndNotEquals(){

        System.out.println("Running test: testEqualsAndNotEquals");

        assertEquals(6,demoUtils.add(2,4),"2+4 must be 6");
        assertNotEquals(6,demoUtils.add(1,9),"1+9 must not be 6");

    }

    @Test
    @DisplayName("Multiply")
    void testMultiply(){
        assertEquals(12,demoUtils.multiply(3,4),"3*4 must be 12");
    }

    @Test
    @DisplayName("Null And Not Null")
    void testNullAndNotNull(){

        System.out.println("Running test: testNullAndNotNull");

        String str1 = null;
        String str2 = "luv2code";

        assertNull(demoUtils.checkNull(str1),"Object should be null");
        assertNotNull(demoUtils.checkNull(str2),"Object should be not null");

    }

    @Test
    @DisplayName("Same and Not Same")
    void testSameAndNotSame(){

        String str = "love2code";

        assertSame(demoUtils.getAcademy(),demoUtils.getAcademyDuplicate(),"object should refer to same object");
        assertNotSame(str,demoUtils.getAcademy(),"object should not refer to  same object");

    }

    @Test
    @DisplayName("True and False")
    void testTrueAndFalse(){

        int gradeOne=10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne,gradeTwo),"This should return true");
        assertFalse(demoUtils.isGreater(gradeTwo,gradeOne),"This should return false");
    }

    @Test
    @DisplayName("Array Equal")
    void testArrayEquals(){

        String [] stringArray = {"A","B","C"};

        assertArrayEquals(stringArray,demoUtils.getFirstThreeLettersOfAlphabet(),"Arrays should be the same");

    }

    @Test
    @DisplayName("Iterable Equals")
    void testIterableEquals(){

        List<String> theList = List.of("luv","2","code");

        assertIterableEquals(theList,demoUtils.getAcademyInList(),"Expected List should be same as actual list");
    }

    @Test
    @DisplayName("Line Matches")
    void testLineMatch(){

        List<String> theList = List.of("luv","2","code");

        assertLinesMatch(theList,demoUtils.getAcademyInList(),"Line should be match");

    }

    @Test
    @DisplayName(" Throws and Does Not Throws")
    void testThrowsAndDoesNotThrows(){

        assertThrows(Exception.class,()->{demoUtils.throwException(-1);},"should throw exception");

        assertDoesNotThrow(()->{demoUtils.throwException(5);},"should not throw exception");

    }

    @Test
    @DisplayName("Timeout")
    void testTimeout(){

        assertTimeoutPreemptively(Duration.ofSeconds(3),()->{demoUtils.checkTimeout();},"Method should execute 3 seconds");
    }








    /*@BeforeAll
    static void beforeAll(){
        System.out.println("@BeforeAll execute only once before all test methods executions in the class");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("@AfterAll execute only once after all test methods executions in the class");
    }

    @AfterEach
    void afterEach(){
        System.out.println("Running @AfterEach");
        System.out.println();
    }*/







}
