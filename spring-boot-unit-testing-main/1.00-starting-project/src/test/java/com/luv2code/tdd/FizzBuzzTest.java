package com.luv2code.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    // If number is divisible by 3 , print Fizz

    @DisplayName("Divisible by three")
    @Test
    @Order(1)
    void testForDivisibleThree(){

        String expected = "Fizz";

        assertEquals(expected,FizzBuzz.compute(3),"Should return Fizz");


    }

    // If number is divisible by 5 , print Buzz

    @DisplayName("Divisible by five")
    @Test
    @Order(2)
    void testForDivisibleFive(){

        String expected = "Buzz";

        assertEquals(expected,FizzBuzz.compute(5),"Should return Buzz");


    }

    // If number is divisible by 3 and 5 , print FizzBuzz

    @DisplayName("Divisible by three and five")
    @Test
    @Order(3)
    void testForDivisibleThreeAndFive(){

        String expected = "FizzBuzz";

        assertEquals(expected,FizzBuzz.compute(15),"Should return FizzBuzz");


    }

    // If number is NOT divisible by 3 and 5 , print number

    @DisplayName("NOt divisible by three and five")
    @Test
    @Order(4)
    void testForNotDivisibleThreeAndFive(){

        String expected = "1";

        assertEquals(expected,FizzBuzz.compute(1),"Should return 1");


    }

    @DisplayName("Testing small test data")
    @ParameterizedTest(name = "value={0} , expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(5)
    void testSmallDataFile(int value,String expected) {

        assertEquals(expected,FizzBuzz.compute(value));

    }

    @DisplayName("Testing medium test data")
    @ParameterizedTest(name = "value={0} , expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(6)
    void testMediumDataFile(int value,String expected) {

        assertEquals(expected,FizzBuzz.compute(value));

    }

    @DisplayName("Testing large test data")
    @ParameterizedTest(name = "value={0} , expected={1}")
    @CsvFileSource(resources = "/large-test-data.csv")
    @Order(7)
    void testLargeDataFile(int value,String expected) {

        assertEquals(expected,FizzBuzz.compute(value));

    }







    }
