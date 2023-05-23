package ansarbektassov.SensorRestApp.services;

import ansarbektassov.SensorRestApp.models.Measurement;
import ansarbektassov.SensorRestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementsService {

    private final MeasurementsRepository repository;

    @Autowired
    public MeasurementsService(MeasurementsRepository repository) {
        this.repository = repository;
    }

    public void save(Measurement measurement) {
        measurement.setMeasured_at(LocalDateTime.now());
        repository.save(measurement);
    }

    public List<Measurement> findAll() {
        return repository.findAll();
    }

    public List<Measurement> findRainyDays() {
        return repository.findAllByRaining(true);
    }

    public Optional<Measurement> findById(int id) {
        return repository.findById(id);
    }
}
