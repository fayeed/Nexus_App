package com.example.fayeed.nexus.Tables.AWS;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;


@DynamoDBTable(tableName = "socailapp-mobilehub-1223798447-Id_Table")
public class IdTableDO
{
    private String _institudeId;
    private String _hId;
    private String _lId;
    private String _mId;
    private String _noUsers;
    private String _plan;
    private String _chat_location;
    private String _discussion_thread_location;
    private String _news_location;
    private String _profile_location;
    private String _tweet_location;

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

    @DynamoDBAttribute(attributeName = "h_Id")
    public String getHId()
    {
        return _hId;
    }
    public void setHId(final String _hId)
    {
        this._hId = _hId;
    }

    @DynamoDBAttribute(attributeName = "l_Id")
    public String getLId()
    {
        return _lId;
    }
    public void setLId(final String _lId)
    {
        this._lId = _lId;
    }

    @DynamoDBAttribute(attributeName = "m_Id")
    public String getMId()
    {
        return _mId;
    }
    public void setMId(final String _mId)
    {
        this._mId = _mId;
    }

    @DynamoDBAttribute(attributeName = "noUsers")
    public String getNoUsers()
    {
        return _noUsers;
    }
    public void setNoUsers(final String _noUsers)
    {
        this._noUsers = _noUsers;
    }

    @DynamoDBAttribute(attributeName = "plan")
    public String getPlan()
    {
        return _plan;
    }
    public void setPlan(final String _plan)
    {
        this._plan = _plan;
    }

    @DynamoDBAttribute(attributeName = "discussion_thread_location")
    public String getDiscussion_thread_location() {
        return _discussion_thread_location;
    }
    public void setDiscussion_thread_location(final String _discussion_thread_location){
        this._discussion_thread_location = _discussion_thread_location;
    }

    @DynamoDBAttribute(attributeName = "news_location")
    public String getNews_location() {
        return _news_location;
    }
    public void setNews_location(final String _news_location){
        this._news_location = _news_location;
    }

    @DynamoDBAttribute(attributeName = "profile_location")
    public String getProfile_location() {
        return _profile_location;
    }
    public void setProfile_location(final String _profile_location){
        this._profile_location = _profile_location;
    }

    @DynamoDBAttribute(attributeName = "tweet_location")
    public String getTweet_location() {
        return _tweet_location;
    }
    public void setTweet_location(final String _tweet_location){
        this._tweet_location = _tweet_location;
    }

    @DynamoDBAttribute(attributeName = "chat_location")
    public String get_chat_location() {
        return _chat_location;
    }
    public void set_chat_location(final String _chat_location) {
        this._chat_location = _chat_location;
    }
}
