package bakingapp.project.anuja.com.bakeapp.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by USER on 02-10-2017.
 */

public class WidgetRemoteViewService extends RemoteViewsService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewFactory(this.getApplicationContext(), intent);
    }

}
