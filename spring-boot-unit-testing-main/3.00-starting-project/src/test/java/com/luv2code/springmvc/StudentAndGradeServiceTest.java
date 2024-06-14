package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradeDao;
import com.luv2code.springmvc.repository.MathGradeDao;
import com.luv2code.springmvc.repository.ScienceGradeDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradeDao mathGradeDao;

    @Autowired
    private ScienceGradeDao scienceGradeDao;

    @Autowired
    private HistoryGradeDao historyGradeDao;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;



    @BeforeEach
    void setupDatabase(){

        /*jdbc.execute("insert into student(id,firstname,lastname,email_address) " +
                "values (1,'Eric','Roby','eric.roby@luv2code_school.com')" );*/
        jdbc.execute(sqlAddStudent);

//        jdbc.execute("insert into math_grade(id,student_id,grade) values (1,1,100.00)");
        jdbc.execute(sqlAddMathGrade);

//        jdbc.execute("insert into science_grade(id,student_id,grade) values (1,1,100.00)");
        jdbc.execute(sqlAddScienceGrade);

//        jdbc.execute("insert into history_grade(id,student_id,grade) values (1,1,100.00)");
        jdbc.execute(sqlAddHistoryGrade);
    }


    @Test
    void createStudentService(){

        studentService.createStudent("Chad","Darby","chad.darby@luv2code_school.com");

        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");

        assertEquals("chad.darby@luv2code_school.com",student.getEmailAddress(),"find by email");

    }

    @Test
    void isStudentNullCheck(){

        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void deleteStudentService(){

        Optional<CollegeStudent> deletedCollageStudent = studentDao.findById(1);

        Optional<MathGrade> deletedMathGrade = mathGradeDao.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradeDao.findById(1);
        Optional<HistoryGrade> deletedHistoryGrad = historyGradeDao.findById(1);

        assertTrue(deletedCollageStudent.isPresent(),"Return True");

        assertTrue(deletedMathGrade.isPresent());
        assertTrue(deletedScienceGrade.isPresent());
        assertTrue(deletedHistoryGrad.isPresent());

        studentService.deleteStudent(1);

        deletedCollageStudent  = studentDao.findById(1);

        deletedMathGrade = mathGradeDao.findById(1);
        deletedScienceGrade = scienceGradeDao.findById(1);
        deletedHistoryGrad = historyGradeDao.findById(1);

        assertFalse(deletedCollageStudent.isPresent(),"Return false");

        assertFalse(deletedMathGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());
        assertFalse(deletedHistoryGrad.isPresent());
    }

    @Sql("/insertData.sql")
    @Test
    void getGradeBookService(){

        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradeBook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        for (CollegeStudent collegeStudent : iterableCollegeStudents){
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5,collegeStudents.size());
    }


    @Test
    void createGradeService(){

        // Create the grade
        assertTrue(studentService.createGrade(80.50,1,"math"));
        assertTrue(studentService.createGrade(80.50,1,"science"));
        assertTrue(studentService.createGrade(80.50,1,"history"));

        // Get all grade with StudentId
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1);

        // Verify there is grades
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,"Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,"Student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2,"Student has history grades");
    }


    @Test
    void createGradeServiceReturnFalse(){

        assertFalse(studentService.createGrade(105,1,"math"));
        assertFalse(studentService.createGrade(-5,1,"math"));
        assertFalse(studentService.createGrade(80.50,2,"math"));
        assertFalse(studentService.createGrade(80.50,1,"literature"));

    }

    @Test
    void deleteGradeService(){

        assertEquals(1,studentService.deleteGrade(1,"math"), "Returns student id after delete");

        assertEquals(1,studentService.deleteGrade(1,"science"), "Returns student id after delete");

        assertEquals(1,studentService.deleteGrade(1,"history"), "Returns student id after delete");
    }


    @Test
    void deleteGradeServiceReturnStudentIdZero(){

        assertEquals(0,studentService.deleteGrade(0,"science"), "No student should have 0 id");

        assertEquals(0,studentService.deleteGrade(1,"literature"), "\"No student should have literature class");
    }

    @Test
    void studentInformation(){

        GradebookCollegeStudent collegeStudent = studentService.studentInformation(1);

        assertNotNull(collegeStudent);
        assertEquals(1,collegeStudent.getId());
        assertEquals("Eric",collegeStudent.getFirstname());
        assertEquals("Roby",collegeStudent.getLastname());
        assertEquals("eric.roby@luv2code_school.com",collegeStudent.getEmailAddress());
        assertTrue(collegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(collegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(collegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    void studentInformationServiceReturnNull(){

        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);

        assertNull(gradebookCollegeStudent);

    }




    @AfterEach
    void setupAfterTransaction(){
        /*jdbc.execute("DELETE FROM student");
        jdbc.execute("DELETE FROM math_grade");
        jdbc.execute("DELETE FROM science_grade");
        jdbc.execute("DELETE FROM history_grade");*/

        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }
}
