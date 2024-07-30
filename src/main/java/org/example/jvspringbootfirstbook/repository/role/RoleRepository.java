package org.example.jvspringbootfirstbook.repository.role;

import org.example.jvspringbootfirstbook.model.Role;
import org.example.jvspringbootfirstbook.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByName(RoleName name);
}
