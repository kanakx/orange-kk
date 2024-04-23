package org.dev;

import java.util.List;

public class MyCalendar {

    private TimeFrame workingHours;
    private List<TimeFrame> plannedMeetings;

    public MyCalendar(TimeFrame workingHours, List<TimeFrame> plannedMeetings) {
        this.workingHours = workingHours;
        this.plannedMeetings = plannedMeetings;
    }

    public TimeFrame getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(TimeFrame workingHours) {
        this.workingHours = workingHours;
    }

    public List<TimeFrame> getPlannedMeetings() {
        return plannedMeetings;
    }

    public void setPlannedMeetings(List<TimeFrame> plannedMeetings) {
        this.plannedMeetings = plannedMeetings;
    }

}
