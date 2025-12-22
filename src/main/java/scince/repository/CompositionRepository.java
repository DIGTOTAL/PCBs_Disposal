package scince.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition, Integer> {
    List<Composition> findByLowerHeatingValueOfCompositionBetween(double min, double max);
}
