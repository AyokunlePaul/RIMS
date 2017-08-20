package i.am.eipeks.rims._adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._classes.Passenger;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;



public class PassengerInformationSection extends StatelessSection {

    private ArrayList<Passenger> passengers;
    private String header;

    public PassengerInformationSection(ArrayList<Passenger> passengers, String header) {
        super(new SectionParameters.Builder(R.layout.card_view_passenger_information_trip)
                .headerResourceId(R.layout.section_header).build());

        this.header = header;
        this.passengers = passengers;

    }

    @Override
    public int getContentItemsTotal() {
        return passengers.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        Passenger currentPassenger = passengers.get(position);

        if (currentPassenger.getPassengerSex().equals("Male")){
            itemHolder.sex.setText("M");
        } else {
            itemHolder.sex.setText("F");
        }

        itemHolder.passengerName.setText(currentPassenger.getPassengerName());
        itemHolder.passengerAddress.setText(currentPassenger.getPassengerAddress());
        itemHolder.passengerPhoneNumber.setText(currentPassenger.getPassengerPhone());
        itemHolder.passengerNextOfKin.setText(currentPassenger.getNextOfKin());
        itemHolder.passengerSeatNumber.setText(currentPassenger.getSeatNumber());
        itemHolder.passengerNextOfKinPhone.setText(currentPassenger.getNextOfKinPhone());
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderHolder headerHolder = (HeaderHolder) holder;

        headerHolder.header.setText(header);
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {

        TextView passengerName, passengerPhoneNumber, passengerSeatNumber,
                passengerAddress, passengerNextOfKin, passengerNextOfKinPhone;
        Button sex;

        ItemHolder(View itemView) {
            super(itemView);
            passengerName = (TextView) itemView.findViewById(R.id.card_view_passenger_name_passenger_information);
            passengerPhoneNumber = (TextView) itemView.findViewById(R.id.card_view_passenger_phone_number_passenger_information);
            passengerSeatNumber = (TextView) itemView.findViewById(R.id.card_view_passenger_seat_passenger_information);
            passengerAddress = (TextView) itemView.findViewById(R.id.card_view_passenger_address_passenger_information);
            passengerNextOfKin = (TextView) itemView.findViewById(R.id.card_view_passenger_next_of_kin_passenger_information);
            passengerNextOfKinPhone = (TextView) itemView.findViewById(R.id.card_view_passenger_next_of_kin_phone_number_passenger_information);

            sex = (Button) itemView.findViewById(R.id.card_view_passenger_sex_passenger_information);
        }
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {

        TextView header;

        HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.section_header);
        }
    }
}
