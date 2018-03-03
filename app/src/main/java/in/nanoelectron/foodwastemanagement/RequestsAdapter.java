package in.nanoelectron.foodwastemanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ravi on 3/3/18.
 */

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder> {

    private ArrayList<Requests> requestsList;
    private View itemView;
    private Context context;

    public RequestsAdapter(Context context,ArrayList<Requests> requestsList)
    {
        this.context = context;
        this.requestsList = requestsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView email,num_people,donar;

        public MyViewHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.req_org_name);
            num_people = (TextView) view.findViewById(R.id.req_no_people);
            donar = (TextView) view.findViewById(R.id.donar_status);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requests_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Requests request = requestsList.get(position);
        holder.email.setText(request.getEmail());
        holder.num_people.setText(request.getNum_people()+"");
        holder.donar.setText(request.getDonor());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = requestsList.get(position).getEmail();
                Intent intent = new Intent(context,DisplayDonor.class);
                intent.putExtra("email",email);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return requestsList.size();
    }
}
