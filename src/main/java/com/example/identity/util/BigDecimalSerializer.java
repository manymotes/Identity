package com.example.identity.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author rob
 *
 */
public class BigDecimalSerializer extends StdSerializer<BigDecimal> {

    public BigDecimalSerializer() {
        super(BigDecimal.class);
    }

    @Override
    public void serialize(final BigDecimal value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        try {
            jgen.writeNumber(value.longValueExact());
        } catch (ArithmeticException ex) {
            jgen.writeNumber(value.stripTrailingZeros().toPlainString());
        }
    }
}
