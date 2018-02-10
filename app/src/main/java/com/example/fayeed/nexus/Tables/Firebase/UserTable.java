package com.example.fayeed.nexus.Tables.Firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserTable {
    private String userId;
    private String institudeId;
    private String location;
    private String roleId;
    private String role;

    public UserTable(){}

    public UserTable(String userId, String institudeId, String location, String roleId, String role){
        this.userId = userId;
        this.institudeId = institudeId;
        this.location = location;
        this.roleId = roleId;
        this.role = role;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getInstitudeId()
    {
        return institudeId;
    }

    public String getLocation()
    {
        return location;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public String getRole() {
        return role;
    }
}
