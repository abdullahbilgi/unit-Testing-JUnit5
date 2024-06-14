package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;


    // @Mock
    @MockBean
    private ApplicationDao applicationDao;

    // @InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    void beforeEach(){

        studentOne.setFirstname("Eric");
        studentOne.setLastname("Roby");
        studentOne.setEmailAddress("eric.roby@luv2code_school.com");
        studentOne.setStudentGrades(studentGrades);
    }


    @DisplayName("When & Verify")
    @Test
    void assertEqualTestGrades(){

        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.0);

        assertEquals(100.0,applicationService.addGradeResultsForSingleClass(studentOne.getStudentGrades().getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());

        verify(applicationDao,times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());

    }

    @DisplayName("Find Gpa")
    @Test
    void assertEqualFindGpa(){

        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31,applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));

    }

    @DisplayName("Check Null")
    @Test
    void testAssertCheckNull(){

        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

        assertNotNull(applicationService.checkNull(studentOne.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Throw Runtime Exception")
    @Test
    void throwRuntimeError(){

        CollegeStudent nullStudent = (CollegeStudent)context.getBean("collegeStudent");

        doThrow(RuntimeException.class).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        verify(applicationDao,times(1)).checkNull(nullStudent);

    }

    @DisplayName("Mulitple Stubbing")
    @Test
    void stubbingConsecutiveCalls(){

        CollegeStudent nullStudent = (CollegeStudent)context.getBean("collegeStudent");

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(RuntimeException.class)
                .thenReturn("Dont throw exception second time");

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Dont throw exception second time",applicationService.checkNull(nullStudent));

        verify(applicationDao,times(2)).checkNull(nullStudent);


    }




}
