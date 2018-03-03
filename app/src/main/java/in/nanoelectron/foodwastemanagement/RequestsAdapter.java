package in.nanoelectron.foodwastemanagement;

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

    public RequestsAdapter(ArrayList<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView email,num_people,waiting_time;

        public MyViewHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.req_org_name);
            num_people = (TextView) view.findViewById(R.id.req_no_people);
            waiting_time = (TextView) view.findViewById(R.id.waiting_time);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requests_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Requests request = requestsList.get(position);
        holder.email.setText(request.getEmail());
        holder.num_people.setText(request.getNum_people());
        holder.waiting_time.setText(request.getWaiting_time());
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }
}
