package com.cisco.josouthe.output;

import com.cisco.josouthe.metric.BaselineData;
import com.cisco.josouthe.metric.BaselineTimeslice;
import com.cisco.josouthe.metric.MetricData;
import com.cisco.josouthe.metric.MetricValue;

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
        printStream.println("<Metrics>");
        printStream.println(String.format("<Application>%s</Application>", metricData.applicationName));
        for( MetricValue metricValue : metricData.metricValues ) {
            printStream.print("<Metric>");
            printStream.print(String.format("<Name>%s</Name>", metricData.metricName));
            printStream.print(String.format("<Timestamp>%d</Timestamp>", metricValue.startTimeInMillis));
            printStream.print(String.format("<Value>%d</Value>", metricValue.value));
            printStream.print(String.format("<Min>%d</Min>", metricValue.min));
            printStream.print(String.format("<Max>%d</Max>", metricValue.max));
            MetricValue baseline = baselineData.getTimeSlice(metricValue.startTimeInMillis);
            if( baseline != null ) {
                printStream.print(String.format("<Average>%d</Average>", baseline.value));
                printStream.print(String.format("<StdDev>%f</StdDev>", baseline.standardDeviation));
            }
            printStream.println("</Metric>");
        }
        printStream.println("</Metrics>");
    }
}
