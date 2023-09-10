package org.auth.entity;

import java.util.UUID;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

    @Column(nullable = false , unique = true)
    private String name;
    
}
