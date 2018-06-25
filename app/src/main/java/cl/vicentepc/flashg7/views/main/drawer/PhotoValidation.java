package cl.vicentepc.flashg7.views.main.drawer;

import android.content.Context;

import cl.vicentepc.flashg7.data.PhotoPreference;

public class PhotoValidation {

    private Context context;
    private PhotoCallback callback;

    public PhotoValidation(Context context, PhotoCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void validate(){

        String url = new PhotoPreference(context).getPhoto();
        if(url != null){

            callback.availablePhoto(url);

        }else{

            callback.emptyPhoto();

        }

    }

}
