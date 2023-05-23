package ansarbektassov.SensorRestApp.validators;

import ansarbektassov.SensorRestApp.dto.SensorDTO;
import ansarbektassov.SensorRestApp.models.Sensor;
import ansarbektassov.SensorRestApp.services.SensorsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorsService service;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorValidator(SensorsService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        Sensor sensor = (Sensor) target;

        Optional<Sensor> foundSensor = service.findByName(sensor.getName());
        if(foundSensor.isPresent())
            errors.rejectValue("name","","Sensor with such name already exists");
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return this.modelMapper.map(SensorDTO.class,Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return this.modelMapper.map(Sensor.class,SensorDTO.class);
    }
}
