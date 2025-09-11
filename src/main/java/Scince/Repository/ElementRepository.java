package Scince.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ElementRepository extends JpaRepository<Element, Integer> {

    Optional<Element> findByName(String name);

    @Query(value = "select atomic_mass from elements where id = :id", nativeQuery = true)
    Double getAtomicMassById(@Param("id") int id);
}
