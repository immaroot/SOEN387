package ca.concordia.model;

import java.util.Date;

public class Vote {
    private int choice;
    private Date date;

    public Vote(int choice, Date date) {
        this.choice = choice;
        this.date = date;
    }

    public int getChoice() {
        return choice;
    }

    public Date getDate() {
        return date;
    }
}
