package com.android.jobber.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobberUsersResponse {
    @SerializedName("jobber_users")
    @Expose
    private List<JobberUsers> jobberUsers = null;

    public List<JobberUsers> getJobberUsers() {
        return jobberUsers;
    }

    public void setJobberUsers(List<JobberUsers> jobberUsers) {
        this.jobberUsers = jobberUsers;
    }
}
