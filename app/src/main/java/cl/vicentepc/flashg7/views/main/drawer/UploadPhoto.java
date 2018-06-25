package cl.vicentepc.flashg7.views.main.drawer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.vicentepc.flashg7.data.CurrentUser;
import cl.vicentepc.flashg7.data.Nodes;
import cl.vicentepc.flashg7.data.PhotoPreference;
import cl.vicentepc.flashg7.models.LocalUser;

public class UploadPhoto {

    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFirebase(String path){

        final CurrentUser currentUser = new CurrentUser();
        String folder = currentUser.sanitizedEmail(currentUser.email() + "/");
        String photoName = "avatar.jpeg";
        String baseUrl = "gs://flashg7-2458e.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);

        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") String[] fullUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];

                new PhotoPreference(context).photoSave(url);
                Log.d("URL", url);
                LocalUser user = new LocalUser();
                user.setEmail(currentUser.email());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUser.uid());
                String key = currentUser.sanitizedEmail(currentUser.email());
                new Nodes().user(key).setValue(user);
                FirebaseDatabase.getInstance().getReference().child("users").child(key).setValue(user);

            }
        });


    }
}
