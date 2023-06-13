package com.cisco.josouthe.output;

import com.cisco.josouthe.metric.BaselineData;
import com.cisco.josouthe.metric.MetricData;

import java.io.PrintStream;

public class XMLOutputPrinter implements OutputPrinter {
    MetricData metricData;
    BaselineData baselineData;

    public XMLOutputPrinter() {

    }

    @Override
    public void setMetricData(MetricData metricData) {
        this.metricData=metricData;
    }

    @Override
    public void setBaseLineData(BaselineData baseLineData) {
        this.baselineData=baseLineData;
    }

    @Override
    public void print(PrintStream printStream) {

    }
}
