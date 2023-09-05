package wanted.structure.Instagram_clone.api.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.structure.Instagram_clone.api.auth.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
        Optional<Auth> findAuthByUserId(Long userId);
}
