package com.lub.balder.config.provider;

import com.lub.balder.config.AbstractConfigurationProvider;
import com.lub.balder.config.FlinkConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class PropertiesObjectConfigurationProvider extends AbstractConfigurationProvider {
    private static final Logger logger = LoggerFactory
            .getLogger(PropertiesFileConfigurationProvider.class);
    private final Properties properties;

    public PropertiesObjectConfigurationProvider( Properties properties) {
        this.properties = properties;
    }


    @Override
    protected FlinkConfiguration getFlinkConfiguration() {
        logger.info("properties : ");
        for (String propertiesName :  properties.stringPropertyNames()) {
            logger.info("name = " + propertiesName + " , value = " + properties.stringPropertyNames());
        }
        return new FlinkConfiguration(toMap(properties));
    }

}
