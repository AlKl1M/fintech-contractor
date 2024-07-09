package com.alkl1m.contractor.domain.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alkl1m
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "contractor", name = "country")
public class Country {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
