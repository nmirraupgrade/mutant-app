package com.magnetocorp.application.helper;

import org.springframework.stereotype.Component;

import com.magnetocorp.application.domain.Dna;
import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.request.Request;

@Component
public class TestHelper {

    public Dna createMutant(String sequence) {
        return Dna.builder().sequence(sequence).isMutant(true).build();
    }

    public Dna createHuman(String sequence) {
        return Dna.builder().sequence(sequence).isMutant(false).build();
    }

    public Request createRequest(String[] dna) {
        return Request.builder().dna(dna).build();
    }

    public StatsDto createStatsDto(long mutants, long humans) {
        return StatsDto.builder()
                       .countHumanDna(humans)
                       .countMutantDna(mutants)
                       .ratio((double) mutants/humans).build();
    }

}
