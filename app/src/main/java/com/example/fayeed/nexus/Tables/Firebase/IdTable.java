package com.example.fayeed.nexus.Tables.Firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class IdTable
{
    private String institudeId;
    private String hId;
    private String lId;
    private String mId;
    private int noUsers;
    private String plan;

    public IdTable(){

    }

    public IdTable(String institudeId, String hId, String mId, String lId, String plan){
        this.institudeId = institudeId;
        this.hId = hId;
        this.mId = mId;
        this.lId = lId;
        this.plan = plan;
        this.noUsers = 0;
    }

    public String getInstitudeId()
    {
        return institudeId;
    }

    public String getHId()
    {
        return hId;
    }

    public String getLId()
    {
        return lId;
    }

    public String getMId()
    {
        return mId;
    }

    public int getNoUsers()
    {
        return noUsers;
    }

    public void setNoUsers(int noUsers) {
        this.noUsers = noUsers;
    }

    public String getPlan()
    {
        return plan;
    }

    public void setHId(String hId) {
        this.hId = hId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public void setLId(String lId) {
        this.lId = lId;
    }
}
