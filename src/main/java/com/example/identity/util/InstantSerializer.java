package com.example.identity.util;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InstantSerializer extends StdSerializer<Instant> {

    public static final DateTimeFormatter ISO_8601_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
    private static final ZoneId GMT = ZoneId.of("GMT");

    public InstantSerializer() {
        super(Instant.class);
    }

    @Override
    public void serialize(final Instant value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeString(ISO_8601_FORMAT.format(value.atZone(GMT)));
    }
}
