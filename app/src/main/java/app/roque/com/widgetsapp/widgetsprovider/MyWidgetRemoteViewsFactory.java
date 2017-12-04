package app.roque.com.widgetsapp.widgetsprovider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import app.roque.com.widgetsapp.Evento;
import app.roque.com.widgetsapp.service.ApiService;
import app.roque.com.widgetsapp.service.ApiServiceGenerator;
import retrofit2.Call;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";

    List<String> mCollection = new ArrayList<>();
    Context mContext = null;

    public MyWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initialize();
    }

    @Override
    public void onDataSetChanged() {
        initialize();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mCollection.get(position));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    private void initialize() {
        try {
            ApiService service = ApiServiceGenerator.createService(ApiService.class);
            Call<List<Evento>> call = service.getEventos();
            List<Evento> eventos = call.execute().body();
            Log.d(TAG, "eventos: " + eventos);
            for (Evento evento : eventos){
                String id = evento.getId().toString();
                String nombre = evento.getNombre();
                String created_at = evento.getCreated_at();
                String mensaje = evento.getMensaje();
                String lista = id+ " | " + created_at + "\n" +nombre+ " \n"+ mensaje;

                Log.d(TAG, "nombre: " + nombre);
                mCollection.add(lista);
            }

        }catch(Exception e){
            Log.e(TAG, e.toString());
        }
    }


}
