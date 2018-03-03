package in.nanoelectron.foodwastemanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ravi on 3/3/18.
 */

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.MyViewHolder> {

    private ArrayList<Donations> donationsList;
    private View itemView;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView email,num_people,expiry_time;

        public MyViewHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.don_org_name);
            num_people = (TextView) view.findViewById(R.id.don_no_people);
            expiry_time = (TextView) view.findViewById(R.id.expiry_time);
        }
    }

    public DonationsAdapter(Context context,ArrayList<Donations> donationsList) {
        this.donationsList = donationsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donations_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Donations donation = donationsList.get(position);
        holder.email.setText(donation.getEmail());
        holder.num_people.setText(donation.getNum_people()+"");
        holder.expiry_time.setText(donation.getExpiry_time().toString());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = donationsList.get(position).getEmail();
                Intent intent = new Intent(context,DisplayAcceptor.class);
                intent.putExtra("email",email);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return donationsList.size();
    }
}
