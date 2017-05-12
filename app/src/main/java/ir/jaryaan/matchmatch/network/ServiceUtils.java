package ir.jaryaan.matchmatch.network;

import android.util.Log;

import com.google.gson.Gson;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ir.jaryaan.matchmatch.MatchMatchApplication;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ServiceUtils {

    private static final String LOG_TAG = MatchMatchApplication.LOG_TAG;

    public static Retrofit createDefaultRetrofitBuilder(String baseUrl, boolean useSsl, Gson gson, Interceptor... interceptors) {

        OkHttpClient.Builder okHttpClientBuilder = createDefaultClient(useSsl);
        if (interceptors != null && interceptors.length > 0) {
            for (Interceptor interceptor : interceptors) {
                okHttpClientBuilder.interceptors().add(interceptor);
            }
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();
    }

    public static Interceptor getLoggingInterceptor(HttpLoggingInterceptor.Level logLevel) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(logLevel);
        return loggingInterceptor;
    }

    public static OkHttpClient.Builder createDefaultClient(boolean useSsl) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .followSslRedirects(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES);

        if (useSsl) {
            return okHttpClientBuilder;
        }

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            Log.d(LOG_TAG, e.getMessage());
        }

        okHttpClientBuilder = okHttpClientBuilder
                .sslSocketFactory(sslSocketFactory)
                .retryOnConnectionFailure(true)
                .hostnameVerifier((hostname, session) -> true);

        return okHttpClientBuilder;
    }
}
