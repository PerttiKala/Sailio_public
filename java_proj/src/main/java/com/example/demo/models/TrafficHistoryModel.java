package com.example.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class TrafficHistoryModel {
    private int tmsPointId;
    private int year;
    private int ordinalDate;
    private int hour;
    private int minute;
    private int second;
    private int oneHundredthSecond;
    private double length;
    private int lane;
    private int direction;
    private int vehicleClass;
    private int speed;
    private int faulty;
    private String totalTimeTechnical;
    private String timeIntervalTechnical;
    private String queueStartTechnical;

    public int getTmsPointId() {
        return tmsPointId;
    }

    public void setTmsPointId(int tmsPointId) {
        this.tmsPointId = tmsPointId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOrdinalDate() {
        return ordinalDate;
    }

    public void setOrdinalDate(int ordinalDate) {
        this.ordinalDate = ordinalDate;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getOneHundredthSecond() {
        return oneHundredthSecond;
    }

    public void setOneHundredthSecond(int oneHundredthSecond) {
        this.oneHundredthSecond = oneHundredthSecond;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(int vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFaulty() {
        return faulty;
    }

    public void setFaulty(int faulty) {
        this.faulty = faulty;
    }

    public String getTotalTimeTechnical() {
        return totalTimeTechnical;
    }

    public void setTotalTimeTechnical(String totalTimeTechnical) {
        this.totalTimeTechnical = totalTimeTechnical;
    }

    public String getTimeIntervalTechnical() {
        return timeIntervalTechnical;
    }

    public void setTimeIntervalTechnical(String timeIntervalTechnical) {
        this.timeIntervalTechnical = timeIntervalTechnical;
    }

    public String getQueueStartTechnical() {
        return queueStartTechnical;
    }

    public void setQueueStartTechnical(String queueStartTechnical) {
        this.queueStartTechnical = queueStartTechnical;
    }

    @Override
    public String toString() {
        return "TrafficHistoryModel{" +
               "tmsPointId=" + tmsPointId +
               ", year=" + year +
               ", ordinalDate=" + ordinalDate +
               ", hour=" + hour +
               ", minute=" + minute +
               ", second=" + second +
               ", oneHundredthSecond=" + oneHundredthSecond +
               ", length=" + length +
               ", lane=" + lane +
               ", direction=" + direction +
               ", vehicleClass=" + vehicleClass +
               ", speed=" + speed +
               ", faulty=" + faulty +
               ", totalTimeTechnical='" + totalTimeTechnical + '\'' +
               ", timeIntervalTechnical='" + timeIntervalTechnical + '\'' +
               ", queueStartTechnical='" + queueStartTechnical + '\'' +
               '}';
    }
}
