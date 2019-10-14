package com.cp3405.joblink.ui.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "jobs")
public class Job {

//    @NonNull
//    public String getJobID() {
//        return jobID;
//    }

    @PrimaryKey
    @NonNull
    public String jobID;

    @NonNull
    @ColumnInfo(name = "job_title")
    public String jobTitle;
    @NonNull
    @ColumnInfo(name = "description")
    public String description;

    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "employer_ID")
    @NonNull
    @ColumnInfo(name = "employer_ID")
    public String employerID;

    public Job(String jobID, String jobTitle, String description, String employerID) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.description = description;
        this.employerID = employerID;
    }

//    @NonNull
//    public String getJobId() {
//        return jobID;
//    }
//
//    @NonNull
//    public String getJobTitle() {
//        return this.jobTitle;
//    }
//
//    @NonNull
//    public String getDescription() {
//        return this.description;
//    }
//
//    @NonNull
//    public String getEmployerID() {
//        return this.employerID;
//    }
}
