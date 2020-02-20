package ua.training.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.dao.ExcursionDao;
import ua.training.entity.Cruise;
import ua.training.entity.Excursion;
import ua.training.exception.ExcursionNotFound;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExcursionServiceTest {
    private static final Excursion EXCURSION = Excursion.builder()
            .excursionName("name")
            .price(100L)
            .duration(4)
            .build();
    private static final long ID = 1L;

    @InjectMocks
    ExcursionService excursionService;

    @Mock
    ExcursionDao excursionDao;


    @Test
    public void showAllExcursionsInCruise() {
        when(excursionDao.findAllExcursionsByCruiseId(anyLong())).thenReturn(Collections.singletonList(EXCURSION));
        List<Excursion> actual = excursionService.showAllExcursionsInCruise(Cruise.builder().id(ID).build());

        Assert.assertThat(actual, is(not(Collections.emptyList())));
    }

    @Test
    public void findById() throws ExcursionNotFound {
        when(excursionDao.findById(ID)).thenReturn(Optional.of(EXCURSION));

        Excursion excursion = excursionService.findById(ID);

        verify(excursionDao, times(1)).findById(ArgumentMatchers.eq(ID));
        Assert.assertEquals(EXCURSION, excursion);
    }

    @Test(expected = ExcursionNotFound.class)
    public void findByIdIfNotFound() throws ExcursionNotFound {
        when(excursionDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        excursionService.findById(1L);
    }

    @Test
    public void getTotalSumExcursionSetIfEmptySet() {
        Set<Excursion> excursions = Collections.emptySet();

        long actual = excursionService.getTotalSumExcursionSet(excursions);

        Assert.assertEquals(0, actual);
    }

    @Test
    public void getTotalSumExcursionSet() {
        Set<Excursion> excursions = new HashSet<>();
        Excursion excursion = Excursion.builder().excursionName("test").duration(10).id(100L).price(100L).build();
        excursions.add(EXCURSION);
        excursions.add(excursion);

        long actual = excursionService.getTotalSumExcursionSet(excursions);

        Assert.assertEquals(EXCURSION.getPrice() + excursion.getPrice(), actual);
    }

}