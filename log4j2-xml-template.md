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


### Use MDC
```
# https://logging.apache.org/log4j/2.x/manual/configuration.html#ConfigurationSyntax
name = LoggerConfig

# https://logging.apache.org/log4j/2.x/manual/appenders.html#ConsoleAppender
# https://logging.apache.org/log4j/2.x/manual/layouts.html#PatternLayout
appender.stdout.type = Console
appender.stdout.name = STDOUT
appender.stdout.layout.type = PatternLayout
appender.stdout.layout.pattern = %level | %msg%n

appender.stdout_mdc.type = Console
appender.stdout_mdc.name = STDOUT_MDC
appender.stdout_mdc.layout.type = PatternLayout
appender.stdout_mdc.layout.pattern = %level | eventId=%mdc{eventId} correlationId=%mdc{correlationId} entityId=%mdc{entityId} eventName=%mdc{eventName} sources=%mdc{sources} facilityId=%mdc{facilityId} | %msg%n

# https://logging.apache.org/log4j/2.x/manual/configuration.html#Properties
logger.nordstrom.name = com.nordstrom
logger.nordstrom.level = info
logger.nordstrom.additivity = false
logger.nordstrom.appenderRef.stdout.ref = STDOUT_MDC

# https://stackoverflow.com/a/23877814/1429373
rootLogger.level = INFO
rootLogger.appenderRef.stdout.ref = STDOUT
```
