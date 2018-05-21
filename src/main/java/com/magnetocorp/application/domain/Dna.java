package com.magnetocorp.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a DNA entity
 * 
 * @author Nadia Mirra
 */

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dna {

    @Id
    @Column
    private String sequence;

    @Column
    private Boolean isMutant;

}