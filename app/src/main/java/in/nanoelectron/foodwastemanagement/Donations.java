package in.nanoelectron.foodwastemanagement;


import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by ravi on 3/3/18.
 */

public class Donations {
    private String email,type_food,acceptor;
    private Date expiry_time;
    private int num_people;
    private ArrayList<String>requests;

    public Donations(){
    }

    public Donations(String email,String type_food,int num_people,Date expiry_time) {
        this.email = email;
        this.type_food = type_food;
        this.num_people = num_people;
        this.expiry_time = expiry_time;
        requests = new ArrayList<>();
    }

    public String getExpiry_time() {
        return expiry_time.toString();
    }

    public void setExpiry_time(Date expiry_time) {
        this.expiry_time = expiry_time;
    }

    public String getEmail() {
        return email;
    }

    public String getType_food() {
        return type_food;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public int getNum_people() {
        return num_people;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType_food(String type_food) {
        this.type_food = type_food;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public void setNum_people(int num_people) {
        this.num_people = num_people;
    }

    public void addRequest(String email){
        requests.add(email);
    }
}
