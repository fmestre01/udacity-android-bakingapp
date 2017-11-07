package udacity.com.bakingapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import udacity.com.bakingapp.util.Const;

public class ApiClient {
    private static Retrofit r = null;

    public static Retrofit getClient() {
        if (r == null) {
            r = new Retrofit.Builder()
                    .baseUrl(Const.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return r;
    }
}
