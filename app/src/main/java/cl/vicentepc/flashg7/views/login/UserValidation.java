package cl.vicentepc.flashg7.views.login;

import cl.vicentepc.flashg7.data.CurrentUser;

public class UserValidation {

    private UserValidationCallback callback;

    public UserValidation(UserValidationCallback callback) {
        this.callback = callback;
    }

    public void validation(){

        if(new CurrentUser().getCurrentUser()!= null){

            callback.logged();

        }else{

            callback.signUp();

        }

    }

}
