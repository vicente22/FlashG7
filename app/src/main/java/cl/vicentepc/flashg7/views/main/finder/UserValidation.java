package cl.vicentepc.flashg7.views.main.finder;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.vicentepc.flashg7.data.CurrentUser;
import cl.vicentepc.flashg7.data.EmailProcessor;
import cl.vicentepc.flashg7.data.Nodes;
import cl.vicentepc.flashg7.data.PhotoPreference;
import cl.vicentepc.flashg7.models.Chat;
import cl.vicentepc.flashg7.models.LocalUser;

public class UserValidation {

    private FinderCallback callback;
    private Context context;

    public UserValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email){
        if(email.trim().length() > 0){

            if(email.contains("@")){
                String currentEmail = new CurrentUser().email();
                if(!email.equals(currentEmail)){

                    findUser(email);

                }else{
                    callback.error("¿Chat contigo mismo?");
                }
            }else{
                callback.error("Email mal escrito");
            }

        }else{

            callback.error("Se necesita el email");

        }
    }

    private void findUser(String email){
        new Nodes().user(new EmailProcessor().sanitizedEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocalUser otherUser = dataSnapshot.getValue(LocalUser.class);
                if(otherUser != null){
                    createChats(otherUser);
                }else{
                    callback.notFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createChats(LocalUser otherUser){

        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreference(context).getPhoto();

        String key = new EmailProcessor().keyEmails(otherUser.getEmail());

        //Usuario que empieza
        Chat currentChat = new Chat();
        currentChat.setKey(key);
        currentChat.setPhoto(otherUser.getPhoto());
        currentChat.setNotification(true);
        currentChat.setReceiver(otherUser.getEmail());

        //Chat del otro usuario logueado
        Chat otherChat = new Chat();
        otherChat.setKey(key);
        otherChat.setPhoto(photo);
        otherChat.setNotification(true);
        otherChat.setReceiver(currentUser.getEmail());

        //Enviamos la data de tu usuario loguead
        new Nodes().userChat(currentUser.getUid()).child(key).setValue(currentChat);

        new Nodes().userChat(otherUser.getUid()).child(key).setValue(otherChat);

        callback.success();

    }

}
