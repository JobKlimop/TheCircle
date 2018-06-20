package io.antmedia.android.broadcaster.security;

import android.content.Context;

import java.io.IOException;

import io.antmedia.android.broadcaster.network.RTMPStreamer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestTask {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String post(String url, Context c, RTMPStreamer.Frame frame, String hash) throws IOException {

        // TODO: Implelement hashing of specified code
        String json = "{hash: " + hash + "}";

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            return e.toString();
        }
    }

}
