package com.lub.balder.config.provider;

import com.lub.balder.config.AbstractConfigurationProvider;
import com.lub.balder.config.FlinkConfiguration;
import org.apache.flink.util.FlinkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesFileConfigurationProvider extends AbstractConfigurationProvider {
    private static final Logger logger = LoggerFactory
            .getLogger(PropertiesFileConfigurationProvider.class);
    private static final String DEFAULT_PROPERTIES_IMPLEMENTATION = "java.util.Properties";
    private final File file;

    public PropertiesFileConfigurationProvider(String filePath) throws FlinkException {
        this(new File(filePath));
    }


    public PropertiesFileConfigurationProvider( File file) throws FlinkException {
        if (!file.exists()){
            throw new FlinkException("配置文件不存在");
        }
        this.file = file;
    }

    @Override
    protected FlinkConfiguration getFlinkConfiguration() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String resolverClassName = System.getProperty("propertiesImplementation",
                    DEFAULT_PROPERTIES_IMPLEMENTATION);
            Class<? extends Properties> propsclass = Class.forName(resolverClassName).asSubclass(Properties.class);
            Properties properties = propsclass.newInstance();
            properties.load(reader);
            return new FlinkConfiguration(toMap(properties));
        } catch (IOException ex) {
            logger.error("Unable to load file:" + file
                    + " (I/O failure) - Exception follows.", ex);
        } catch (ClassNotFoundException e) {
            logger.error("Configuration resolver class not found", e);
        } catch (InstantiationException e) {
            logger.error("Instantiation exception", e);
        } catch (IllegalAccessException e) {
            logger.error("Illegal access exception", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.warn(
                            "Unable to close file reader for file: " + file, e);
                }
            }
        }
        return new FlinkConfiguration(new HashMap<>());
    }

}
