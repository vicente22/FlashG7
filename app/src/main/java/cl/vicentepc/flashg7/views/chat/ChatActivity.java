package cl.vicentepc.flashg7.views.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cl.vicentepc.flashg7.R;
import cl.vicentepc.flashg7.chats.ChatsFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String key = getIntent().getStringExtra(ChatsFragment.CHAT_KEY);
        String email = getIntent().getStringExtra(ChatsFragment.CHAT_RECEIVER);

        getSupportActionBar().setTitle(email);

    }
}
