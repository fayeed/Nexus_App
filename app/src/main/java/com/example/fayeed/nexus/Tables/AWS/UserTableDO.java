package com.example.fayeed.nexus.Tables.AWS;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "socailapp-mobilehub-1223798447-User_Table")

public class UserTableDO {
    private String _userId;
    private String _institudeId;
    private String _identityPoolId;
    private String _location;
    private String _roleId;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId()
    {
        return _userId;
    }
    public void setUserId(final String _userId)
    {
        this._userId = _userId;
    }

    @DynamoDBAttribute(attributeName = "Institude_Id")
    public String getInstitudeId()
    {
        return _institudeId;
    }
    public void setInstitudeId(final String _institudeId)
    {
        this._institudeId = _institudeId;
    }

    @DynamoDBAttribute(attributeName = "identityPoolId")
    public String getIdentityPoolId()
    {
        return _identityPoolId;
    }
    public void setIdentityPoolId(final String _identityPoolId)
    {
        this._identityPoolId = _identityPoolId;
    }

    @DynamoDBAttribute(attributeName = "location")
    public String getLocation()
    {
        return _location;
    }
    public void setLocation(final String _location)
    {
        this._location = _location;
    }

    @DynamoDBAttribute(attributeName = "role_Id")
    public String getRoleId()
    {
        return _roleId;
    }
    public void setRoleId(final String _roleId)
    {
        this._roleId = _roleId;
    }

}
