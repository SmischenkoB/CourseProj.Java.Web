package config.repositories;

import config.Entities.UserToTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTOTestRepository extends JpaRepository<UserToTest, Long> {
}
