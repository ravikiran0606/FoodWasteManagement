package in.nanoelectron.foodwastemanagement;

import java.util.Date;

/**
 * Created by ravi on 3/3/18.
 */

public class Requests {
    private  String email,donor;
    private  int num_people;
    private Date waiting_time;

    public Requests(){

    }

    public Requests(String email, String donor, int num_people) {
        this.email = email;
        this.donor = donor;
        this.num_people = num_people;
    }

    public String getWaiting_time() {
        return waiting_time.toString();
    }

    public void setWaiting_time(Date waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public int getNum_people() {
        return num_people;
    }

    public void setNum_people(int num_people) {
        this.num_people = num_people;
    }
}
