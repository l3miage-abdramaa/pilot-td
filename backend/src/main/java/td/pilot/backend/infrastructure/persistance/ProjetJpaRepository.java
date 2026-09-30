package td.pilot.backend.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface ProjetJpaRepository extends JpaRepository<ProjetEntity, UUID> {

    Optional<ProjetEntity> findByCode(String code);

    boolean existsByCode(String code);
    
}
