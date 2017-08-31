package i.am.eipeks.rims._adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._classes.MiniTripInfo;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


public class HomeSection extends StatelessSection {


    private ArrayList<MiniTripInfo> miniTripInfos;
    private String header;
    private Context context;
    private Intent intent;

    public static final String INTENT_UUID = "UUID";
    public static final String INTENT_REGISTRATION_NUMBER = "registrationNumber";

    public HomeSection(Context context, Intent intent, String header, ArrayList<MiniTripInfo> miniTripInfos) {
        super(new SectionParameters.Builder(R.layout.home_information_card_view)
        .headerResourceId(R.layout.section_header)
        .build());

        this.miniTripInfos = miniTripInfos;
        this.header = header;
        this.context = context;
        this.intent = intent;
    }

    @Override
    public int getContentItemsTotal() {
        return miniTripInfos.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        final MiniTripInfo currentInfo = miniTripInfos.get(position);
        itemViewHolder.vehicleName.setText(currentInfo.getVehicleName());
        itemViewHolder.dateAndTime.setText(String.format("%s", currentInfo.getDateAndTime()));
        itemViewHolder.passengers.setText(String.format("%s-%s", currentInfo.getNumberOfPassengers(), "passengers"));
        itemViewHolder.driversName.setText(currentInfo.getDriversName());
        itemViewHolder.displacement.setText(currentInfo.getDisplacement());

        itemViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent
                .putExtra(INTENT_UUID, currentInfo.getUuid())
                .putExtra(INTENT_REGISTRATION_NUMBER, currentInfo.getVehicle().getRegistrationNumber())
                .putExtra(Constants.CALLING_ACTIVITY, Constants.HOME_ACTIVITY));
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

        headerViewHolder.header.setText(this.header);

    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.section_header);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView vehicleName, dateAndTime, driversName, displacement, passengers;
        View rootView;

        ItemViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;

            vehicleName = rootView.findViewById(R.id.vehicle_name_home_text_view);
            dateAndTime = rootView.findViewById(R.id.date_and_time_home_card_view);
            driversName = rootView.findViewById(R.id.driver_s_name_home_card_view);
            displacement = rootView.findViewById(R.id.displacement_home_card_view);
            passengers = rootView.findViewById(R.id.total_number_of_passengers_home_card_view);

        }
    }
}
