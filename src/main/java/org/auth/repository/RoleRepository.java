/**
 * 
 */
package org.auth.repository;

import java.util.UUID;

import org.auth.entity.Role;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Garvit
 *
 */
@ApplicationScoped
public class RoleRepository implements  PanacheRepository<Role>{

	
	public Role findByName(String name) {
		return find("name", name).firstResult();
	}
	
}

