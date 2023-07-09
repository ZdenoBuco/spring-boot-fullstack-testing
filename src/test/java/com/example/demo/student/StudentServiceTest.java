package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
// this annotation replace all staff commented with star
class StudentServiceTest {
    //run studentServiceTest with Coverage - I can check the coverage of tests
    @Mock
    private StudentRepository studentRepository;
    // * private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
    // *    autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

//  *  @AfterEach
//  *  void tearDown() throws Exception {
//  *      autoCloseable.close();
//  *  }

    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll(); //from Mockito
        // we don't involve real repo because with Mock it's much faster (we're not entering to DB, creating instantiates nor insert new student, etc.  )
    }

    @Test
    void canAddStudent() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE );
        //when
        underTest.addStudent(student);
        // then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository)
                .save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE );
        given(studentRepository.selectExistsEmail(student.getEmail()))
                .willReturn(true);

        //when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");
        verify(studentRepository, never()).save(any());
        //if exception is thrown, check if any student is saved in repo.
    }

    @Test
    @Disabled
    void canDeleteStudent() {
    }
}