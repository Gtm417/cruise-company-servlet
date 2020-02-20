package ua.training.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.dao.CruiseDao;
import ua.training.entity.Cruise;
import ua.training.entity.Ship;
import ua.training.exception.CruiseNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CruiseServiceTest {
    private static final Cruise CRUISE = Cruise.builder().id(1L).name("test").descriptionEng("english").descriptionRu("russian").build();
    private static final Ship SHIP = Ship.builder().name("name").maxPassengerAmount(1000).build();

    @Rule
    public ExpectedException exception;
    @InjectMocks
    CruiseService cruiseService;
    @Mock
    CruiseDao cruiseDao;


    @Test
    public void getAllCruises() {
        when(cruiseDao.findAll()).thenReturn(Collections.singletonList(CRUISE));

        List<Cruise> actual = cruiseService.getAllCruises();

        verify(cruiseDao, times(1)).findAll();
        Assert.assertThat(actual, is(not(Collections.emptyList())));
    }

    @Test
    public void changeCruiseDescription() {

        when(cruiseDao.update(CRUISE)).thenReturn(true);
        boolean actual = cruiseService.updateCruise(CRUISE);

        verify(cruiseDao, times(1)).update(CRUISE);
        Assert.assertTrue(actual);
    }


    @Test
    public void findCruiseByIdIfExist() throws CruiseNotFoundException {
        long id = 1L;
        when(cruiseDao.findById(id)).thenReturn(Optional.of(CRUISE));

        Cruise cruise = cruiseService.findById(id);

        verify(cruiseDao, times(1)).findById(ArgumentMatchers.eq(id));
        Assert.assertEquals(CRUISE, cruise);
    }

    @Test(expected = CruiseNotFoundException.class)
    public void findCruiseByIdIfNotFound() throws CruiseNotFoundException {
        when(cruiseDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        cruiseService.findById(1L);
    }

    @Test
    public void checkAmountPassengerIfMore() {
        SHIP.setCurrentPassengerAmount(1000);
        CRUISE.setShip(SHIP);

        boolean actual = cruiseService.checkAmountPassenger(CRUISE);

        Assert.assertFalse(actual);
    }

    @Test
    public void checkAmountPassengerIfLess() {
        SHIP.setCurrentPassengerAmount(100);
        CRUISE.setShip(SHIP);

        boolean actual = cruiseService.checkAmountPassenger(CRUISE);

        Assert.assertTrue(actual);
    }

}