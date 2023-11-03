package com.onbelay.liquidbtools.test;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@ComponentScan(basePackages = {"com.onbelay.*"})
@EntityScan(basePackages = { "com.onbelay.*"})
@TestPropertySource( locations= "classpath:application-integrationtest.properties")
@SpringBootTest
@Disabled("Do not run *TestCase classes with JUnit")
public class DbScriptsSpringTestCase {
}
