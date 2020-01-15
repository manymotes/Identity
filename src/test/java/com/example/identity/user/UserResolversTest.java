package com.example.identity.user;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

import com.example.identity.GraphQLResponse;
import com.example.identity.GraphQLTestHelper;
import com.example.identity.IdentityApplication;
import com.example.identity.session.SessionService;
import com.example.identity.session.repository.SessionRepository;
import com.example.identity.user.model.User;
import com.example.identity.user.model.UserInput;
import com.example.identity.user.passwordService.PasswordService;
import com.example.identity.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTest;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = IdentityApplication.class)
@GraphQLTest
public class UserResolversTest extends GraphQLTestHelper {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SessionRepository sessionRepository;

    @Inject
    private PasswordService passwordService;

    @Inject
    private UserService userService;

    @Inject
    private SessionService sessionService;

    private static final String EMAIL = "email@gmail.com";
    private static final String FIRST_NAME = "Steve";
    private static final String LAST_NAME = "Irwin";
    private static final String PASSWORD = "P@ssw0rd!";
    private static final String AUTH_COOKIE = "id";
    private static final UUID USER_UUID = UUID.randomUUID();
    private static final String GENDER = "male";
    private static final UUID SESSION_UUID = UUID.randomUUID();

    @Test
    public void createUserTest() throws IOException, JSONException, ParseException {

        User user = User.builder()
            .password(passwordService.hashPassword(PASSWORD))
            .lastName(LAST_NAME)
            .firstName(FIRST_NAME)
            .email(EMAIL)
            .age(25)
            .uuid(USER_UUID)
            .gender(GENDER)
            .build();

        when(userRepository.save(any())).thenReturn(user);

        UserInput userInput = UserInput.builder()
            .email(EMAIL)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .password(PASSWORD)
            .build();



        ObjectMapper mapper = new ObjectMapper();
        ObjectNode vars = mapper.valueToTree(userInput);
        ObjectNode variables = mapper.createObjectNode();

        variables.put("user", vars);


        GraphQLResponse postResult = perform(CREATE_USER_QUERY, variables);

        postResult.isOk();


        JSONObject object = (JSONObject) new JSONParser().parse(postResult.getRawResponse().getBody());
        JSONObject data = (JSONObject) object.get("data");
        JSONObject userJson = (JSONObject) object.get("user");
    }

    private static final String CREATE_USER_QUERY = "mutation CreateUser($user: UserInput!) {" +
        "  createUser(user: $user) {" +
        "    email" +
        "    uuid" +
        "    firstName" +
        "    lastName" +
        "  }" +
        "}";
}
