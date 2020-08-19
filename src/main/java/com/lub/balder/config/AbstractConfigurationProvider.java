package com.lub.balder.config;

import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractConfigurationProvider implements ConfigurationProvider {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConfigurationProvider.class);

    public AbstractConfigurationProvider() {
        super();
    }

    protected abstract FlinkConfiguration getFlinkConfiguration();

    public Map<String, Context> getContext() {
        Map<String, Context> componentAwareMap = Maps.newHashMap();
        FlinkConfiguration fconfig = getFlinkConfiguration();
        FlinkConfiguration.AgentConfiguration agentConf = fconfig.getAgentConfiguration();
        if (agentConf != null) {
            return agentConf.getComponentContextMap();
        }
        return componentAwareMap;
    }

    protected Map<String, String> toMap(Properties properties) {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String value = properties.getProperty(name);
            result.put(name, value);
        }
        return result;
    }



}
