package cl.vicentepc.flashg7.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.vicentepc.flashg7.R;
import cl.vicentepc.flashg7.models.Chat;

public class ChatsAdapter extends FirebaseRecyclerAdapter<Chat, ChatsAdapter.ChatsHolder> {

    private ChatsListener listener;

    public ChatsAdapter(ChatsListener listener, @NonNull FirebaseRecyclerOptions<Chat> options) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChatsHolder holder, int position, @NonNull Chat model) {
        Picasso.with(holder.photoBiv.getContext()).load(model.getPhoto());
        holder.emailTv.setText(model.getReceiver());

        if(model.isNotification()){
            holder.notificationV.setVisibility(View.VISIBLE);
        }else{
            holder.notificationV.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat auxChat = getItem(holder.getAdapterPosition());
                listener.clicked(auxChat.getKey(), auxChat.getReceiver());
            }
        });

    }

    @NonNull
    @Override
    public ChatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent,false);
        return new ChatsHolder(view);
    }

    public static class ChatsHolder extends RecyclerView.ViewHolder {

        private BubbleImageView photoBiv;
        private TextView emailTv;
        private View notificationV;

        public ChatsHolder(View itemView) {
            super(itemView);

            emailTv = itemView.findViewById(R.id.emailTv);
            notificationV = itemView.findViewById(R.id.notificationV);
            photoBiv = itemView.findViewById(R.id.photoBiv);



        }
    }

}
