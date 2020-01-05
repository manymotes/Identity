package com.example.identity.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.inject.Inject;

import com.example.identity.GraphQLResponse;
import com.example.identity.GraphQLTestHelper;
import com.example.identity.IdentityApplication;
import com.example.identity.session.SessionService;
import com.example.identity.session.model.Session;
import com.example.identity.session.repository.SessionRepository;
import com.example.identity.user.UserService;
import com.example.identity.user.model.User;
import com.example.identity.user.model.UserResponse;
import com.example.identity.user.passwordService.PasswordService;
import com.example.identity.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.request.RequestContextHolder;


@ContextConfiguration(classes = IdentityApplication.class)
@GraphQLTest
public class AuthenticationMutationResolverTest {

    private static final String EMAIL = "email@gmail.com";
    private static final String FIRST_NAME = "Steve";
    private static final String LAST_NAME = "Irwin";
    private static final String PASSWORD = "P@ssw0rd!";
    private static final String AUTH_COOKIE = "id";
    private static final UUID USER_UUID = UUID.randomUUID();
    private static final String GENDER = "male";
    private static final UUID SESSION_UUID = UUID.randomUUID();

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private SessionService sessionService;

    @Inject
    private PasswordService passwordService;

    @Inject
    private GraphQLTestHelper graphQLTestHelper;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void loginTest() throws Exception {

        User user = User.builder()
            .password(passwordService.hashPassword(PASSWORD))
            .lastName(LAST_NAME)
            .firstName(FIRST_NAME)
            .email(EMAIL)
            .age(25)
            .uuid(USER_UUID)
            .gender(GENDER)
            .build();

        Session session = Session.builder()
            .createdAt(Instant.now())
            .expiration(Instant.now().plus(15, ChronoUnit.MINUTES))
            .userUuid(USER_UUID)
            .uuid(SESSION_UUID)
            .build();

        when(userRepository.findUserByEmail(any())).thenReturn(user);
        when(sessionRepository.save(any())).thenReturn(session);

        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("email", EMAIL);
        variables.put("password", PASSWORD);

        GraphQLResponse postResult = graphQLTestHelper.perform(LOGIN_QUERY, variables);

        postResult.isOk();
        assertThat(postResult.getRawResponse().getBody())
            .isEqualTo("{\"data\":{\"login\":{\"authState\":\"AUTHENTICATED\",\"userUuid\":\"" + USER_UUID + "\"}}}");
    }

    private static final String LOGIN_QUERY = "mutation Login($email: String!, $password: String!) {" +
        " login(email: $email, password: $password) {" +
        "    authState" +
        "    userUuid" +
        "  }" +
        "}";

}
