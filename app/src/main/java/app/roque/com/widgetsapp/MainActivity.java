package app.roque.com.widgetsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.roque.com.widgetsapp.service.ApiService;
import app.roque.com.widgetsapp.service.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView eventosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventosList = (ListView) findViewById(R.id.eventosList);

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Evento>> call = service.getEventos();
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                       List<Evento> eventosLista = new ArrayList<>();
                       eventosLista = response.body();
                       EventosAdapter adapter = new EventosAdapter(MainActivity.this,eventosLista);
                       eventosList.setAdapter(adapter);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

}
    private class EventosAdapter extends ArrayAdapter<Evento>{

        private List<Evento> listEventos;

        public EventosAdapter(Context context, List<Evento> events) {
            super(context, R.layout.item_evento,events);
            listEventos = events;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.item_evento, null);

            TextView idText = (TextView)item.findViewById(R.id.eventoId);
            TextView nombreText = (TextView)item.findViewById(R.id.eventoNombre);
            TextView fechaText = (TextView)item.findViewById(R.id.eventoFecha);
            TextView mensajeText = (TextView)item.findViewById(R.id.eventoMensaje);

            idText.setText(listEventos.get(position).getId().toString());
            nombreText.setText(listEventos.get(position).getNombre());
            fechaText.setText(listEventos.get(position).getCreated_at());
            mensajeText.setText(listEventos.get(position).getMensaje());


            return item;
        }
    }

}
