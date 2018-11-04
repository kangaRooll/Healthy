package com.a59070055.healthy_h1.sleep;

public class Sleep {

    private int primaryId;
    private String currentDate;
    private String timetosleep;
    private String timetowakeup;
    private String counttime;

    private static Sleep sleepInstance;

    private Sleep() {

    }

    public Sleep(int primaryId, String currentDate, String timetosleep, String timetowakeup, String counttime) {
        this.setPrimaryId(primaryId);
        this.setCurrentDate(currentDate);
        this.setTimetosleep(timetosleep);
        this.setTimetowakeup(timetowakeup);
        this.setCounttime(counttime);
    }

    public static Sleep getSleepInstance() {
        if(sleepInstance == null) {
            sleepInstance = new Sleep();
        }
        return sleepInstance;
    }

    public static Sleep setSleepInstance() {
        if(sleepInstance != null) {
            sleepInstance = null;
        }
        return sleepInstance;
    }

    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCounttime() {
        return counttime;
    }

    public void setCounttime(String counttime) {
        this.counttime = counttime;
    }

    public String getTimetosleep() {
        return timetosleep;
    }

    public void setTimetosleep(String timetosleep) {
        this.timetosleep = timetosleep;
    }

    public String getTimetowakeup() {
        return timetowakeup;
    }

    public void setTimetowakeup(String timetowakeup) {
        this.timetowakeup = timetowakeup;
    }
}