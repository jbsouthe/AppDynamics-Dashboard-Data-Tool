package com.cisco.josouthe.output;

import com.cisco.josouthe.metric.BaselineData;
import com.cisco.josouthe.metric.MetricData;

import java.io.PrintStream;

public interface OutputPrinter {
    public void setMetricData( MetricData metricData );
    public void setBaseLineData(BaselineData baseLineData );
    public void print(PrintStream printStream);
}
