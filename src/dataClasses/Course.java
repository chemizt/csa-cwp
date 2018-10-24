package dataClasses;

import java.time.Period;

public class Course
{
    private String name;
    private Period duration;
    private int intensity;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Period getDuration()
    {
        return duration;
    }
    public void setDuration(Period duration)
    {
        this.duration = duration;
    }
    public int getIntensity()
    {
        return intensity;
    }
    public void setIntensity(int intensity)
    {
        this.intensity = intensity;
    }
}
