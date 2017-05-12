package ir.jaryaan.matchmatch.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.UnknownHostException;

import ir.jaryaan.matchmatch.entities.ApiError;
import ir.jaryaan.matchmatch.exception.ApiBusinessException;
import ir.jaryaan.matchmatch.exception.ApiNetworkException;
import ir.jaryaan.matchmatch.exception.ApiUnknownException;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ResponseUtil {
    public static boolean isNetworkError(Throwable error) {
        return error == null ||
                error instanceof UnknownHostException ||
                error instanceof IOException;
    }

    public static boolean isServerError(Throwable error) {
        if (error != null && error instanceof HttpException) {
            HttpException exception = (HttpException) error;
            return exception.code() >= 500;
        }
        return false;
    }

    public static boolean isBusinessError(Throwable error) {
        if (error != null && error instanceof HttpException) {
            HttpException exception = (HttpException) error;
            return exception.code() >= 400 && exception.code() < 500;
        }
        return false;
    }

    public static int getStatusCode(Throwable error) {
        if (error != null && error instanceof HttpException) {
            HttpException exception = (HttpException) error;
            return exception.code();
        }
        return 0;
    }

    public static ApiError getApiError(Throwable error) {
        if (isBusinessError(error)) {
            HttpException exception = (HttpException) error;
            try {
                String body = exception.response().errorBody().string();
                return GsonParser.getGson().fromJson(body, ApiError.class);
            } catch (NullPointerException | JsonSyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getBusinessErrorMessage(Throwable e) {
        ApiError error = getApiError(e);
        if (error != null) {
            if (!TextUtils.isEmpty(error.getError())) {
                return error.getError();
            }
        }
        return null;
    }

    @NonNull
    public static Throwable convertApiException(@NonNull Throwable throwable) {
        if (ResponseUtil.isBusinessError(throwable)) {
            return new ApiBusinessException(ResponseUtil.getBusinessErrorMessage(throwable));
        }
        if (ResponseUtil.isNetworkError(throwable)) {
            return new ApiNetworkException(throwable);
        }

        return new ApiUnknownException(throwable);
    }

}
