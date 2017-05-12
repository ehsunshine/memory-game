package ir.jaryaan.matchmatch.entities;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by ehsun on 5/12/2017.
 */
@Getter
@NoArgsConstructor
public class ApiResponse {
    @SerializedName("current_page")
    int currentPage;
    @SerializedName("photos")
    JsonArray cardImages;


}
