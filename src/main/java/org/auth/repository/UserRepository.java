package org.auth.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;
import org.auth.entity.Users;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Users> {
  
  public Users findByUsername(String username) {
    return find("username", username).firstResult();
  }
  
  public Optional<Users> findByUsernameOrEmail(String usernameOrEmail) {
    return find("username = ?1 or email = ?1", usernameOrEmail).firstResultOptional();
  }
 
  public Users findByEmail(String email) {
    return find("email", email).firstResult();
  }
}
