package com.cisco.josouthe.metric;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MetricData {
    public MetricData() {}

    public MetricData( String name, String urlString) throws MalformedURLException {
        this.url = new URL(urlString);
        this.hostname = url.getHost();
        this.metricName = name;
    }

    public long metricId, applicationId;
    public String metricName, metricPath, frequency, hostname;
    public transient URL url;
    public List<MetricValue> metricValues;
    public String controllerHostname;
    public String applicationName;
}
