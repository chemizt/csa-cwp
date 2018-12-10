package auxClasses;

import static auxClasses.ParserAuxUtils.purgeString;
import static auxClasses.ParserAuxUtils.returnSubString;

public class Address
{
    private long postIndex;
    private String country;
    private String city;
    private String street;
    private String building;
    private String floor;
    private Schedule container;
    public long getPostIndex()
    {
        return postIndex;
    }
    public void setPostIndex(long postIndex)
    {
        this.postIndex = postIndex;
    }
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getStreet()
    {
        return street;
    }
    public void setStreet(String street)
    {
        this.street = street;
    }
    public String getBuilding()
    {
        return building;
    }
    public void setBuilding(String building)
    {
        this.building = building;
    }
    public String getFloor()
    {
        return floor;
    }
    public void setFloor(String floor)
    {
        this.floor = floor;
    }
    public void setContainer(Schedule container)
    {
        this.container = container;
    }
    public void parseString(String strToParse)
    {
        String companyName = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        postIndex = Integer.parseInt(returnSubString(strToParse, "#"));
        strToParse = purgeString(strToParse, "#");
        country = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        city = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        street = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        building = returnSubString(strToParse, "#");
        strToParse = purgeString(strToParse, "#");
        floor = returnSubString(strToParse, "#");
        container.getCompanies().get(companyName).setLocation(this);
    }
    public String returnFull()
    {
        return postIndex + " " + country + ", г. " + city + ", ул. " + street + ", д. " + building + ", эт. " + floor;
    }
}
