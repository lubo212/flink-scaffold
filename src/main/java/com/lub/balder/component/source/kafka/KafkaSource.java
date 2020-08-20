package com.lub.balder.component.source.kafka;

import com.lub.balder.component.source.Source;
import com.lub.balder.config.Context;
import com.lub.balder.exception.FlinkException;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class KafkaSource extends Source<String> {

    private Properties kafkaProps;

    private Context context;

    String topic;

    FlinkKafkaConsumer011<String> stringFlinkKafkaConsumer011;


    @Override
    public SourceFunction<String> getSource() {
        return stringFlinkKafkaConsumer011;
    }

    @Override
    public void init(Context context) {
        this.context = context;
        topic = getSubscriptionTopic(context);
        kafkaProps = getConsumerProps(context);
        stringFlinkKafkaConsumer011 = new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(), kafkaProps);

        Long beginTime = context.getLong("fetch.timestamp", -1L);
        if (beginTime > 0) {
            stringFlinkKafkaConsumer011.setStartFromTimestamp(beginTime);
        }
    }

    private String getSubscriptionTopic(Context context) {
        String topicProperty = context.getString(KafkaSourceConstants.TOPICS);
        if (topicProperty == null || topicProperty.isEmpty()) {
            throw new FlinkException("Regex cannot be null");
        }
        return topicProperty;
    }

    private Properties getConsumerProps(Context ctx) {
        Properties kafkaProps = new Properties();
        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                KafkaSourceConstants.DEFAULT_KEY_DESERIALIZER);
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                KafkaSourceConstants.DEFAULT_VALUE_DESERIALIZER);

        //These always take precedence over config
        kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                KafkaSourceConstants.DEFAULT_AUTO_COMMIT);
        //Defaults overridden based on config
        kafkaProps.putAll(ctx.getSubProperties(KafkaSourceConstants.KAFKA_CONSUMER_PREFIX));
        String bootstrapServers  = ctx.getString(KafkaSourceConstants.BOOTSTRAP_SERVERS);
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return kafkaProps;
    }
}
