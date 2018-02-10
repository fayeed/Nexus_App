package com.example.fayeed.nexus.Tables.AWS;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "socailapp-mobilehub-1223798447-Registration_Table")

public class RegistrationTableDO
{
    private String _institudeId;
    private String _address;
    private String _companyEmailID;
    private String _companyName;
    private String _companyPhoneNo;
    private String _companyWebsite;
    private String _country;
    private String _state;

    @DynamoDBHashKey(attributeName = "Institude_Id")
    @DynamoDBAttribute(attributeName = "Institude_Id")
    public String getInstitudeId()
    {
        return _institudeId;
    }
    public void setInstitudeId(final String _institudeId)
    {
        this._institudeId = _institudeId;
    }

    @DynamoDBAttribute(attributeName = "address")
    public String getAddress()
    {
        return _address;
    }
    public void setAddress(final String _address)
    {
        this._address = _address;
    }

    @DynamoDBAttribute(attributeName = "companyEmailID")
    public String getCompanyEmailID()
    {
        return _companyEmailID;
    }
    public void setCompanyEmailID(final String _companyEmailID)
    {
        this._companyEmailID = _companyEmailID;
    }

    @DynamoDBAttribute(attributeName = "companyName")
    public String getCompanyName()
    {
        return _companyName;
    }
    public void setCompanyName(final String _companyName)
    {
        this._companyName = _companyName;
    }

    @DynamoDBAttribute(attributeName = "companyPhoneNo")
    public String getCompanyPhoneNo()
    {
        return _companyPhoneNo;
    }
    public void setCompanyPhoneNo(final String _companyPhoneNo)
    {
        this._companyPhoneNo = _companyPhoneNo;
    }

    @DynamoDBAttribute(attributeName = "companyWebsite")
    public String getCompanyWebsite()
    {
        return _companyWebsite;
    }
    public void setCompanyWebsite(final String _companyWebsite)
    {
        this._companyWebsite = _companyWebsite;
    }

    @DynamoDBAttribute(attributeName = "country")
    public String getCountry()
    {
        return _country;
    }
    public void setCountry(final String _country)
    {
        this._country = _country;
    }

    @DynamoDBAttribute(attributeName = "state")
    public String getState()
    {
        return _state;
    }
    public void setState(final String _state)
    {
        this._state = _state;
    }
}
