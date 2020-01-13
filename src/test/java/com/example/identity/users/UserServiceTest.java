package com.example.identity.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import javax.inject.Inject;

import com.example.identity.user.model.User;
import com.example.identity.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    private static final String EMAIL = "email@gmail.com";
    private static final String FIRST_NAME = "Steve";
    private static final String LAST_NAME = "Irwin";
    private static final String PASSWORD = "P@ssw0rd!";
    private static final UUID USER_UUID = UUID.randomUUID();
    private static final String GENDER = "male";
    private static final int AGE = 99;

    @Inject
    private UserRepository userRepository;


    @Test
    public void testCreateUer() {

        User userToSave = User.builder()
            .age(AGE)
            .email(EMAIL)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .password(PASSWORD)
            .uuid(USER_UUID)
            .gender(GENDER)
            .build();

        User user = userRepository.save(userToSave);

        user.setUuid(userToSave.getUuid());

        assertThat(user).isEqualTo(userToSave);
    }


}
