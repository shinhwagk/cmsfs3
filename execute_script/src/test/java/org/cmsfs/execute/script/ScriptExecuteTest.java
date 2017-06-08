package org.cmsfs.execute.script;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ScriptExecuteTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void executeScriptText() throws Exception {
        String p = System.getProperty("os.name").toLowerCase();
        File file = null;
        if (p.startsWith("linux")) {
            file = new File(classLoader.getResource("test_script.sh").getFile());

        } else if (p.startsWith("win")) {
            file = new File(classLoader.getResource("test_script.ps1").getFile());
        }
        ScriptExecute se = new ScriptExecute(Arrays.asList(file.getAbsolutePath()), new HashMap<>(), Optional.empty(), Optional.empty());
        assertEquals("test", se.executeScript());

    }
}