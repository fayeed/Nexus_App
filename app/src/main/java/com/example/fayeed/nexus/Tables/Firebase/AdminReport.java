package com.example.fayeed.nexus.Tables.Firebase;

import java.text.DateFormat;
import java.util.Date;

public class AdminReport {
    String reportedById;
    String reportedId;
    String reportedByName;
    String reportedName;
    String report;
    String timestamp;
    String category;
    boolean resloved;

    public AdminReport(){}

    public AdminReport(String reportedById, String reportedId, String reportedByName, String reportedName, String report,
                       String category, boolean resloved){
        this.reportedById = reportedById;
        this.reportedId = reportedId;
        this.reportedByName = reportedByName;
        this.reportedName = reportedName;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
        this.report = report;
        this.category = category;
        this.resloved = resloved;
    }
}
