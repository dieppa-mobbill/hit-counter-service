package com.mobbill.hitcounter.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by antoniop on 22/04/15.
 */
public class MobbillHitCounter implements Serializable{



    private Timestamp timeStamp;

    private long counterValue;


    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(long counterValue) {
        this.counterValue = counterValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobbillHitCounter that = (MobbillHitCounter) o;

        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return timeStamp != null ? timeStamp.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MobbillHitCounter{" +
                "timeStamp=" + timeStamp +
                ", counterValue=" + counterValue +
                '}';
    }
}
