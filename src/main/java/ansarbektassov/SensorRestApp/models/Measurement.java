package ansarbektassov.SensorRestApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "value")
    @NotNull
    @Min(value = -100, message = "Value should be between -100 and 100")
    @Max(value = 100,message = "Value should be between -100 and 100")
    private double value;
    @Column(name = "raining")
    @NotNull
    private boolean raining;

    @Column(name = "measured_at")
    private LocalDateTime measured_at;

    @ManyToOne
    @JoinColumn(name = "sensor_name",referencedColumnName = "name")
    private Sensor sensor;

    public Measurement() {}

    public Measurement(double value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getMeasured_at() {
        return measured_at;
    }

    public void setMeasured_at(LocalDateTime measured_at) {
        this.measured_at = measured_at;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", measured_at=" + measured_at +
                ", sensor=" + sensor +
                '}';
    }
}
