package com.magnetocorp.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magnetocorp.application.domain.Dna;

/**
 * DNA repository
 * 
 * @author Nadia Mirra
 */
public interface DnaRepository extends JpaRepository<Dna, Long> {

    Long countByIsMutantTrue();

    Long countByIsMutantFalse();

    Dna findBySequence(String sequence);

}