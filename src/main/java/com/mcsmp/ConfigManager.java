package com.mcsmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class ConfigManager {
    private Map<String, Object> config;

    public ConfigManager(String configPath) {
        try (FileInputStream fis = new FileInputStream(new File(configPath))) {
            Yaml yaml = new Yaml();
            config = yaml.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return (String) config.get(path);
    }

    public int getInt(String path) {
        return (int) config.get(path);
    }

    // Other data types
}
