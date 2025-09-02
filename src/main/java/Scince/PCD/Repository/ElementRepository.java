package Scince.PCD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElementRepository extends JpaRepository<Element, Integer> {

    Optional<Element> findByName(String name);
}
