package org.dev;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleUtility {

    private ScheduleUtility() {}

    public static List<TimeFrame> findFreeTimeFrames(MyCalendar c1) {
        List<TimeFrame> freeTimeFrames = new ArrayList<>();

        if (c1.getPlannedMeetings().isEmpty()) {
            freeTimeFrames.add(c1.getWorkingHours());
            return freeTimeFrames;
        }

        TimeFrame workingHours = c1.getWorkingHours();
        LocalTime startTime = c1.getWorkingHours().getStartTime();
        List<TimeFrame> plannedMeetings = c1.getPlannedMeetings();
        for (TimeFrame plannedMeeting : plannedMeetings) {
            if (plannedMeeting.getStartTime().isAfter(startTime) && plannedMeeting.getStartTime().isBefore(workingHours.getEndTime())) {
                freeTimeFrames.add(new TimeFrame(startTime, plannedMeeting.getStartTime()));
            }
            startTime = plannedMeeting.getEndTime().isAfter(startTime) ? plannedMeeting.getEndTime() : startTime;
        }

        if (startTime.isBefore(workingHours.getEndTime())) {
            freeTimeFrames.add(new TimeFrame(startTime, workingHours.getEndTime()));
        }

        return freeTimeFrames;
    }

    public static List<TimeFrame> proposeMeetingTimeFrames(MyCalendar c1, MyCalendar c2, Duration desiredMeetingDuration) {
        List<TimeFrame> freeTimeC1 = findFreeTimeFrames(c1);
        List<TimeFrame> freeTimeC2 = findFreeTimeFrames(c2);

        List<TimeFrame> res = new ArrayList<>();

        for (TimeFrame timeFrameC1 : freeTimeC1) {
            for (TimeFrame timeFrameC2 : freeTimeC2) {
                if (doTimeFramesOverlap(timeFrameC1, timeFrameC2)) {
                    LocalTime startTime = returnLaterTime(timeFrameC1.getStartTime(), timeFrameC2.getStartTime());
                    LocalTime endTime = returnEarlierTime(timeFrameC1.getEndTime(), timeFrameC2.getEndTime());
                    if (Duration.between(startTime, endTime).toMinutes() >= desiredMeetingDuration.toMinutes()) {
                        res.add(new TimeFrame(startTime, endTime));
                    }
                }
            }
        }

        return res;
    }

    private static boolean doTimeFramesOverlap(TimeFrame timeFrame1, TimeFrame timeFrame2) {
        return timeFrame1.getStartTime().isBefore(timeFrame2.getEndTime()) && timeFrame2.getStartTime().isBefore(timeFrame1.getEndTime());
    }

    private static LocalTime returnEarlierTime(LocalTime localTime1, LocalTime localTime2) {
        return localTime1.isBefore(localTime2) ? localTime1 : localTime2;
    }

    private static LocalTime returnLaterTime(LocalTime localTime1, LocalTime localTime2) {
        return localTime1.isAfter(localTime2) ? localTime1 : localTime2;
    }

}
