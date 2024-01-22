package ru.zan.springcourse.SpringCourseProject_3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.zan.springcourse.SpringCourseProject_3.DTO.SensorDTO;
import ru.zan.springcourse.SpringCourseProject_3.model.Sensor;
import ru.zan.springcourse.SpringCourseProject_3.service.SensorService;
import ru.zan.springcourse.SpringCourseProject_3.util.ErrorResponse;
import ru.zan.springcourse.SpringCourseProject_3.util.SensorNotCreatedException;
import ru.zan.springcourse.SpringCourseProject_3.util.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    private final ModelMapper modelMapper;
    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        Sensor sensor = convertToPerson(sensorDTO);
        sensorValidator.validate(sensor,bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError error : fieldErrors){
                errorMsg.append(error.getField()).append("-")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }


    private Sensor convertToPerson(SensorDTO sensorDTO) {
        return  modelMapper.map(sensorDTO, Sensor.class);
    }


}
