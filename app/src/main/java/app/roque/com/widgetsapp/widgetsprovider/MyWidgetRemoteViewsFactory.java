package app.roque.com.widgetsapp.widgetsprovider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import app.roque.com.widgetsapp.R;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (mCursor != null) {
            mCursor.close();
        }

        /*final long identityToken = Binder.clearCallingIdentity();
        Uri uri = Contract.PATH_TODOS_URI;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                Contract._ID + " DESC");

        Binder.restoreCallingIdentity(identityToken);*/

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_evento);
        rv.setTextViewText(R.id.eventoId, mCursor.getString(1));
        rv.setTextViewText(R.id.eventoFecha, mCursor.getString(1));
        rv.setTextViewText(R.id.eventoNombre, mCursor.getString(1));
        rv.setTextViewText(R.id.eventoMensaje, mCursor.getString(1));

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(CollectionAppWidgetProvider.EXTRA_LABEL, mCursor.getString(1));
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
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
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
