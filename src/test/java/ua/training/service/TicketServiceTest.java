package ua.training.service;

import org.junit.Test;
import ua.training.exception.TicketsEmptyListException;
import ua.training.model.entity.Ticket;

public class TicketServiceTest {

    @Test
    public void showTicketsForBuy() {
        TicketService ticketService = new TicketService();
        try {
            ticketService.showTicketsForBuy(1L).stream().map(Ticket::getCruise).forEach(System.out::println);
        } catch (TicketsEmptyListException e) {
            e.printStackTrace();
        }

    }
}