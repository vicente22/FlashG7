package cl.vicentepc.flashg7.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PhotoPreference {

    private static final String GROUP_PHOTO = "cl.vicentepc.flashg7.data.GROUP_KEY";
    private static final String KEY_PHOTO = "cl.vicentepc.flashg7.data.KEY_PHOTO";

    private Context context;

    public PhotoPreference(Context context) {
        this.context = context;
    }

    public void photoSave(String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_PHOTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(KEY_PHOTO, url);
        prefEditor.apply();
    }

    public String getPhoto() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_PHOTO, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO, null);
    }

}
