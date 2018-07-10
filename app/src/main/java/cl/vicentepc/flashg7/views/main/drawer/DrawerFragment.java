package cl.vicentepc.flashg7.views.main.drawer;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import cl.vicentepc.flashg7.R;
import cl.vicentepc.flashg7.data.CurrentUser;
import cl.vicentepc.flashg7.views.login.LoginActivity;

import static android.app.Activity.RESULT_OK;


public class DrawerFragment extends Fragment implements PhotoCallback {

    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private CircularImageView avatar;

    private int PHOTO_SIZE = 30;


    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView logout = view.findViewById(R.id.logoutTv);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
            }
        });

        TextView userEmail = view.findViewById(R.id.emailTv);
        userEmail.setText(new CurrentUser().email());

        String[] permissions = new String[]{

                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };

        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(getActivity(), PHOTO_SIZE, magicalPermissions);

        avatar = view.findViewById(R.id.avatarCi);

        new PhotoValidation(getContext(), this).validate();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        magicalPermissions.permissionResult(requestCode, permissions, grantResults);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        if (RESULT_OK == resultCode) {

            Bitmap photo = magicalCamera.getPhoto();
            String path = magicalCamera.savePhotoInMemoryDevice(photo,"avatar","flash", MagicalCamera.JPEG, true);

            path = "file://" + path;

            setPhoto(path);

            new UploadPhoto(getContext()).toFirebase(path);


        } else {

            requestSelfie();

        }

    }

    private void requestSelfie() {

        new AlertDialog.Builder(getActivity())
                .setTitle("Selfie :)")
                .setMessage("Para completar el registro debes tener una selfie realizada")
                .setCancelable(false)
                .setPositiveButton("Selfie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        magicalCamera.takeFragmentPhoto(DrawerFragment.this);
                        dialog.dismiss();

                    }
                })
                .show();
    }

    private void setPhoto(String url) {

        Picasso.with(getContext()).load(url).centerCrop().fit().into(avatar);

    }

    @Override
    public void emptyPhoto() {

        requestSelfie();

    }

    @Override
    public void availablePhoto(String url) {

        setPhoto(url);

    }
}
