### File appender example
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Properties>
        <Property name="APP_NAME">wm-event-receiver</Property>

        <!--Sets the root logger level -->
        <Property name="LOGGER_LEVEL">INFO</Property>

        <!--Async every 200 events unless mem usages is low-->
        <Property name="LOGGER_SIZE">200</Property>

        <!--Force rollover every 9 MBs -->
        <Property name="LOGGER_ROLLOVER_SIZE">9</Property>

        <!--Force rollover every 6 hours default-->
        <Property name="LOGGER_ROLLOVER_TIME">6</Property>

        <Property name="LOGGER_PATH">/usr/local/tomcat/logs/</Property>
    </Properties>

    <Appenders>
        <RollingRandomAccessFile
            append="false"
            fileName="${LOGGER_PATH}${APP_NAME}.log"
            filePattern="${LOGGER_PATH}${APP_NAME}-%d{yyyy-MM-dd}-%i-log.gz"
            immediateFlush="false"
            name="${APP_NAME}"
        >
            <PatternLayout>
                <Pattern>{ "timestamp":"%d{yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}", "message":%m }%n</Pattern>
            </PatternLayout>

            <Policies>
                <TimeBasedTriggeringPolicy interval="${LOGGER_ROLLOVER_TIME}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${LOGGER_ROLLOVER_SIZE} MB"/>
            </Policies>

            <DefaultRolloverStrategy max="6">
                <Delete basePath="${LOGGER_PATH}" maxDepth="2">
                    <IfFileName glob="*${APP_NAME}*.log.gz" />
                    <IfLastModified age="2d" />
                </Delete>
            </DefaultRolloverStrategy>
        </RollingRandomAccessFile>

        <Async name="Async">
            <AppenderRef ref="${APP_NAME}"/>
            <bufferSize>${LOGGER_SIZE}</bufferSize>
        </Async>
    </Appenders>

    <Loggers>
        <Root level="DEBUG">
            <AppenderRef ref="Async"/>
        </Root>
    </Loggers>
</Configuration>
```

## Console Appender example
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
  <Appenders>
    <Console name="STDOUT" target="SYSTEM_OUT">
      <PatternLayout pattern="%d{yyyy-MM-dd'T'HH:mm:ss:SSSSSSX} [%t] %-5level %logger{36} - %msg%n" />
    </Console>
  </Appenders>
  <Loggers>
    <Logger name="org.apache.kafka" level="ERROR">
      <AppenderRef ref="STDOUT"/>
    </Logger>
    <Logger name="io.confluent.kafka.serializers.KafkaAvroDeserializerConfig" level="ERROR">
      <AppenderRef ref="STDOUT"/>
    </Logger>
    <Root level="info">
      <AppenderRef ref="STDOUT"/>
    </Root>
  </Loggers>
</Configuration>
```
