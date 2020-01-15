package com.example.identity;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Named;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Named
public class GraphQLTestHelper {

    private ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders headers = new HttpHeaders();

    @Autowired(required = false)
    private TestRestTemplate restTemplate;

    public GraphQLResponse perform(String graphqlQuery, ObjectNode variables) throws IOException {

        String payload = createJsonQuery(graphqlQuery, variables);
        return post(payload);
    }

    public GraphQLResponse perform(String graphqlQuery, ObjectNode variables, UUID sessionUuid) throws IOException {

        String payload = createJsonQuery(graphqlQuery, variables);
        return post(payload, sessionUuid);
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

    private GraphQLResponse post(String payload, UUID sessionUuid) {
        return postRequest(forJson(payload, headers, sessionUuid));
    }

    private GraphQLResponse postRequest(HttpEntity<Object> request) {
        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, request, String.class);
        System.out.println(request);
        System.out.println(response);
        return new GraphQLResponse(response);
    }

    static HttpEntity<Object> forJson(String json, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(json, headers);
    }


    static HttpEntity<Object> forJson(String json, HttpHeaders headers, UUID sessionUuid) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Cookie", "JSESSIONID=" + sessionUuid);
        return new HttpEntity<>(json, headers);
    }
}
