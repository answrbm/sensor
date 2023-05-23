package ansarbektassov.SensorRestApp.dto;

import ansarbektassov.SensorRestApp.models.Sensor;
import jakarta.validation.constraints.*;

public class MeasurementDTO {

    @NotNull
    @Min(value = -100, message = "Value should be between -100 and 100")
    @Max(value = 100,message = "Value should be between -100 and 100")
    private double value;
    @NotNull
    private boolean raining;
    @NotNull
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
