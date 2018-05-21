package com.magnetocorp.application.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO that contains data related to the stats (information already processed and persisted)
 * 
 * @author Nadia Mirra
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsDto implements Serializable {

    private static final long serialVersionUID = -2924345347686560636L;

    private long countMutantDna;
    private long countHumanDna;
    private double ratio;

}
