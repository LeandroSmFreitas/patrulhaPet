package br.com.manager.patrulhapet.repository;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {


    List<Animal> findByUser(User user);
}
