package config.repositories;

import config.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    //Test[] getAll();
}
