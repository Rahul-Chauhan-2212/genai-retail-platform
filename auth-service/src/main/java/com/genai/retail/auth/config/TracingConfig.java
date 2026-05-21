package com.genai.retail.auth.config;

import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ThreadLocalAccessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    @PostConstruct
    public void setup() {

        ContextRegistry.getInstance()
                .registerThreadLocalAccessor(
                        new MdcThreadLocalAccessor()
                );
    }

    static class MdcThreadLocalAccessor
            implements ThreadLocalAccessor<String> {

        @Override
        public Object key() {
            return "trace-mdc";
        }

        @Override
        public String getValue() {
            return MDC.get("traceId");
        }

        @Override
        public void setValue(String value) {

            if (value != null) {
                MDC.put("traceId", value);
            }
        }

        @Override
        public void reset() {
            MDC.clear();
        }
    }
}