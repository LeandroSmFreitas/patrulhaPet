package br.com.manager.patrulhapet.repository;

import br.com.manager.patrulhapet.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {


}
