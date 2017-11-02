package bakingapp.project.anuja.com.bakeapp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 11-05-2017.
 */

public class BakingClient {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
