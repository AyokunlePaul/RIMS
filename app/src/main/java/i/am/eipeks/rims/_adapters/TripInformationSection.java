package i.am.eipeks.rims._adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import i.am.eipeks.rims.R;
import i.am.eipeks.rims._classes._model_class.SubTrip;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;



public class TripInformationSection extends StatelessSection {

    private SubTrip subTrip;
    private String header;
    private Context context;

    public TripInformationSection(Context context, SubTrip subTrip, String header) {
        super(new SectionParameters.Builder(R.layout.card_view_trip_information_item)
                .headerResourceId(R.layout.section_header).build());
        this.subTrip = subTrip;
        this.header = header;
        this.context = context;
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

        itemHolder.dateAndTime.setText(subTrip.getTrip().getDateAndTime());
        itemHolder.departure.setText(String.format("%s: %s", "Departure", subTrip.getTrip().getDeparture()));
        itemHolder.displacement.setText(subTrip.getTrip().getDisplacement());
        itemHolder.driver.setText(String.format("%s: %s", "Driver", subTrip.getDriver().getDriverName()));
        itemHolder.contact.setText(String.format("%s: %s", "Contact", subTrip.getDriver().getDriverPhone()));
//        Toast.makeText(context, subTrip.getDriver().getDriverName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderHolder headerHolder = (HeaderHolder) holder;
        headerHolder.header.setText(header);
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {

        TextView departure, displacement, driver, dateAndTime, contact;

        ItemHolder(View itemView) {
            super(itemView);

            departure = (TextView) itemView.findViewById(R.id.departure_card_view_trip);
            displacement = (TextView) itemView.findViewById(R.id.displacement_card_view_trip);
            driver = (TextView) itemView.findViewById(R.id.driver_card_view_trip);
            dateAndTime = (TextView) itemView.findViewById(R.id.date_and_time_card_view_trip);
            contact = (TextView) itemView.findViewById(R.id.contact_card_view_trip);

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
