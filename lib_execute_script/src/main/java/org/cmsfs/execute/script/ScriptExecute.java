package org.cmsfs.execute.script;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ScriptExecute {

    final String workspace = "workspace";

    final long workNumber;
    final String workFolder;

    final String currentPath = System.getProperty("user.dir");

    final List<String> files;
    final Map<String, String> env;
    final Optional<String> dataOpt;
    final Optional<String> argsOpt;

    public ScriptExecute(List<String> files, Map<String, String> env, Optional<String> dataOpt, Optional<String> argsOpt) {
        this.workNumber = ThreadLocalRandom.current().nextLong(100000000);
        this.workFolder = currentPath + "/" + workspace + "/" + workNumber;
        this.files = files;
        this.env = env;
        this.dataOpt = dataOpt;
        this.argsOpt = argsOpt;
    }

    public ScriptExecute(List<String> files, Map<String, String> env) {
        this(files, env, Optional.empty(), Optional.empty());
    }

    public ScriptExecute(List<String> files, Optional<String> data, Optional<String> args) {
        this(files, new HashMap<>(), data, args);
    }

    public ScriptExecute(List<String> files) {
        this(files, new HashMap<>(), Optional.empty(), Optional.empty());
    }

    public ScriptExecute(List<String> files, Optional<String> data) {
        this(files, new HashMap<>(), data, Optional.empty());
    }

    void writeFile(String content, String file) {
        try {
            FileUtils.writeStringToFile(new File(file), content, Charset.forName("UTF-8"), false);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public String start() throws Exception {

        String mainFile = files.get(0);

        List<String> executor = executorChoice(mainFile);

        List<String> commands = new ArrayList<>();

        executor.forEach(commands::add);

        createWorkDir();

        files.forEach(this::downScript);

        commands.add(mainFile);

        dataOpt.ifPresent(data -> {
            writeFile(data, this.workFolder + "/" + "data.json");
            commands.add("data.json");
        });

        argsOpt.ifPresent(data -> {
            writeFile(data, this.workFolder + "/" + "args.json");
            commands.add("args.json");
        });


        String[] ss = new String[]{"a", "b", "c"};
        ProcessBuilder pb = new ProcessBuilder(commands)
                .directory(new File(this.workFolder));
        Map<String, String> pbEnv = pb.environment();
        env.forEach((k, v) -> pbEnv.put(k, v));

        Process p = pb.start();

        String line;
        List<String> lines = new ArrayList<>();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = r.readLine()) != null) {
            lines.add(line);
        }
        r.close();

        try {
            return String.join("\n", lines);
        } finally {
            FileUtils.forceDeleteOnExit(new File(this.workFolder));
        }
    }

    void createWorkDir() throws IOException {
        String workDirPath = this.workspace + "/" + this.workNumber;
        FileUtils.forceMkdir(new File(workDirPath));
    }

    void downScript(String sFile) {
        System.out.println(sFile);
        try {
            File sourceFile = new File(sFile);
            File targetFile = new File(this.workFolder + "/" + sourceFile.getName());
            FileUtils.copyURLToFile(sourceFile.toURI().toURL(), targetFile);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    List<String> executorChoice(String mainFile) throws Exception {
        String[] p = mainFile.split("\\.");
        String executer = p[p.length - 1];

        switch (executer) {
            case "py":
                return Arrays.asList("python3");
            case "sh":
                return Arrays.asList("sh");
            case "ps1":
                return Arrays.asList("powershell", "-file");
            default:
                throw new Exception("actuator unknown: " + executer);
        }
    }
}
