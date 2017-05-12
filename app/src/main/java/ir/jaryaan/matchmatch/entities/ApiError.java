package ir.jaryaan.matchmatch.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ApiError {
    @SerializedName("status")
    private int status;
    @SerializedName("error")
    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
