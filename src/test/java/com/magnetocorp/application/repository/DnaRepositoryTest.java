package com.magnetocorp.application.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.magnetocorp.application.config.TestConfig;
import com.magnetocorp.application.domain.Dna;
import com.magnetocorp.application.helper.TestHelper;
import com.magnetocorp.application.repository.DnaRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  classes = { TestConfig.class }, 
  loader = AnnotationConfigContextLoader.class)
public class DnaRepositoryTest {

  @Autowired
  private DnaRepository repository;

  @Spy
  private TestHelper helper;

  @Test
  public void testRepo() {
    Dna mutantDna1 = helper.createMutant("atgcgacagtgcttatgtagaaggcccctatcacgg");
    Dna savedDna = repository.save(mutantDna1);
    Assert.assertNotNull(savedDna);
    Assert.assertTrue(savedDna.getSequence().equals("atgcgacagtgcttatgtagaaggcccctatcacgg"));

    Dna mutantDna2 = helper.createMutant("atgcgacagggcccctatcacgg");
    repository.save(mutantDna2);
    Dna mutantDna3 = helper.createMutant("atgcgacagtgctagaaggcccctatcacgg");
    repository.save(mutantDna3);

    Dna humanDna = helper.createHuman("atgcccctatcacgg");
    repository.save(humanDna);

    Long humans = repository.countByIsMutantFalse();
    Assert.assertNotNull(humans);
    Assert.assertTrue(humans.equals(1L));
    Long mutants = repository.countByIsMutantTrue();
    Assert.assertNotNull(mutants);
    Assert.assertTrue(mutants.equals(3L));

    Dna found = repository.findBySequence("atgcgacagtgctagaaggcccctatcacgg");
    Assert.assertNotNull(found);
    Assert.assertTrue(found.getIsMutant());

    found = repository.findBySequence("atacgg");
    Assert.assertNull(found);
  }

}
