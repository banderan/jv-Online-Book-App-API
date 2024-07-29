package org.example.jvspringbootfirstbook.repository.role;

import org.example.jvspringbootfirstbook.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
