package ua.training.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.dao.OrderDao;
import ua.training.entity.Order;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.exception.SaveOrderException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    private static final User USER = buildUser();
    private final static Order ORDER = buildOrder();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    OrderService service;

    @Mock
    OrderDao orderDao;

    private static User buildUser() {
        return User.builder()
                .id(1L)
                .login("login")
                .password("pass")
                .role(Role.USER)
                .balance(1000L)
                .build();
    }

    private static Order buildOrder() {
        return Order.builder()
                .firstName("test")
                .secondName("test")
                .orderPrice(1000)
                .user(USER)
                .build();
    }


    @Test
    public void showAllPassengersOnCruise() {

        when(orderDao.findAllOrdersByCruise(anyLong())).thenReturn(Collections.singletonList(ORDER));
        List<Order> actual = service.showAllPassengersOnCruise(1L);
        verify(orderDao, times(1)).findAllOrdersByCruise(anyLong());

        Assert.assertThat(actual, is(not(Collections.emptyList())));
    }

    @Test
    public void showAllUserOrders() {
        when(orderDao.findAllOrdersByUserWithOffsetAndLimit(anyInt(), anyInt(), anyLong())).thenReturn(Collections.singletonList(ORDER));
        List<Order> actual = service.showAllUserOrders(1, 5, 1L);

        verify(orderDao, times(1)).findAllOrdersByUserWithOffsetAndLimit(0, 5, 1L);

        Assert.assertThat(actual, is(not(Collections.emptyList())));
    }


    @Test
    public void buyCruise() throws SaveOrderException {
        service.buyCruise(ORDER);
        verify(orderDao, times(1)).buyInTransaction(ORDER);
    }


    @Test
    public void countAllOrders() {
        when(orderDao.countOrdersByUserId(anyLong())).thenReturn(5);

        int actual = service.countAllOrders(1L);

        verify(orderDao, times(1)).countOrdersByUserId(1L);
        Assert.assertEquals(5, actual);

    }

    @Test
    public void subUserBalance() {
        long price = 100L;
        long actual = service.subUserBalance(USER, price);

        Assert.assertEquals(USER.getBalance() - price, actual);
    }
}