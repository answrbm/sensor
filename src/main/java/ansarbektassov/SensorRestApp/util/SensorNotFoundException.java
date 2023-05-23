package ansarbektassov.SensorRestApp.util;

public class SensorNotFoundException extends RuntimeException {

    public SensorNotFoundException() {}

    public SensorNotFoundException(String message) {
        super(message);
    }
}
