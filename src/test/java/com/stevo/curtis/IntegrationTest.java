package com.stevo.curtis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = IntegrationTest.Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IntegrationTest {

    @Autowired
    @Qualifier("flow.input")
    private MessageChannel in;

    @Test
    public void test() {
        // TODO
        new MessagingTemplate(in).convertAndSend("foo");
    }

    @Configuration
    @EnableIntegration
    public static class Config {
        // TODO

        @Bean
        public IntegrationFlow flow() {
            return f -> f
//                    .transform("payload.toUpperCase()")
//                    .transform("@myTransformer.transform(payload)")
                    .<String, String>transform(p -> p.toUpperCase())
                    .handle(logger());
        }

        @Bean
        public MessageHandler logger() {
            return new LoggingHandler("WARN");
        }

        @Bean
        public Object myTransformer() {
            return new Object() {
                public String transform(String in) {
                    return (in + in).toLowerCase();
                }
            };
        }
    }
}
