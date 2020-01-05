package com.example.identity.Authentication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

import com.example.identity.GraphQLResponse;
import com.example.identity.IdentityApplication;
import com.example.identity.ResolverTest;
import com.example.identity.authentication.AuthenticationMutationResolver;
import com.example.identity.session.SessionService;
import com.example.identity.session.model.Session;
import com.example.identity.session.repository.SessionRepository;
import com.example.identity.user.UserService;
import com.example.identity.user.model.User;
import com.example.identity.user.model.UserResponse;
import com.example.identity.user.passwordService.PasswordService;
import com.example.identity.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes= IdentityApplicationTests.class)
//@RunWith(SpringRunner.class)
//@GraphQLTest
//@EnableJpaRepositories("com.example.identity")

//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = IdentityApplication.class)
//@WebMvcTest


@ContextConfiguration(classes = IdentityApplication.class)
//@RunWith(SpringRunner.class)
@GraphQLTest
public class AuthenticationMutationResolverTest extends ResolverTest {

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

    private UserResponse userResponse;

//    @Inject
//    private MockMvc mvc;
//
//    @Autowired
//    private GraphQLTestTemplate graphQLTestTemplate;


    @BeforeEach
    public void setup() {
//        UserInput userInput = UserInput.builder()
//            .email(EMAIL)
//            .firstName(FIRST_NAME)
//            .lastName(LAST_NAME)
//            .password(PASSWORD)
//            .build();
//
//        userResponse = userService.createUser(userInput);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @AfterEach
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
//        userService.deleteUser(userResponse.getUuid());
    }

    @Test
    public void loginTest() throws Exception {

//        Map<String, Object> vars = Map.of("email", EMAIL, "password", PASSWORD);

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

//        var variables = new LinkedHashMap<>();
        ObjectNode variables = new ObjectMapper().createObjectNode();
        variables.put("email", EMAIL);
        variables.put("password", PASSWORD);
//
        GraphQLResponse postResult = perform(LOGIN_QUERY, variables);

        postResult.isOk();
//
//        Map<String, Object> vars = Map.of("email", EMAIL, "password", PASSWORD);
//
//        mvc.perform((asyncDispatch(postGraphQL(LOGIN_QUERY, vars))))
//            .andExpect(status().isOk())
//            .andExpect(cookie().exists(AUTH_COOKIE))
//            .andExpect(jsonPath("$.data.login.env.name").value("test"))
//            .andExpect(jsonPath("$.data.login.customers").isEmpty())
//            .andExpect(jsonPath("$.data.login.features").isEmpty());
//            .andExpect(jsonPath("$.data.login.authState").value(AuthState.SETUP_MFA.toString()))
//            .andExpect(jsonPath("$.data.login.userUuid").value(user.getUuid().toString()));

//
//        var postResults = performGraphQlPost("", vars);
//
//        String tmp = postResults.toString();

    }

    private static final String LOGIN_QUERY = "mutation Login($email: String!, $password: String!) {" +
        " login(email: $email, password: $password) {" +
        "    authState" +
        "    userUuid" +
        "  }" +
        "}";

//
//
//    private ResultActions performGraphQlPost(String query) throws Exception {
//        return performGraphQlPost(query, null);
//    }
//
//    private ResultActions performGraphQlPost(String query, Map variables) throws Exception {
//        return mvc.perform(post("/v1/graphql")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(generateRequest(query, variables))
//        );
//    }
//
//    protected String generateRequest(String query, Map variables) throws JSONException {
//        var jsonObject = new JSONObject();
//
//        jsonObject.put("query", query);
//
//        if (variables != null) {
//            jsonObject.put("variables", Collections.singletonMap("input", variables));
//        }
//
//        return jsonObject.toString();
//    }


    private ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders headers = new HttpHeaders();

    @Autowired(required = false)
    private TestRestTemplate restTemplate;



    public GraphQLResponse perform(String graphqlQuery, ObjectNode variables) throws IOException {

        String payload = createJsonQuery(graphqlQuery, variables);
        return post(payload);
    }


    private String createJsonQuery(String graphql, ObjectNode variables)
        throws JsonProcessingException {

        ObjectNode wrapper = objectMapper.createObjectNode();
        wrapper.put("query", graphql);
        wrapper.set("variables", variables);
        return objectMapper.writeValueAsString(wrapper);
    }

    private GraphQLResponse post(String payload) {
        return postRequest(forJson(payload, headers));
    }

    private GraphQLResponse postRequest(HttpEntity<Object> request) {
        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, request, String.class);
        return new GraphQLResponse(response);
    }

    static HttpEntity<Object> forJson(String json, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(json, headers);
    }


}
