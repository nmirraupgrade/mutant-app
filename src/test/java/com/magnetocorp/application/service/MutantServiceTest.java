package com.magnetocorp.application.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.magnetocorp.application.config.TestConfig;
import com.magnetocorp.application.domain.Dna;
import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.repository.DnaRepository;
import com.magnetocorp.application.service.MutantService;
import com.magnetocorp.application.validator.DnaValidator;

import org.junit.Assert;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  classes = { TestConfig.class }, 
  loader = AnnotationConfigContextLoader.class)
public class MutantServiceTest {

    @InjectMocks
    private MutantService service;

    @Mock
    private DnaRepository repository;

    @Mock
    private DnaValidator validator;

    @Test
    public void isMutantTest() {

        // Test mutant example 6X6
        String[] valid6x6 = {"atgcga", "cagtgc", "ttatgt", "agaagg", "ccccta", "tcactg"};
        String validSequence = "atgcgacagtgcttatgtagaaggcccctatcactg";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertTrue(service.isMutant(valid6x6));

        // Test human example 6X6
        String[] invalid6x6 = {"atgcga", "cagtgc", "ttattt", "agacgg", "gcgtca", "tcactg"};
        validSequence = "atgcgacagtgcttatttagacgggcgtcatcactg";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertFalse(service.isMutant(invalid6x6));

        // Test mutant example 8x8
        String[] valid8x8 = {"ctagtact", "aaaagttc", "tgagttcc", "gaataggg", "ataagccg", "aaaagggg", "tatatgat",
                             "cccgaccc"};
        validSequence = "ctagtactaaaagttctgagttccgaatagggataagccgaaaaggggtatatgatcccgaccc";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertTrue(service.isMutant(valid8x8));

        // Test human example 10x10
        String[] invalid10x10 = {"TCACCCGTAA", "TTTACTAAAT", "AATATCAAAT", "CGTATATATT", "TATTGATTTA", "GGGTGTTGAT",
                                 "TAGTGTATGA", "AATTTGATAG", "TGATTGTATA", "ATCCGTGAGT"};
        validSequence = "TCACCCGTAATTTACTAAATAATATCAAATCGTATATATTTATTGATTTAGGGTGTTGATTAGTGTATGAAATTTGATAG"
                        + "TGATTGTATAATCCGTGAGT";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertFalse(service.isMutant(invalid10x10));

        // Test mutant example 10x10
        String[] valid10x10 = {"TCACCCGTAA", "TTTACTAAAT", "AATAAAAAAT", "CGTATATATT", "TATTGATTTA", "GGGTGTTGAT",
                               "TAGTGTATGA", "AATTTGATAG", "TGATTGTATA", "ATCCGTGAGT"};
        validSequence = "TCACCCGTAATTTACTAAATAATAAAAAATCGTATATATTTATTGATTTAGGGTGTTGATTAGTGTATGAATTTGATAGTGATTGTATA"
                        + "ATCCGTGAGT";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertTrue(service.isMutant(valid10x10));

        String[] saved4x4 = {"TCAC", "TTTA", "AATT", "CGTA"};
        validSequence = "TCACTTTAAATTCGTA";
        when(repository.findBySequence(validSequence)).thenReturn(Dna.builder().isMutant(true).build());
        Assert.assertTrue(service.isMutant(saved4x4));

        String[] human4x4 = {"TCAC", "TTTA", "AATT", "CGTA"};
        validSequence = "TCACTTTAAATTCGTA";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertFalse(service.isMutant(human4x4));

        String[] mutant4x4 = {"AAAA", "TTTC", "CCCC", "ACTG"};
        validSequence = "AAAATTTCCCCC";
        when(repository.findBySequence(validSequence)).thenReturn(null);
        Assert.assertTrue(service.isMutant(mutant4x4));

        verify(repository, times(7)).save(any());
    }
    
    @Test
    public void getStatsTest() {
        when(repository.countByIsMutantFalse()).thenReturn(0L);
        when(repository.countByIsMutantTrue()).thenReturn(250L);

        StatsDto stats = service.getStats();
        Assert.assertNotNull(stats);
        Assert.assertEquals(0L, stats.getCountHumanDna());
        Assert.assertEquals(250L, stats.getCountMutantDna());
        Assert.assertTrue(stats.getRatio() == 1.0);

        when(repository.countByIsMutantFalse()).thenReturn(100L);
        when(repository.countByIsMutantTrue()).thenReturn(0L);
        stats = service.getStats();
        Assert.assertNotNull(stats);
        Assert.assertEquals(100L, stats.getCountHumanDna());
        Assert.assertEquals(0L, stats.getCountMutantDna());
        Assert.assertTrue(stats.getRatio() == 0);

        when(repository.countByIsMutantFalse()).thenReturn(100L);
        when(repository.countByIsMutantTrue()).thenReturn(40L);
        stats = service.getStats();
        Assert.assertNotNull(stats);
        Assert.assertEquals(100L, stats.getCountHumanDna());
        Assert.assertEquals(40L, stats.getCountMutantDna());
        Assert.assertTrue(stats.getRatio() == 0.4);
    }

}
