package udacity.com.bakingapp.util;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.com.bakingapp.idlingresource.SimpleIdlingResource;
import udacity.com.bakingapp.model.Receita;
import udacity.com.bakingapp.service.ApiClient;
import udacity.com.bakingapp.service.ApiService;

public class BaixarReceitas {

    public static void downloadRecipe(final DelayerCallback callback,
                                      @Nullable final SimpleIdlingResource idlingResource) {

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        ApiService mAPIService = ApiClient.getClient().create(ApiService.class);

        Call<List<Receita>> call = mAPIService.getBakingRecipes();
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Call<List<Receita>> call, Response<List<Receita>> response) {
                List<Receita> receitas = response.body();
                if (callback != null) {
                    callback.onDone((ArrayList<Receita>) receitas);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Receita>> call, Throwable t) {
                if (callback != null) {
                    callback.onFailed(t);
                }
            }
        });
    }

    public interface DelayerCallback {
        void onDone(ArrayList<Receita> receitas);

        void onFailed(Throwable t);
    }
}
