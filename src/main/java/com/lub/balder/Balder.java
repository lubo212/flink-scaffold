package com.lub.balder;

import com.lub.balder.component.source.kafka.KafkaSource;
import com.lub.balder.config.Context;
import com.lub.balder.config.provider.PropertiesFileConfigurationProvider;
import com.lub.balder.constants.BasicConfigurationConstants;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.FlinkException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Balder {

    private static Map<String, Context> contextMap;


    private StreamExecutionEnvironment creatingEnvironment(Context flinkContext) {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        if (flinkContext.getInteger(BasicConfigurationConstants.ENABLE_CHECKPOINT_POINTING) != null) {
            int enableCheckPointing = flinkContext.getInteger(BasicConfigurationConstants.ENABLE_CHECKPOINT_POINTING);
            executionEnvironment.enableCheckpointing(enableCheckPointing, CheckpointingMode.AT_LEAST_ONCE);
            CheckpointConfig checkpointConfig = executionEnvironment.getCheckpointConfig();
            checkpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
            //设置checkpoint位置
            if (flinkContext.getString(BasicConfigurationConstants.CHECKPOINT_DIR) != null) {
                String checkpointDir = flinkContext.getString(BasicConfigurationConstants.CHECKPOINT_DIR);
                Path path = Paths.get(checkpointDir);
                executionEnvironment.setStateBackend(new FsStateBackend(path.toString()));
            }
            if (flinkContext.getInteger(BasicConfigurationConstants.PARALLELISM) != null) {
                int parallelism = flinkContext.getInteger(BasicConfigurationConstants.PARALLELISM);
                executionEnvironment.setParallelism(parallelism);
            }
        }
        return executionEnvironment;
    }


    public  StreamExecutionEnvironment handler(StreamExecutionEnvironment executionEnvironment) {
        KafkaSource kafkaSource = new KafkaSource();
        kafkaSource.init(contextMap.get("source"));
        executionEnvironment.addSource(kafkaSource.getSource());
        return executionEnvironment;
    }


    public static void main(String[] args) throws Exception {
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String configPath = parameterTool.get("config-path");
        contextMap = new PropertiesFileConfigurationProvider(configPath).getContext();
        Balder balder = new Balder();
        Context flinkContext = contextMap.get("flink");
        StreamExecutionEnvironment executionEnvironment = balder.creatingEnvironment(flinkContext);
        balder.handler(executionEnvironment).execute(flinkContext.getString("run.job-name"));
    }

}
