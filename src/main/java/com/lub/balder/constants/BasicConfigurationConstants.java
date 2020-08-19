/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.lub.balder.constants;

public final class BasicConfigurationConstants {

  public static final String CONFIG_SOURCES = "sources";
  public static final String CONFIG_SOURCES_PREFIX = CONFIG_SOURCES + ".";
  public static final String CONFIG_SOURCE_CHANNELSELECTOR_PREFIX = "selector.";

  public static final String CONFIG_SINKS = "sinks";
  public static final String CONFIG_SINKS_PREFIX = CONFIG_SINKS + ".";
  public static final String CONFIG_SINK_PROCESSOR_PREFIX = "processor.";

  public static final String CONFIG_SINKGROUPS = "sinkgroups";
  public static final String CONFIG_SINKGROUPS_PREFIX = CONFIG_SINKGROUPS + ".";

  public static final String CONFIG_CHANNEL = "channel";
  public static final String CONFIG_CHANNELS = "channels";
  public static final String CONFIG_CHANNELS_PREFIX = CONFIG_CHANNELS + ".";

  public static final String CONFIG_Flink = "flink";
  public static final String CONFIG_FLINKS = "flinks";
  public static final String CONFIG_FLINKS_PREFIX = CONFIG_FLINKS + ".";


  public static final String CONFIG_COMPONENT = "component";
  public static final String CONFIG_COMPONENTS = "components";
  public static final String CONFIG_COMPONENTS_PREFIX = CONFIG_FLINKS + ".";

  public static final String PARTITION_STRATEGY_SHUFFLE =  "shuffle";
  public static final String PARTITION_STRATEGY_REBALANCE =  "rebalance";


  public static final String CONFIG_CONFIG = "config";
  public static final String CONFIG_PARSE = "parse";
  public static final String CONFIG_TYPE = "type";
  public static final String CONFIG_NEXT = "next";
  public static final String CONFIG_HANDLER = "handler";
  public static final String CONFIG_EVENT_TIME = "eventTime";



  public static final String ENABLE_CHECKPOINT_POINTING="enableCheckPointing";
  public static final String MIN_PAUSE_BETWEEN_CHECKPOINTS="minPauseBetweenCheckpoints";
  public static final String MAX_CONCURRENT_CHECKPOINT="maxConcurrentCheckpoint";
  public static final String FAIL_ON_CHECKPOINT_ERROR="failOnCheckPointingErrors";
  public static final String USE_SNAPSHOT_COMPRESSION="useSnapshotCompression";
  public static final String ENABLE_EXTERNALIZED_CHECKPOINTS="enableExternalizedCheckPoints";
  public static final String ENABLE_EXTERNALIZED_CHECKPOINTS_RETAIN_ON_CANCELLATION="RETAIN_ON_CANCELLATION";
  public static final String ENABLE_EXTERNALIZED_CHECKPOINTS_DELETE_ON_CANCELLATION="DELETE_ON_CANCELLATION";


  public static final String PARALLELISM="parallelism";

  public static final String TIME_CHARACTERISTIC="timeCharacteristic";

  public static final String CHECKPOINT_DIR="checkpointDir";
//  public static final String TIME_CHARACTERISTIC_SCHEMA="timeCharacteristicSchema";

  private BasicConfigurationConstants() {
    // disable explicit object creation
  }

}
