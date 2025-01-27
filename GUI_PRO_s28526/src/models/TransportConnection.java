package models;

public class TransportConnection {
    private Country from;
    private Country to;
    private TransportType transportType;

    private boolean isOpen;


    public TransportConnection(Country from, Country to, TransportType type) {
        this.from = from;
        this.to = to;
        this.transportType = type;
        this.isOpen = true;

    }



    public Country getFrom() {
        return from;
    }

    public Country getTo() {
        return to;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public boolean isOpen() {
        return isOpen;
    }



    public void setOpen(boolean open) {
        isOpen = open;
    }
}
