package com.magnetocorp.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.mapper.Mapper;
import com.magnetocorp.application.request.Request;
import com.magnetocorp.application.response.StatsResponse;
import com.magnetocorp.application.service.MutantService;

/**
 * Contoller that provides the endpoints of the mutant application
 * 
 * @author Nadia Mirra
 */

@RestController
public class MutantController {

    @Autowired
    private MutantService mutantService;

    @Autowired
    private Mapper mapper;

    /**
     * Endpoint that receives a request and calls the service, in order to know if a DNA is mutant or not.
     * 
     * @param request contains the DNA to be analyzed
     * @return an empty body with two different Http status. OK is returned if the DNA matches the mutant criteria,
     *         Http status FORBIDDEN is returned otherwise
     */
    @PostMapping("/mutant")
    public ResponseEntity<Object> isMutant(@RequestBody Request request) {
        boolean mutant = mutantService.isMutant(request.getDna());
        if (mutant) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Endpoint that returns the stats of all the DNAs analyzed
     * 
     * @return StatsResponse containing the data related to the stats
     */
    @GetMapping("/stats")
    public StatsResponse stats() {
        StatsDto stats = mutantService.getStats();
        return mapper.map(stats);
    }

}