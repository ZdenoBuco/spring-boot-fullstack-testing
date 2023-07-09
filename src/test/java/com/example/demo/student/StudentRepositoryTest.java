package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
//redirect to testing DB
class StudentRepositoryTest {
    //DB doesn't work but this is working testing...

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        //deleting all data in DB after test
    }

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
    @Test
    void itShouldCheckIfStudentDoesNotExistsByEmail() {
        // given
        String email = "jamila@gmail.com";
        // when
        Boolean exists = underTest.selectExistsEmail(email);
        // then
        assertThat(exists).isFalse();
    }
    // we're testing method .selectExistEmail because that's only method what we created/wrote down
}