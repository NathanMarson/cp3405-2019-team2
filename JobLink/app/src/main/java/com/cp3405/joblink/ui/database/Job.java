package com.cp3405.joblink.ui.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "jobs", indices = {@Index(value = "job_title", unique = true)})
public class Job {

//    @NonNull
//    public String getJobID() {
//        return jobID;
//    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int jobID;

    @NonNull
    @ColumnInfo(name = "job_title")
    public String jobTitle;
    @NonNull
    @ColumnInfo(name = "description")
    public String description;

    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "employer_ID")
    @NonNull
    @ColumnInfo(name = "employer_ID")
    public int employerID;

    public Job(String jobTitle, String description, int employerID) {
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
