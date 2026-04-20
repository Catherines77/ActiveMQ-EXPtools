package cc.kiiy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
    
    private static final String ENV_FILE = "environments.properties";
    
    public static Map<String, String> loadEnvironments() {
        Map<String, String> environments = new LinkedHashMap<>();
        try {
            File file = new File(ENV_FILE);
            if (file.exists()) {
                Properties envProps = new Properties();
                FileInputStream fis = new FileInputStream(file);
                envProps.load(fis);
                fis.close();
                
                for (String key : envProps.stringPropertyNames()) {
                    environments.put(key, envProps.getProperty(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return environments;
    }
    
    public static void saveEnvironments(Map<String, String> environments) {
        try {
            File file = new File(ENV_FILE);
            FileOutputStream fos = new FileOutputStream(file);
            Properties envProps = new Properties();
            for (Map.Entry<String, String> entry : environments.entrySet()) {
                envProps.setProperty(entry.getKey(), entry.getValue());
            }
            envProps.store(fos, "Environment Configurations");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
