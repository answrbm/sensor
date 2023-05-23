package ansarbektassov.SensorRestApp.controllers;

import ansarbektassov.SensorRestApp.dto.SensorDTO;
import ansarbektassov.SensorRestApp.models.Sensor;
import ansarbektassov.SensorRestApp.services.SensorsService;
import ansarbektassov.SensorRestApp.util.SensorErrorResponse;
import ansarbektassov.SensorRestApp.util.SensorNotCreatedException;
import ansarbektassov.SensorRestApp.util.SensorNotFoundException;
import ansarbektassov.SensorRestApp.validators.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Sensor> getSensors() {
        return this.sensorsService.findAll();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Validated SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor,bindingResult);
        System.out.println(sensorDTO);
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMessage.append(error.getField())
                            .append("-").append(error.getDefaultMessage())
                            .append(";");
            }
            throw new SensorNotCreatedException(errorMessage.toString());
        }
        sensorsService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return this.modelMapper.map(sensorDTO,Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return this.modelMapper.map(sensor,SensorDTO.class);
    }
}
