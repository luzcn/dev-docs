FROM java:8

ENV VERSION 0.10
ENV JAR jmx_prometheus_httpserver-$VERSION-jar-with-dependencies.jar


RUN mkdir -p /opt/jmx_exporter
RUN curl -L https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_httpserver/$VERSION/$JAR -o /opt/jmx_exporter/$JAR

COPY start.sh  /opt/jmx_exporter/
COPY config.yml  /opt/jmx_exporter/

RUN chmod +x  /opt/jmx_exporter/start.sh

# VOLUME ["/etc/jmx_exporter"]

CMD /opt/jmx_exporter/start.sh

EXPOSE 5556
