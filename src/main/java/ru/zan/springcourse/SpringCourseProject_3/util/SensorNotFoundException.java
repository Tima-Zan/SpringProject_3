package ru.zan.springcourse.SpringCourseProject_3.util;

public class SensorNotFoundException extends RuntimeException{

    public SensorNotFoundException(String msg) {
        super(msg);
    }
}
