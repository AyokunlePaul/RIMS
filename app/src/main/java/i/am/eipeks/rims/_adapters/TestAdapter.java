package i.am.eipeks.rims._adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import i.am.eipeks.rims.R;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Holder> {

    private Context context;
    private int totalNumberOfSeats;

    public TestAdapter(Context context, int totalNumberOfSeats){
        this.context = context;
        this.totalNumberOfSeats = totalNumberOfSeats;
    }

    @Override
    public TestAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((CardView) LayoutInflater.from(this.context).inflate(R.layout.activity_test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TestAdapter.Holder holder, int position) {
        holder.seatNumber.setText(String.format("%s", Integer.toString(position + 1)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.format("%s %s %s", "Seat", Integer.toString(holder.getAdapterPosition() + 1), "clicked"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.totalNumberOfSeats;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView seatNumber;
        CardView cardView;

        Holder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            seatNumber = cardView.findViewById(R.id.seat_number_test);
        }
    }


}
