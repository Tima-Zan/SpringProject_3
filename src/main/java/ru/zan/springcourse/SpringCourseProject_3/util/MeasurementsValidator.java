package ru.zan.springcourse.SpringCourseProject_3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zan.springcourse.SpringCourseProject_3.model.Measurements;
import ru.zan.springcourse.SpringCourseProject_3.service.MeasurementsService;
import ru.zan.springcourse.SpringCourseProject_3.service.SensorService;

@Component
public class MeasurementsValidator implements Validator {


    private final SensorService sensorService;
    @Autowired
    public MeasurementsValidator( SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurements.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurements measurements = (Measurements) target;
        if(measurements.getSensor() == null){
            return;
        }
        if (sensorService.getSensorFindByName(measurements.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor","","A sensor with this name was not found");
        }
    }
}
