package exodia.repository;

import exodia.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    User findByUsernameAndPassword(String username, String password);
}
