package com.example.identity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import com.example.identity.util.BigDecimalSerializer;
import com.example.identity.util.InstantSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
public abstract class ResolverTest {


    private static ObjectMapper jsonMapper = configure();

    @Inject protected MockMvc mvc;

    protected MvcResult postGraphQL(String query, Map<String, Object> variables) throws Exception {
        return mvc.perform(post("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .content(generateRequest(query, variables)))
            .andExpect(request().asyncStarted())
            .andReturn();
    }

    protected MvcResult postGraphQL(String query, Map<String, ?> variables, Cookie cookie) throws Exception {
        return mvc.perform(post("/graphql")
            .cookie(cookie)
            .contentType(MediaType.APPLICATION_JSON)
            .content(generateRequest(query, variables)))
            .andExpect(request().asyncStarted())
            .andReturn();
    }

    protected MvcResult postGraphQL(String query, Map<String, Object> variables, HttpHeaders headers) throws Exception {
        return mvc.perform(post("/graphql")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(generateRequest(query, variables)))
            .andExpect(request().asyncStarted())
            .andReturn();
    }

    protected String generateRequest(String query, Map<String, ?> variables) throws JSONException {
        Map<String, Object> body = new HashMap<>();
        body.put("query", query);
        if (variables != null) {
            body.put("variables", variables);
        }
        try {
            return writeValue(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String generateSuccess(Object... keyValuePairs) throws IOException {
        return writeValue(map("data", map(keyValuePairs)));
    }

    protected Map<String, Object> map(Object... keyValuePairs) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            map.put((String)keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return map;
    }

    public static String writeValue(final Object json) throws IOException {
        return jsonMapper.writeValueAsString(json);
    }

    private static ObjectMapper configure() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new SimpleModule()
                .addSerializer(Instant.class, new InstantSerializer())
                .addSerializer(BigDecimal.class, new BigDecimalSerializer()))
            .disable(SerializationFeature.INDENT_OUTPUT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
