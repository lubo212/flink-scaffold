package com.lub.balder.component.source;

import com.lub.balder.component.Component;
import com.lub.balder.config.Context;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.Serializable;

public abstract class Source<T>  implements Serializable, Component {

    private Context context;

    public Source() {
    }

    public abstract SourceFunction<T> getSource();

}
