package i.am.eipeks.rims._adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import i.am.eipeks.rims.R;
import i.am.eipeks.rims._classes.Vehicle;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;



public class VehicleInformationSection extends StatelessSection {

    private Vehicle vehicle;
    private String header;

    public VehicleInformationSection(Vehicle vehicle, String header) {
        super(new SectionParameters.Builder(R.layout.card_view_vehicle_information_item)
                .headerResourceId(R.layout.section_header).build());

        this.header = header;
        this.vehicle = vehicle;

    }

    @Override
    public int getContentItemsTotal() {
        return 1;
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

        itemHolder.vehicleName.setText(vehicle.getVehicleName());
        itemHolder.vehicleMake.setText(String.format("%s: %s", "Make", vehicle.getVehicleMake()));
        itemHolder.vehicleNumber.setText(String.format("%s: %s", "Vehicle No", vehicle.getVehicleEngine()));
        itemHolder.vehicleCapacity.setText(String.format("%s: %s %s", "Capacity", vehicle.getVehicleCapacity(), "Seater"));

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderHolder headerHolder = (HeaderHolder) holder;

        headerHolder.header.setText(header);

    }

    private static class ItemHolder extends RecyclerView.ViewHolder {

        TextView vehicleMake, vehicleName, vehicleNumber, vehicleCapacity;

        ItemHolder(View itemView) {
            super(itemView);
            vehicleName = (TextView) itemView.findViewById(R.id.owner_s_name_card_view_information);
            vehicleMake = (TextView) itemView.findViewById(R.id.vehicle_make_card_view_information);
            vehicleNumber = (TextView) itemView.findViewById(R.id.vehicle_number_card_view_information);
            vehicleCapacity = (TextView) itemView.findViewById(R.id.capacity_card_view_information);
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
