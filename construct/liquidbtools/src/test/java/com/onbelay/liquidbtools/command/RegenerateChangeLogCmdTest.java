package com.onbelay.liquidbtools.command;

import com.onbelay.liquidbtools.test.DbScriptsSpringTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

public class RegenerateChangeLogCmdTest extends DbScriptsSpringTestCase {

    @Autowired
    private RegenerateChangeLogCmd regenerateChangeLogCmd;


    @Test
    public void readInVariables() throws Exception {

        try (InputStream inputStream = getClass().getResourceAsStream("/example.xml")) {
            regenerateChangeLogCmd.run(inputStream);
        }

    }
}
