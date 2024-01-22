package ru.zan.springcourse.SpringCourseProject_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurements")
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="measurements_id")
    private Integer measurementsId;
    @Column(name = "value")
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double value;
    @Column(name = "raining")
    @NotNull
    private Boolean raining;
    @Column(name = "measurements_date_time")
    @NotNull
    private LocalDateTime measurementsDateTime;
    @ManyToOne
    @JoinColumn(name = "sensor",referencedColumnName = "name")
    private Sensor sensor;

    public Integer getMeasurementsId() {
        return measurementsId;
    }

    public void setMeasurementsId(Integer measurementsId) {
        this.measurementsId = measurementsId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getMeasurementsDateTime() {
        return measurementsDateTime;
    }

    public void setMeasurementsDateTime(LocalDateTime measurementsDateTime) {
        this.measurementsDateTime = measurementsDateTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
