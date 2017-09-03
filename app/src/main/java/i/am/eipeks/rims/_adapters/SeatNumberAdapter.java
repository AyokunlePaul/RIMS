package i.am.eipeks.rims._adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import i.am.eipeks.rims.R;


public class SeatNumberAdapter extends RecyclerView.Adapter<SeatNumberAdapter.Holder> {

    private Context context;
    private int totalNumberOfSeats;
    private int selectedSeat;
    private String selectedSeats;
    private List<String> selectedSeatsList;

    public SeatNumberAdapter(Context context, int totalNumberOfSeats, String selectedSeats){
        this.context = context;
        this.totalNumberOfSeats = totalNumberOfSeats;
        selectedSeatsList = Arrays.asList(selectedSeats.split("_"));
    }

    @Override
    public SeatNumberAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((CardView) LayoutInflater.from(this.context).inflate(R.layout.activity_test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final SeatNumberAdapter.Holder holder, int position) {
        holder.seatNumber.setText(String.format("%s", Integer.toString(position + 1)));
        if (selectedSeats.contains(Integer.toString(position + 1))){
            holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.black));
            holder.cardView.setEnabled(false);
            holder.cardView.setClickable(false);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSeats.contains(Integer.toString(holder.getAdapterPosition() + 1))) {
                    if ((holder.getAdapterPosition() + 1) == selectedSeat){
                        holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.black));
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                        selectSeat(0);
                        selectedSeatsList.remove(String.valueOf(holder.getAdapterPosition() + 1));
                    }
                    Snackbar.make(view, "Seat taken", Snackbar.LENGTH_SHORT).show();
                } else {
                    holder.seatNumber.setTextColor(context.getResources().getColor(android.R.color.white));
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                    selectSeat(holder.getAdapterPosition() + 1);
                    selectedSeatsList.add(String.valueOf(holder.getAdapterPosition() + 1));
                }
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

    public String getSeats(){
        StringBuilder value = new StringBuilder();
        for (String currentSeat: selectedSeatsList){
            value.append(currentSeat).append("_");
        }
        return value.deleteCharAt(value.lastIndexOf("_")).toString();
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
