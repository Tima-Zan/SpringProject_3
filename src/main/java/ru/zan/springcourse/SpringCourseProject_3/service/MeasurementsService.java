package ru.zan.springcourse.SpringCourseProject_3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zan.springcourse.SpringCourseProject_3.model.Measurements;
import ru.zan.springcourse.SpringCourseProject_3.repository.MeasurementsRepositories;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepositories measurementsRepositories;
    private final SensorService sensorService;
    @Autowired
    public MeasurementsService(MeasurementsRepositories measurementsRepositories, SensorService sensorService) {
        this.measurementsRepositories = measurementsRepositories;
        this.sensorService = sensorService;
    }

    public List<Measurements> findAll(){
        return measurementsRepositories.findAll();
    }
    @Transactional
    public void save(Measurements measurements){
        enrichMeasurements(measurements);
        measurementsRepositories.save(measurements);
    }

    public void enrichMeasurements(Measurements measurements){
        measurements.setSensor(sensorService.getSensorFindByName(measurements.getSensor().getName()).get());
        measurements.setMeasurementsDateTime(LocalDateTime.now());
    }

}
