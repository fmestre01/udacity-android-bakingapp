package udacity.com.bakingapp.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import udacity.com.bakingapp.model.Receita;
import udacity.com.bakingapp.util.Const;

public interface ApiService {
    @GET(Const.SERVICE_API)
    Call<List<Receita>> getBakingRecipes();
}
