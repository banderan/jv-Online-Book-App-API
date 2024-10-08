package org.example.jvspringbootfirstbook.repository.user;

import java.util.Optional;
import org.example.jvspringbootfirstbook.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

    Optional<User> findByShippingAddress(String shippingAddress);
}
