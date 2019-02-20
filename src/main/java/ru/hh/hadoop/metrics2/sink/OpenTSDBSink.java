package ru.hh.hadoop.metrics2.sink;

import cn.ennwifi.opentsdb.HttpClientImpl;
import cn.ennwifi.opentsdb.builder.put.DataPointBuild;
import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.metrics2.AbstractMetric;
import org.apache.hadoop.metrics2.MetricsException;
import org.apache.hadoop.metrics2.MetricsRecord;
import org.apache.hadoop.metrics2.MetricsSink;

import java.net.InetAddress;
import java.net.UnknownHostException;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public class OpenTSDBSink implements MetricsSink {
    private static final String OPENTSDB_URL = "opentsdb_url";
    private static final String METRICS_PREFIX = "metrics_prefix";
    private HttpClientImpl client = null;
    private String hostname = null;
    private String metricsPrefix = null;


    public void setClient(HttpClientImpl client) {
        this.client = client;
    }

    public void putMetrics(MetricsRecord record) {
        DataPointBuild build = DataPointBuild.getInstance();
        StringBuilder metricsPathPrefix = new StringBuilder();

        metricsPathPrefix.append(metricsPrefix).append(".")
                .append(record.context()).append(".").append(record.name());

        for (AbstractMetric metric : record.metrics()) {
            if (hostname != null) {
                build.addDataPoint(metricsPathPrefix.toString() + "." + metric.name())
                        .setValue(metric.value().intValue(), (int) (System.currentTimeMillis() / 1000))
                        .addTag("host", hostname);
            }

        }

        try {
            client.put(build);
        } catch (Exception e) {
            throw new MetricsException("Error sending metrics", e);
        }
    }

    public void flush() {}

    public void init(SubsetConfiguration conf) {
        String opentsdbUrl = conf.getString(OPENTSDB_URL);

        try {
            String result = InetAddress.getLocalHost().getHostName();
            if (StringUtils.isNotEmpty( result))
                hostname = result;
        } catch (UnknownHostException e) {
            throw new MetricsException("Error getting hostname, " + e);
        }

        metricsPrefix = conf.getString(METRICS_PREFIX);
        if (metricsPrefix == null) {
            metricsPrefix = "";
        }

        try {
            HttpClientImpl client = new HttpClientImpl(opentsdbUrl);
            setClient(client);
        } catch (Exception e) {
            throw new MetricsException("Error creating connection, " + opentsdbUrl, e);
        }
    }
}
