package ru.zan.springcourse.SpringCourseProject_3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zan.springcourse.SpringCourseProject_3.model.Sensor;
import ru.zan.springcourse.SpringCourseProject_3.repository.SensorRepositories;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepositories sensorRepositories;
    @Autowired
    public SensorService(SensorRepositories sensorRepositories) {
        this.sensorRepositories = sensorRepositories;
    }
    @Transactional
    public void save (Sensor sensor){
        sensorRepositories.save(sensor);
    }

    public Optional<Sensor> getSensorFindByName(String name){
        return sensorRepositories.findByName(name);
    }




}
