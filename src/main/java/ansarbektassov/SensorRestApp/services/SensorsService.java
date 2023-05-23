package ansarbektassov.SensorRestApp.services;

import ansarbektassov.SensorRestApp.models.Sensor;
import ansarbektassov.SensorRestApp.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorsService {

    private final SensorsRepository repository;

    @Autowired
    public SensorsService(SensorsRepository repository) {
        this.repository = repository;
    }

    public void save(Sensor sensor) {
        this.repository.save(sensor);
    }

    public List<Sensor> findAll() {
        return repository.findAll();
    }

    public Optional<Sensor> findById(int id) {
        return repository.findById(id);
    }

    public Optional<Sensor> findByName(String name) {
        return repository.findByName(name);
    }
}
