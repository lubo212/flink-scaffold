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
package com.lub.balder.exception;

public class FlinkConfigurationError {
  private String key;
  private final FlinkConfigurationErrorType errorType;
  private ErrorOrWarning error;

  /**
   * Component which had an error, specific key in error(which can be null)
   *
   * @param key
   * @param error
   */
  public FlinkConfigurationError( String key,
                                 FlinkConfigurationErrorType error, ErrorOrWarning err) {
    this.error = err;
    if (key != null) {
      this.key = key;
    } else {
      this.key = "";
    }
    this.errorType = error;

  }

  public String getKey() {
    return key;
  }

  public FlinkConfigurationErrorType getErrorType() {
    return errorType;
  }

  public ErrorOrWarning getErrorOrWarning() {
    return error;
  }

  public enum ErrorOrWarning {
    ERROR,
    WARNING;
  }
}
