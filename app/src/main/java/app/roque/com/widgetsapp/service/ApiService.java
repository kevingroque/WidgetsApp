package app.roque.com.widgetsapp.service;

import java.util.List;

import app.roque.com.widgetsapp.Evento;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "https://lab14android-kevinghanz.c9users.io/";

    @GET("api/v1/eventos")
    Call<List<Evento>> getEventos();

    Call<Evento> showEvento(@Path("id") Integer id);

}
