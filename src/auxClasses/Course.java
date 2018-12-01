package auxClasses;

import java.time.Period;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

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
    public void parseString(String strToParse)
    {
        name = returnSubString(strToParse);
        strToParse = purgeString(strToParse);
        duration = Period.parse(returnSubString(strToParse));
        strToParse = purgeString(strToParse);
        intensity = Integer.parseInt(returnSubString(strToParse));
    }
}
