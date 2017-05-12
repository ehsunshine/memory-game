package ir.jaryaan.matchmatch.network;

import java.io.IOException;

import ir.jaryaan.matchmatch.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ehsun on 5/12/2017.
 */

public class InjectConsumerKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String consumerKey = BuildConfig.CONSUMER_KEY;

        Request request = chain.request();
        HttpUrl requestUrl = request.url();

        HttpUrl url = requestUrl.newBuilder()
                .addQueryParameter("consumer_key", consumerKey)
                .build();

        Request.Builder requestBuilder = request.newBuilder()
                .url(url);

        Request finalRequest = requestBuilder.build();
        return chain.proceed(finalRequest);
    }
}
