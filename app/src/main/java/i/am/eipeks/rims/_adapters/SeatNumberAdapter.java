package i.am.eipeks.rims._adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import i.am.eipeks.rims.R;


public class SeatNumberAdapter extends RecyclerView.Adapter<SeatNumberAdapter.Holder> {

    private Context context;
    private int totalNumberOfSeats;
    private int selectedSeat;
    private ArrayList<Integer> selectedSeatsList;
    private boolean hasSelectedOnce;

    public SeatNumberAdapter(Context context, int totalNumberOfSeats, ArrayList<Integer> seatNumberArray){
        this.context = context;
        this.totalNumberOfSeats = totalNumberOfSeats;
        selectedSeatsList = new ArrayList<>();
        for (int counter = 0; counter < totalNumberOfSeats; counter++) {
            this.selectedSeatsList.add(counter + 1);
        }
        for (int seatAlreadySelected : seatNumberArray) {
            this.selectedSeatsList.remove(Integer.valueOf(seatAlreadySelected));
        }
        hasSelectedOnce = false;
    }

    @Override
    public void onViewRecycled(Holder holder) {
        super.onViewRecycled(holder);
        this.selectedSeat = 0;
        hasSelectedOnce = false;
    }

    @Override
    public SeatNumberAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((CardView) LayoutInflater.from(this.context).inflate(R.layout.seat_number_item, parent, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(final SeatNumberAdapter.Holder holder, int position) {
        holder.seatNumber.setText(String.format("%s", Integer.toString(position + 1)));
        if (!selectedSeatsList.contains(holder.getAdapterPosition() + 1)){
            holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
            holder.cardView.setEnabled(false);
            holder.cardView.setClickable(false);
        } else {
            holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, String.valueOf(holder.getAdapterPosition() + 1) + " clicked", Snackbar.LENGTH_SHORT).show();
                if (hasSelectedOnce) {
                    if ((holder.getAdapterPosition() + 1) == selectedSeat) {
                        holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.black));
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                        selectSeat(0);
                        selectedSeatsList.add(holder.getAdapterPosition() + 1);
                        hasSelectedOnce = false;
                    } else {
                        Snackbar.make(view, "You can't select 2 seats", Snackbar.LENGTH_SHORT).show();
                        hasSelectedOnce = true;
                    }
                } else {
                    if (!selectedSeatsList.contains(holder.getAdapterPosition() + 1)) {
                        Snackbar.make(view, "Seat taken", Snackbar.LENGTH_SHORT).show();
                        hasSelectedOnce = false;
                    } else {
                        holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.white));
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.black));
                        selectSeat(holder.getAdapterPosition() + 1);
                        selectedSeatsList.remove(Integer.valueOf(holder.getAdapterPosition() + 1));
                        hasSelectedOnce = true;
                    }
                }

//                Toast.makeText(context, String.valueOf(getSelectedSeat()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.totalNumberOfSeats;
    }

    private void selectSeat(int seatNumber){
        this.selectedSeat = seatNumber;
    }

    public int getSelectedSeat(){
        return this.selectedSeat;
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
