package com.example.fayeed.nexus.Tables.AWS;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;


@DynamoDBTable(tableName = "socailapp-mobilehub")
public class UserProfileDO {

    private String _id;
    private String _name;
    private String _imageLocation;
    private String _workDescription;
    private String _bio;
    private String _email;
    private String _phoneNo;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String get_id() {
        return _id;
    }
    public void set_id(final String _id) {
        this._id = _id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String get_name() {
        return _name;
    }
    public void set_name(final String _name) {
        this._name = _name;
    }

    @DynamoDBAttribute(attributeName = "imageLocation")
    public String get_imageLocation() {
        return _imageLocation;
    }
    public void set_imageLocation(final String _imageLocation) {
        this._imageLocation = _imageLocation;
    }

    @DynamoDBAttribute(attributeName = "workDescription")
    public String get_workDescription() {
        return _workDescription;
    }
    public void set_workDescription(final String _workDescription) {
        this._workDescription = _workDescription;
    }

    @DynamoDBAttribute(attributeName = "bio")
    public String get_bio() {
        return _bio;
    }
    public void set_bio(final String _bio) {
        this._bio = _bio;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String get_email() {
        return _email;
    }
    public void set_email(final String _email) {
        this._email = _email;
    }

    @DynamoDBAttribute(attributeName = "phoneNo")
    public String get_phoneNo() {
        return _phoneNo;
    }
    public void set_phoneNo(final String _phoneNo) {
        this._phoneNo = _phoneNo;
    }
}
