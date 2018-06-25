package cl.vicentepc.flashg7.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {

    public CurrentUser(){

    }

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public String email(){
        return getCurrentUser().getEmail();
    }

    public String uid(){
        return currentUser.getUid();
    }

    public String sanitizedEmail(String email){

        return email.replace("@","AT").replace(".","DOT");

    }

}
