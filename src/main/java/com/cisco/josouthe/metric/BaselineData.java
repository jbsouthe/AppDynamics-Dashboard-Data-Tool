package com.cisco.josouthe.metric;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaselineData {
    public long metricId;
    public String metricName, frequency;
    public int granularityMinutes;
    public List<BaselineTimeslice> dataTimeslices;
    public String controllerHostname;
    public String applicationName;
    public Baseline baseline;

    public MetricValue getTimeSlice( long timestamp ) {
        for( BaselineTimeslice baselineTimeslice : dataTimeslices )
            if( timestamp == baselineTimeslice.startTime ) return baselineTimeslice.metricValue;
        return null;
    }

    public long purgeNullBaselineTimeslices() {
        if( dataTimeslices == null || dataTimeslices.isEmpty() ) return 0;
        long counter=0;
        synchronized (this.dataTimeslices) {
            List<BaselineTimeslice> newDataTimeslices = new ArrayList<>();
            Iterator<BaselineTimeslice> it = dataTimeslices.iterator();
            while(it.hasNext()) {
                BaselineTimeslice baselineTimeslice = it.next();
                if( baselineTimeslice.metricValue != null ) {
                    newDataTimeslices.add(baselineTimeslice);
                } else {
                    counter++;
                }
            }
            this.dataTimeslices.clear();
            this.dataTimeslices.addAll(newDataTimeslices);
        }
        return counter;
    }

    public boolean hasData() {
        return dataTimeslices != null && dataTimeslices.size() > 0;
    }
}
