package com.magnetocorp.application.mapper;

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
import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.helper.TestHelper;
import com.magnetocorp.application.response.StatsResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(
        classes = { TestConfig.class }, 
        loader = AnnotationConfigContextLoader.class)
public class MapperTest {

    @Autowired
    private Mapper mapper;

    @Spy
    private TestHelper helper;

    @Test
    public void testMapper() {
        StatsResponse mapped = mapper.map(null);
        Assert.assertNull(mapped);

        StatsDto dto = helper.createStatsDto(50, 100);
        mapped = mapper.map(dto);
        Assert.assertNotNull(mapped);
        Assert.assertEquals(100, mapped.getCountHumanDna());
        Assert.assertEquals(50, mapped.getCountMutantDna());
        Assert.assertTrue(mapped.getRatio() == 0.5);
    }

}
