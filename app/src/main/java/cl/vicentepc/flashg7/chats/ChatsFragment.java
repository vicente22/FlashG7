package cl.vicentepc.flashg7.chats;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.twitter.sdk.android.core.models.Place;

import java.util.Objects;

import cl.vicentepc.flashg7.adapters.ChatsAdapter;
import cl.vicentepc.flashg7.R;
import cl.vicentepc.flashg7.adapters.ChatsListener;
import cl.vicentepc.flashg7.data.Nodes;
import cl.vicentepc.flashg7.models.Chat;
import cl.vicentepc.flashg7.views.chat.ChatActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment implements ChatsListener{

    private RecyclerView recyclerView;

    public static final String CHAT_KEY = "cl.vicentepc.flashg7.chats.KEY.CHAT_KEY";
    public static final String CHAT_RECEIVER = "cl.vicentepc.flashg7.chats.KEY.CHAT_RECEIVER";


    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewChats);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));

        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(new Nodes().chats(), Chat.class)
                .build();

        ChatsAdapter chatsAdapter = new ChatsAdapter(this, options);

        recyclerView.setAdapter(chatsAdapter);

    }

    @Override
    public void clicked(String key, String email) {
        //Toast.makeText(getContext(), "Hola: " + email, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(CHAT_KEY, key);
        intent.putExtra(CHAT_RECEIVER, email);

        startActivity(intent);


    }
}
