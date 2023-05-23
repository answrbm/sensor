package ansarbektassov.SensorRestApp.repositories;

import ansarbektassov.SensorRestApp.dto.MeasurementDTO;
import ansarbektassov.SensorRestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findAllByRaining(boolean isRaining);
}
