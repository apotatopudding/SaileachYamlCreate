package org.example;

import java.util.ArrayList;
import java.util.List;

public class CronTemp {

    public List<Integer> appointList = new ArrayList<>();//只用于周的指定部分统计
    public Integer cycleMinRange;
    public Integer cycleMaxRange;
    public Integer fromMinRange;
    public Integer fromMaxRange;
    public String appoint;
    public Integer LatestWorkDay;
    public Integer specialInDay;
    public Integer specialInWeek;
    public Integer latestDay;

    public Integer getCycleMinRange() {
        return cycleMinRange;
    }

    public void setCycleMinRange(Integer cycleMinRange) {
        this.cycleMinRange = cycleMinRange;
    }

    public Integer getCycleMaxRange() {
        return cycleMaxRange;
    }

    public void setCycleMaxRange(Integer cycleMaxRange) {
        this.cycleMaxRange = cycleMaxRange;
    }

    public Integer getFromMinRange() {
        return fromMinRange;
    }

    public void setFromMinRange(Integer fromMinRange) {
        this.fromMinRange = fromMinRange;
    }

    public Integer getFromMaxRange() {
        return fromMaxRange;
    }

    public void setFromMaxRange(Integer fromMaxRange) {
        this.fromMaxRange = fromMaxRange;
    }

    public String getAppoint() {
        return appoint;
    }

    public void setAppoint(String appoint) {
        this.appoint = appoint;
    }

    public Integer getLatestWorkDay() {
        return LatestWorkDay;
    }

    public void setLatestWorkDay(Integer latestWorkDay) {
        LatestWorkDay = latestWorkDay;
    }

    public Integer getSpecialInDay() {
        return specialInDay;
    }

    public void setSpecialInDay(Integer specialInDay) {
        this.specialInDay = specialInDay;
    }

    public Integer getSpecialInWeek() {
        return specialInWeek;
    }

    public void setSpecialInWeek(Integer specialInWeek) {
        this.specialInWeek = specialInWeek;
    }

    public Integer getLatestDay() {
        return latestDay;
    }

    public void setLatestDay(Integer latestDay) {
        this.latestDay = latestDay;
    }
}
