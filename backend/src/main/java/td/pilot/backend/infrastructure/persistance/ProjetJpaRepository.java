package td.pilot.backend.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;
import java.util.Optional;

public interface ProjetJpaRepository extends JpaRepository<ProjetEntity, UUID> {

    Optional<ProjetEntity> findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT MAX(p.code) FROM ProjetEntity p WHERE p.code LIKE :prefixe")
    Optional<String> trouverDernierCode(@Param("prefixe") String prefixe);
    
}
