package com.magnetocorp.application.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Response that contains the data related to the stats (information already processed and persisted)
 * 
 * @author Nadia Mirra
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsResponse implements Serializable {

    private static final long serialVersionUID = -2924345347686560636L;

    private long countMutantDna;
    private long countHumanDna;
    private double ratio;

}
