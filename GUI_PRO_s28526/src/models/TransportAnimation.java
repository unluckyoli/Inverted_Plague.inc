package models;

public class TransportAnimation {

    private TransportConnection connection;
    private double progress;
    private double speed;
    private int direction;

    public TransportAnimation(TransportConnection connection, double speed) {

        this.connection = connection;
        this.speed = speed;
        this.progress = 0.0;

        this.direction = 1;
    }

    public void update() {

        progress += direction * speed;

        if (progress > 1.0) {
            progress = 1.0;
            direction = -1;


        } else if (progress < 0.0) {
            progress = 0.0;
            direction = 1;
        }
    }



    public TransportConnection getConnection() {
        return connection;
    }

    public double getProgress() {
        return progress;
    }
}



