package com.magnetocorp.application.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.helper.TestHelper;
import com.magnetocorp.application.mapper.Mapper;
import com.magnetocorp.application.request.Request;
import com.magnetocorp.application.response.StatsResponse;
import com.magnetocorp.application.service.MutantService;

@RunWith(SpringJUnit4ClassRunner.class)
public class MutantControllerTest {

    @InjectMocks
    private MutantController controller = Mockito.spy(MutantController.class);

    @Mock
    private MutantService service;

    @Spy
    private TestHelper helper;

    @Spy
    private Mapper mapper;

    @Test
    public void testMutant() {
        String[] mutantDna = {"atgcga", "cagtgc", "ttatgt", "agaagg", "ccccta", "tcactg"};
        Request request = helper.createRequest(mutantDna);

        when(service.isMutant(mutantDna)).thenReturn(true);

        ResponseEntity<Object> response = controller.isMutant(request);
        assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testHuman() {
        String[] mutantDna = {"atgcga", "cagtgc", "ttattt", "agacgg", "gcgtca", "tcactg"};
        Request request = helper.createRequest(mutantDna);

        when(service.isMutant(mutantDna)).thenReturn(false);

        ResponseEntity<Object> response = controller.isMutant(request);
        assertNotNull(response);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testStats() {
        StatsDto response = helper.createStatsDto(40, 100);
        when(service.getStats()).thenReturn(response);

        StatsResponse statsResponse = controller.stats();
        assertNotNull(statsResponse);
        Assert.assertEquals(40L, statsResponse.getCountMutantDna());
        Assert.assertEquals(100L, statsResponse.getCountHumanDna());
        Assert.assertTrue(statsResponse.getRatio() == 0.4);
    }

}
