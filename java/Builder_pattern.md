# The Builder Pattern Java example

```java

/*
 * Copyright, 1999-2019, salesforce.com
 * All Rights Reserved
 * Company Confidential
 */

package com.salesforce.kwest.query.status;


import javax.annotation.concurrent.Immutable;
import java.time.Instant;

/**
 * @author zheng.lu
 */
@Immutable
public class PrestoQueryStatus {

    public static class Builder {
        private final String queryId;
        private String user;
        private String queryState;
        private Instant createTime;

        public Builder(String queryId) {
            this.queryId = queryId;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder queryState(String queryState) {
            this.queryState = queryState;
            return this;
        }

        public Builder createTime(Instant createTime) {
            this.createTime = createTime;
            return this;
        }

        public PrestoQueryStatus build() {
            PrestoQueryStatus prestoQueryInfo = new PrestoQueryStatus();
            prestoQueryInfo.queryId = this.queryId;
            prestoQueryInfo.user = this.user;
            prestoQueryInfo.queryState = this.queryState;
            prestoQueryInfo.createTime = this.createTime;
            return prestoQueryInfo;
        }
    }

    private PrestoQueryStatus() {

    }

    private String queryId;
    private String user;
    private String queryState;
    private Instant createTime;

    public String getQueryId() {
        return this.queryId;
    }

    public String getUser() {
        return this.user;
    }

    public String getQueryState() {
        return this.queryState;
    }

    public String getCreateTime() {
        return this.createTime.toString();
    }
}

```

- Use this pattern
```java
PrestoQueryStatus queryInfo = new PrestoQueryStatus.Builder(queryCreatedEvent.getMetadata().getQueryId())
                .queryState(queryCreatedEvent.getMetadata().getQueryState())
                .user(queryCreatedEvent.getContext().getUser())
                .createTime(queryCreatedEvent.getCreateTime())
                .build();
```
