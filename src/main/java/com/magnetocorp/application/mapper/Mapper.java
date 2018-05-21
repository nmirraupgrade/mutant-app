package com.magnetocorp.application.mapper;

import org.springframework.stereotype.Component;

import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.response.StatsResponse;

/**
 * Model mapper
 * 
 * @author Nadia Mirra
 */
@Component
public class Mapper {

    public StatsResponse map(StatsDto dto) {
        if (dto != null) {
            return StatsResponse.builder()
                    .countHumanDna(dto.getCountHumanDna())
                    .countMutantDna(dto.getCountMutantDna())
                    .ratio(dto.getRatio())
                    .build();
        }
        return null;

    }

}
