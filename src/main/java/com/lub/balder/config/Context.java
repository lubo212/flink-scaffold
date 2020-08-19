package com.lub.balder.config;

import com.google.gson.Gson;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.base.Preconditions;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.ImmutableMap;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Context implements Serializable {
    private Map<String,String> parameters;

    public Context(){
        parameters = Collections.synchronizedMap(new HashMap<String, String>());
    }

    public Context(Map<String, String> paramters) {
        this();
        this.putAll(paramters);
    }

    public void putAll(Map<String, String> map) {
        parameters.putAll(map);
    }

    public ImmutableMap<String, String> getParameters() {
        synchronized (parameters) {
            return ImmutableMap.copyOf(parameters);
        }
    }

    public void clear() {
        parameters.clear();
    }


    public ImmutableMap<String, String> getSubProperties(String prefix) {
        Preconditions.checkArgument(prefix.endsWith("."),
                "The given prefix does not end with a period (" + prefix + ")");
        Map<String, String> result = Maps.newHashMap();
        synchronized (parameters) {
            for (String key : parameters.keySet()){
                if (key.startsWith(prefix)){
                    String name = key.substring(prefix.length());
                    result.put(name,parameters.get(key));
                }
            }
        }
        return ImmutableMap.copyOf(result);
    }

    public void put(String key, String value) {
        parameters.put(key, value);
    }

    public boolean containsKey(String key) {
        return parameters.containsKey(key);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = get(key);
        if (value != null) {
            return Boolean.parseBoolean(value.trim());
        }
        return defaultValue;
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        String value = get(key);
        if (value != null) {
            return Integer.parseInt(value.trim());
        }
        return defaultValue;
    }


    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        String value = get(key);
        if (value != null) {
            return Long.parseLong(value.trim());
        }
        return defaultValue;
    }


    public Long getLong(String key) {
        return getLong(key, null);
    }



    public String getString(String key, String defaultValue) {
        return get(key, defaultValue);
    }

    public String getString(String key) {
        return get(key);
    }


    private String get(String key) {
        return get(key, null);
    }

    private String get(String key, String defaultValue) {
        String result = parameters.get(key);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
