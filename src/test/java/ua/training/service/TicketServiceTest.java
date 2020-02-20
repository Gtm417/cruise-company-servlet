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
import ua.training.dao.TicketDao;
import ua.training.entity.Cruise;
import ua.training.entity.Ticket;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.TicketNotFound;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {
    public static final Cruise CRUISE = Cruise.builder().id(1L).name("test").descriptionEng("english").descriptionRu("russian").build();
    public static final Ticket TICKET = Ticket.builder().ticketName("Test").price(1000L).discount(10).build();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @InjectMocks
    TicketService ticketService;
    @Mock
    TicketDao ticketDao;


    @Test
    public void addNewTicket() throws DuplicateDataBaseException {

        ticketService.addNewTicket(TICKET);

        verify(ticketDao, times(1)).create(ArgumentMatchers.eq(TICKET));

        Assert.assertEquals(900L, TICKET.getPriceWithDiscount());
    }

    @Test(expected = DuplicateDataBaseException.class)
    public void addNewTicketWhenTicketExist() throws DuplicateDataBaseException {
        when(ticketDao.create(any(Ticket.class))).thenThrow(DuplicateDataBaseException.class);

        ticketService.addNewTicket(TICKET);
    }

    @Test
    public void findAllTicketsForBuy() {
        when(ticketDao.findAllByCruiseId(anyLong())).thenReturn(Collections.singletonList(TICKET));

        List<Ticket> actual = ticketService.findAllTicketsForBuy(CRUISE.getId());
        verify(ticketDao, times(1))
                .findAllByCruiseId(CRUISE.getId());

        Assert.assertThat(actual, is(not(Collections.emptyList())));
    }

    @Test
    public void findTicketByIdAndCruise() throws TicketNotFound {
        when(ticketDao.findByIdAndCruiseId(anyLong(), anyLong())).thenReturn(Optional.of(TICKET));
        Ticket actual = ticketService.findTicketByIdAndCruise(1L, 1L);

        verify(ticketDao, times(1)).findByIdAndCruiseId(1L, 1L);
        Assert.assertEquals(TICKET, actual);
    }

    @Test(expected = TicketNotFound.class)
    public void findTicketByIdAndCruiseWhenTicketNotFound() throws TicketNotFound {
        when(ticketDao.findByIdAndCruiseId(anyLong(), anyLong())).thenReturn(Optional.empty());
        ticketService.findTicketByIdAndCruise(1L, 1L);
    }
}