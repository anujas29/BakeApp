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

//    public static Retrofit getClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build();
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Recipe.class, new Recipe.RecipeTypeAdapter())
//                .registerTypeAdapter(Ingredient.class, new Ingredient.IngredientTypeAdapter())
//                .registerTypeAdapter(Step.class, new Step.StepTypeAdapter())
//                .create();
//         if(retrofit==null) {
//             retrofit = new Retrofit.Builder()
//                     .baseUrl(BASE_URL)
//                     .client(okHttpClient)
//                     .addConverterFactory(GsonConverterFactory.create(gson))
//                     .build();
//         }
//
//        return retrofit;
//    }

}
