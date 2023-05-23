package ansarbektassov.SensorRestApp.controllers;

import ansarbektassov.SensorRestApp.dto.MeasurementDTO;
import ansarbektassov.SensorRestApp.models.Measurement;
import ansarbektassov.SensorRestApp.models.Sensor;
import ansarbektassov.SensorRestApp.services.MeasurementsService;
import ansarbektassov.SensorRestApp.services.SensorsService;
import ansarbektassov.SensorRestApp.util.MeasurementErrorResponse;
import ansarbektassov.SensorRestApp.util.MeasurementNotCreatedException;
import ansarbektassov.SensorRestApp.util.SensorErrorResponse;
import ansarbektassov.SensorRestApp.util.SensorNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(
            MeasurementsService measurementsService, SensorsService sensorsService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Integer getRainyDaysCount() {
        return measurementsService.findRainyDays().stream().map(this::convertToMeasurementDTO).toList().size();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(
            @RequestBody @Validated MeasurementDTO measurementDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMessage.append(error.getField())
                            .append("-").append(error.getDefaultMessage())
                            .append(";");
            }
            throw new MeasurementNotCreatedException(errorMessage.toString());
        }

        Optional<Sensor> sensor = sensorsService.findByName(measurementDTO.getSensor().getName());
        Measurement measurement = convertToMeasurement(measurementDTO);
        if(sensor.isPresent())
            measurement.setSensor(sensor.get());
        else
            throw new SensorNotFoundException("Sensor not found");
        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return this.modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return this.modelMapper.map(measurement,MeasurementDTO.class);
    }

}
