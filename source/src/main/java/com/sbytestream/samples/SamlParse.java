package com.sbytestream.samples;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class SamlParse {
    public static void help() {
        System.out.println("Syntax:");
        System.out.println("java -jar samlparse <settings file> <saml response xml file>");
        System.out.println("java -jar samlparse <saml response xml file>");
        System.out.println("Note: In case path to the settings file is not given, an environment variable named " + ENV_SETTINGS_FILE +
                " should point to the settings file.");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            help();
            return;
        }

        try {
            String settingsFile;
            String inputFile;

            if (args.length == 1) {
                settingsFile = System.getenv(ENV_SETTINGS_FILE);
                if (settingsFile == null || settingsFile == "") {
                    System.out.println(ENV_SETTINGS_FILE + " environment variable does not point to a valid settings file.");
                    return;
                }
                inputFile = args[0];
                System.out.println("Settings file: " + settingsFile);
            }
            else {
                settingsFile = args[0];
                if (!new File(settingsFile).exists()) {
                    System.out.printf("%s not found\n", settingsFile);
                    return;
                }
                inputFile = args[1];
            }

            if (!new File(inputFile).exists()) {
                System.out.printf("%s not found\n", inputFile);
                return;
            }

            SamlSettings settings = new SamlSettings();
            settings.load(settingsFile);

            String samlContent = new String(Files.readAllBytes(Paths.get(inputFile)));
            SamlResponse samlResponse = SamlResponse.parse(settings, samlContent);
            Map<String, String> result = samlResponse.getAttributes();

            if (result != null) {
                for (String key : result.keySet()) {
                    System.out.printf("%s=%s\n", key, result.get(key) == null ? "null" : result.get(key));
                }
            }
        }
        catch(Exception e) {
            System.out.println("Something went terribly wrong!");
            System.out.println(e.getMessage());
        }
    }

    private final static String ENV_SETTINGS_FILE = "SAML_SETTINGS_FILE";
}
