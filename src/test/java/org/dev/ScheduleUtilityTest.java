package org.dev;


import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduleUtilityTest {

    @Test
    void findFreeTimeFrames() {
        // Given
        TimeFrame workingHours1 = new TimeFrame(LocalTime.of(9, 0), LocalTime.of(19, 55));
        TimeFrame plannedMeeting1 = new TimeFrame(LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeFrame plannedMeeting2 = new TimeFrame(LocalTime.of(12, 0), LocalTime.of(13, 0));
        TimeFrame plannedMeeting3 = new TimeFrame(LocalTime.of(16, 0), LocalTime.of(18, 0));
        List<TimeFrame> plannedMeetings1 = List.of(plannedMeeting1, plannedMeeting2, plannedMeeting3);
        MyCalendar c1 = new MyCalendar(workingHours1, plannedMeetings1);

        // When
        List<TimeFrame> actual = ScheduleUtility.findFreeTimeFrames(c1);

        // Then
        TimeFrame res1 = new TimeFrame(LocalTime.of(10, 30), LocalTime.of(12, 0));
        TimeFrame res2 = new TimeFrame(LocalTime.of(13, 0), LocalTime.of(16, 0));
        TimeFrame res3 = new TimeFrame(LocalTime.of(18, 0), LocalTime.of(19, 55));
        List<TimeFrame> expected = List.of(res1, res2, res3);
        assertEquals(expected, actual);
    }

    @Test
    void proposeMeetingTimeFrames_WithInputFromTaskDescription_ReturnsOutputFromTaskDescription() {
        // Given
        TimeFrame workingHours1 = new TimeFrame(LocalTime.of(9, 0), LocalTime.of(19, 55));
        TimeFrame plannedMeeting1 = new TimeFrame(LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeFrame plannedMeeting2 = new TimeFrame(LocalTime.of(12, 0), LocalTime.of(13, 0));
        TimeFrame plannedMeeting3 = new TimeFrame(LocalTime.of(16, 0), LocalTime.of(18, 0));
        List<TimeFrame> plannedMeetings1 = List.of(plannedMeeting1, plannedMeeting2, plannedMeeting3);
        MyCalendar c1 = new MyCalendar(workingHours1, plannedMeetings1);

        TimeFrame workingHours2 = new TimeFrame(LocalTime.of(10, 0), LocalTime.of(18, 30));
        TimeFrame plannedMeeting4 = new TimeFrame(LocalTime.of(10, 0), LocalTime.of(11, 30));
        TimeFrame plannedMeeting5 = new TimeFrame(LocalTime.of(12, 30), LocalTime.of(14, 30));
        TimeFrame plannedMeeting6 = new TimeFrame(LocalTime.of(14, 30), LocalTime.of(15, 0));
        TimeFrame plannedMeeting7 = new TimeFrame(LocalTime.of(16, 0), LocalTime.of(17, 0));
        List<TimeFrame> plannedMeetings2 = List.of(plannedMeeting4, plannedMeeting5, plannedMeeting6, plannedMeeting7);
        MyCalendar c2 = new MyCalendar(workingHours2, plannedMeetings2);

        Duration desiredMeetingDuration = Duration.ofMinutes(30);

        // When
        List<TimeFrame> actual = ScheduleUtility.proposeMeetingTimeFrames(c1, c2, desiredMeetingDuration);

        // Then
        TimeFrame res1 = new TimeFrame(LocalTime.of(11, 30), LocalTime.of(12, 0));
        TimeFrame res2 = new TimeFrame(LocalTime.of(15, 0), LocalTime.of(16, 0));
        TimeFrame res3 = new TimeFrame(LocalTime.of(18, 0), LocalTime.of(18, 30));
        List<TimeFrame> expected = List.of(res1, res2, res3);
        assertEquals(expected, actual);
    }

    @Test
    void proposeMeetingTimeFrames_ReturnsTwoPossibleMeetings() {
        // Given
        TimeFrame workingHours1 = new TimeFrame(LocalTime.of(10, 0), LocalTime.of(12, 0));
        List<TimeFrame> plannedMeetings1 = new ArrayList<>();
        MyCalendar c1 = new MyCalendar(workingHours1, plannedMeetings1);

        TimeFrame workingHours2 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(12, 0));
        TimeFrame plannedMeeting1 = new TimeFrame(LocalTime.of(11, 15), LocalTime.of(11, 45));
        List<TimeFrame> plannedMeetings2 = List.of(plannedMeeting1);
        MyCalendar c2 = new MyCalendar(workingHours2, plannedMeetings2);

        Duration desiredMeetingDuration = Duration.ofMinutes(15);

        // When
        List<TimeFrame> actual = ScheduleUtility.proposeMeetingTimeFrames(c1, c2, desiredMeetingDuration);

        // Then
        TimeFrame res1 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(11, 15));
        TimeFrame res2 = new TimeFrame(LocalTime.of(11, 45), LocalTime.of(12, 0));
        List<TimeFrame> expected = List.of(res1, res2);
        assertEquals(expected, actual);
    }

    @Test
    void proposeMeetingTimeFrames_ReturnsNoPossibleMeetings() {
        // Given
        TimeFrame workingHours1 = new TimeFrame(LocalTime.of(10, 0), LocalTime.of(12, 0));
        List<TimeFrame> plannedMeetings1 = new ArrayList<>();
        MyCalendar c1 = new MyCalendar(workingHours1, plannedMeetings1);

        TimeFrame workingHours2 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(12, 0));
        TimeFrame plannedMeeting1 = new TimeFrame(LocalTime.of(11, 15), LocalTime.of(11, 45));
        List<TimeFrame> plannedMeetings2 = List.of(plannedMeeting1);
        MyCalendar c2 = new MyCalendar(workingHours2, plannedMeetings2);

        Duration desiredMeetingDuration = Duration.ofMinutes(30);

        // When
        List<TimeFrame> actual = ScheduleUtility.proposeMeetingTimeFrames(c1, c2, desiredMeetingDuration);

        // Then
        List<TimeFrame> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void proposeMeetingTimeFrames_ReturnsOnePossibleMeeting() {
        // Given
        TimeFrame workingHours1 = new TimeFrame(LocalTime.of(10, 0), LocalTime.of(12, 0));
        List<TimeFrame> plannedMeetings1 = new ArrayList<>();
        MyCalendar c1 = new MyCalendar(workingHours1, plannedMeetings1);

        TimeFrame workingHours2 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(12, 0));
        TimeFrame plannedMeeting1 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(11, 15));
        List<TimeFrame> plannedMeetings2 = List.of(plannedMeeting1);
        MyCalendar c2 = new MyCalendar(workingHours2, plannedMeetings2);

        Duration desiredMeetingDuration = Duration.ofMinutes(30);

        // When
        List<TimeFrame> actual = ScheduleUtility.proposeMeetingTimeFrames(c1, c2, desiredMeetingDuration);

        // Then
        TimeFrame res1 = new TimeFrame(LocalTime.of(11, 15), LocalTime.of(12, 0));
        List<TimeFrame> expected = List.of(res1);
        assertEquals(expected, actual);
    }

    @Test
    void proposeMeetingTimeFrames_NotEnoughTime_ReturnsNoPossibleMeetings() {
        // Given
        TimeFrame workingHours1 = new TimeFrame(LocalTime.of(10, 0), LocalTime.of(12, 0));
        List<TimeFrame> plannedMeetings1 = new ArrayList<>();
        MyCalendar c1 = new MyCalendar(workingHours1, plannedMeetings1);

        TimeFrame workingHours2 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(12, 0));
        TimeFrame plannedMeeting1 = new TimeFrame(LocalTime.of(11, 0), LocalTime.of(11, 15));
        List<TimeFrame> plannedMeetings2 = List.of(plannedMeeting1);
        MyCalendar c2 = new MyCalendar(workingHours2, plannedMeetings2);

        Duration desiredMeetingDuration = Duration.ofMinutes(46);

        // When
        List<TimeFrame> actual = ScheduleUtility.proposeMeetingTimeFrames(c1, c2, desiredMeetingDuration);

        // Then
        List<TimeFrame> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

}
