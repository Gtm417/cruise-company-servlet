package ua.training.web.form.validation;

import org.junit.Test;
import ua.training.web.form.OrderForm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderFormValidatorTest {

    @Test
    public void validate() {
        Validator<OrderForm> validator = new OrderFormValidator();
        OrderForm orderForm = new OrderForm("Timofey", "Hodik", "1");
        assertFalse(validator.validate(orderForm));
    }

    @Test
    public void validateNull() {
        Validator<OrderForm> validator = new OrderFormValidator();
        OrderForm orderForm = new OrderForm(null, null, null);
        assertTrue(validator.validate(orderForm));
    }

    @Test
    public void validateEmpty() {
        Validator<OrderForm> validator = new OrderFormValidator();
        OrderForm orderForm = new OrderForm("", "", "");
        assertTrue(validator.validate(orderForm));
    }

    @Test
    public void validateOnlyNamesRights() {
        Validator<OrderForm> validator = new OrderFormValidator();
        OrderForm orderForm = new OrderForm("Timofey", "Hodik", "dada");
        assertTrue(validator.validate(orderForm));
    }

    @Test
    public void stringParamValidate() {
        Validator validator = new OrderFormValidator();
        assertFalse(validator.stringParamValidate("Timofey", "[A-Z][a-z]+"));
    }

    @Test
    public void stringParamValidateTicket() {
        Validator validator = new OrderFormValidator();
        assertFalse(validator.stringParamValidate("1", "[1-9]{1}[0-9]*"));
    }
}