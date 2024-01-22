package ru.zan.springcourse.SpringCourseProject_3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.zan.springcourse.SpringCourseProject_3.DTO.MeasurementsDTO;
import ru.zan.springcourse.SpringCourseProject_3.DTO.MeasurementsResponse;
import ru.zan.springcourse.SpringCourseProject_3.model.Measurements;
import ru.zan.springcourse.SpringCourseProject_3.service.MeasurementsService;
import ru.zan.springcourse.SpringCourseProject_3.util.ErrorResponse;
import ru.zan.springcourse.SpringCourseProject_3.util.MeasurementsValidator;
import ru.zan.springcourse.SpringCourseProject_3.util.SensorNotCreatedException;
import ru.zan.springcourse.SpringCourseProject_3.util.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final MeasurementsValidator measurementsValidator;

    private final ModelMapper modelMapper;
    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementsValidator measurementsValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementsValidator = measurementsValidator;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public MeasurementsResponse getMeasurements(){
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementsDTO).collect(Collectors.toList())) ;
    }
    @GetMapping("/rainyDaysCount")
    public  Long getRainyDaysCount(){
       return measurementsService.findAll().stream().filter(Measurements::isRaining).count();
    }
    @PostMapping("/add")
    public HttpEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementsDTO measurementsDTO,
                                                  BindingResult bindingResult){
        Measurements measurements = convertToMeasurements(measurementsDTO);
        measurementsValidator.validate(measurements,bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError error : fieldErrors){
                errorMsg.append(error.getField()).append("-")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotFoundException(errorMsg.toString());
        }
        measurementsService.save(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurements) {
        return  modelMapper.map(measurements, MeasurementsDTO.class);
    }
    private Measurements convertToMeasurements(MeasurementsDTO measurementsDTO) {
        return  modelMapper.map(measurementsDTO, Measurements.class);
    }

}
