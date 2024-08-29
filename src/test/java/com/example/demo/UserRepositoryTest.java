package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        // given
        User user = new User("John Doe", 39,"johndoe@example.com");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getAge()).isEqualTo(39);
        assertThat(savedUser.getName()).isEqualTo("John Doe");
        assertThat(savedUser.getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    public void testFindUserById() {
        // given
        User user = new User("John Doe",35, "johndoe@example.com");
        User savedUser = userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // then
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
        assertThat(foundUser.get().getAge()).isEqualTo(35);
        assertThat(foundUser.get().getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    public void testFindAllUsers() {
        // given
        User user1 = new User("John Doe",40, "johndoe@example.com");
        User user2 = new User("Jane Foe",45, "janedoe@example.com");
        userRepository.save(user1);
        userRepository.save(user2);
        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getName()).isEqualTo("John Doe");
        assertThat(users.get(0).getAge()).isEqualTo(40);
        assertThat(users.get(0).getEmail()).isEqualTo("johndoe@example.com");
        assertThat(users.get(1).getName()).isEqualTo("Jane Foe");
        assertThat(users.get(1).getAge()).isEqualTo(45);
        assertThat(users.get(1).getEmail()).isEqualTo("janedoe@example.com");
    }

    @Test
    public void testUpdateUser() {
        // given
        User user = new User("John Doe",40, "johndoe@example.com");
        User savedUser = userRepository.save(user);

        // when
        savedUser.setName("Jane Foe");
        User updatedUser = userRepository.save(savedUser);

        // then
        assertThat(updatedUser.getName()).isEqualTo("Jane Foe");
        assertThat(updatedUser.getAge()).isEqualTo(40);
        assertThat(updatedUser.getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    public void testDeleteUser() {
        // given
        User user = new User("John Doe", 40,"johndoe@example.com");
        User savedUser = userRepository.save(user);

        // when
        userRepository.delete(savedUser);

        // then
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser.isPresent()).isFalse();
    }
}
