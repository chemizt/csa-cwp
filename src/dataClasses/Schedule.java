package dataClasses;

import java.util.ArrayList;

public class Schedule
{
    public ArrayList<Lesson> getSchedule()
    {
        return schedule;
    }
    public void setSchedule(ArrayList<Lesson> schedule)
    {
        this.schedule = schedule;
    }
    private ArrayList<Lesson> schedule;
}
