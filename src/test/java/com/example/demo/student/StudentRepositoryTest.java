package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {
    //DB doesn't work but this is working testing...

    @Autowired
    private StudentRepository underTest;


    @Test
    void itShouldCheckIfStudentExistsByEmail() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE );
        underTest.save(student);
        // when
        Boolean exists = underTest.selectExistsEmail(email);
        // then
        assertThat(exists).isTrue();
    }
}