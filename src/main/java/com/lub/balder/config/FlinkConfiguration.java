package com.lub.balder.config;

import com.lub.balder.exception.FlinkConfigurationError;
import com.lub.balder.exception.FlinkConfigurationErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FlinkConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FlinkConfiguration.class);





    private final LinkedList<FlinkConfigurationError> errors = new LinkedList<FlinkConfigurationError>();

    AgentConfiguration agentConfiguration = new AgentConfiguration(errors);

    public static final String NEWLINE = System.getProperty("line.separator", "\n");

    public static final String INDENTSTEP = "  ";

    public FlinkConfiguration(Properties properties) {
        for (Object name : properties.keySet()) {
            Object value = properties.get(name);
            if (!addRawProperty(name.toString(), value.toString())) {
                logger.warn("Configuration property ignored: " + name + " = " + value);
            }
        }
    }


    public FlinkConfiguration(Map<String, String> properties) {
        for (String name : properties.keySet()) {
            String value = properties.get(name);
            if (!addRawProperty(name, value)) {
                logger.warn("Configuration property ignored: " + name + " = " + value);
            }
        }
    }

    private boolean addRawProperty(String name, String value) {
        // Null names and values not supported
        if (name == null || value == null) {
            errors.add(new FlinkConfigurationError("",
                            FlinkConfigurationErrorType.AGENT_NAME_MISSING,
                            FlinkConfigurationError.ErrorOrWarning.ERROR));
            return false;
        }
        // Empty values are not supported
        if (value.trim().length() == 0) {
            errors.add(new FlinkConfigurationError( "",
                            FlinkConfigurationErrorType.PROPERTY_VALUE_NULL,
                            FlinkConfigurationError.ErrorOrWarning.ERROR));
            return false;
        }
        // Remove leading and trailing spaces
        name = name.trim();
        value = value.trim();

        int index = name.indexOf('.');

        // All configuration keys must have a prefix defined as agent name
        if (index == -1) {
            errors.add(new FlinkConfigurationError( "",
                            FlinkConfigurationErrorType.INVALID_PROPERTY,
                            FlinkConfigurationError.ErrorOrWarning.ERROR));
            return false;
        }


        // Configuration key must be specified for every property
        if (name.length() == 0) {
            errors.add(new FlinkConfigurationError("",
                            FlinkConfigurationErrorType.PROPERTY_NAME_NULL,
                            FlinkConfigurationError.ErrorOrWarning.ERROR));
            return false;
        }

        return agentConfiguration.addProperty(name, value);
    }


    public AgentConfiguration getAgentConfiguration() {
        return agentConfiguration;
    }

    public List<FlinkConfigurationError> getConfigurationErrors() {
        return errors;
    }


    public static class AgentConfiguration {
        private Map<String, Context> componentContextMap;
        private final List<FlinkConfigurationError> errorList;

        public AgentConfiguration(List<FlinkConfigurationError> errorList) {
            this.errorList = errorList;
            componentContextMap = new HashMap<>();
        }

        public Map<String, Context> getComponentContextMap() {
            return componentContextMap;
        }

        private boolean addProperty(String key, String value) {
            ComponentNameAndConfigKey cnck = parseConfigKey(key);

            if (cnck != null) {
                // it is a source
                String name = cnck.getComponentName();

                Context srcConf;
                srcConf = componentContextMap.get(name);
                if (srcConf == null){
                    srcConf = new Context();
                    componentContextMap.put(name,srcConf);
                }
                srcConf.put(cnck.getConfigKey(),value);
                return true;
            } else {
                logger.warn("Invalid property specified: " + key);
                errorList.add(new FlinkConfigurationError( key,
                        FlinkConfigurationErrorType.INVALID_PROPERTY, FlinkConfigurationError.ErrorOrWarning.ERROR));
                return false;
            }
        }

        private ComponentNameAndConfigKey parseConfigKey(String key) {
            int index = key.indexOf('.');

            if (index == -1) {
                return null;
            }

            String name = key.substring(0, index);
            String configKey = key.substring(index + 1);

            // name and config key must be non-empty
            if (name.length() == 0 || configKey.length() == 0) {
                return null;
            }

            return new ComponentNameAndConfigKey(name, configKey);
        }


    }


    public static class ComponentNameAndConfigKey {

        private final String componentName;
        private final String configKey;

        private ComponentNameAndConfigKey(String name, String configKey) {
            this.componentName = name;
            this.configKey = configKey;
        }

        public String getComponentName() {
            return componentName;
        }

        public String getConfigKey() {
            return configKey;
        }
    }

}
