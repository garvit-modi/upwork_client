/**
 *
 */
package org.auth;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.auth.entity.ROLES;
import org.auth.entity.Role;
import org.auth.repository.RoleRepository;
import org.jboss.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Base class that runs just after the application starts.
 * It initializes roles in the application upon startup.
 * 
 * @author Garvit
 */
@ApplicationScoped
@Startup
public class BaseApplication {

  @Inject
  Logger logger;

  @Inject
  private RoleRepository roleRepository;

  /**
   * This method is called just after the application starts.
   * 
   * @param event The startup event.
   */
  void onStart(@Observes StartupEvent event) {
    logger.info("--Startup method call--");
    initializeRoles();
  }

  /**
   * Initializes roles by adding them to the repository.
   */
  @Transactional
  public void initializeRoles() {
    var roles = roleRepository.listAll();

    // Save roles to the database if roles are not present
    if (roles.isEmpty()) {
      logger.info("Save roles to the database");
      Role roleUser = Role.builder().name(ROLES.USER.toString()).build();
      Role roleAdmin = Role.builder().name(ROLES.ADMIN.toString()).build();
      roleRepository.persistAndFlush(roleAdmin);
      roleRepository.persistAndFlush(roleUser);
    }
  }

  @Produces
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
