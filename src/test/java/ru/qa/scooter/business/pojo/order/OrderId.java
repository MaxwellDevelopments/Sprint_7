package ru.qa.scooter.business.pojo.order;

public class OrderId {
    private int track;

    public OrderId(int track) {
        this.track = track;
    }

    public OrderId() {
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }
}
