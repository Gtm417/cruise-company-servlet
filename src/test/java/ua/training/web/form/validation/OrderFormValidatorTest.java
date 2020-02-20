package ua.training.web.form.validation;

import org.junit.Test;
import ua.training.web.form.OrderForm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderFormValidatorTest {
    Validator<OrderForm> validator = new OrderFormValidator();

    @Test
    public void validate() {
        OrderForm orderForm = new OrderForm("Timofey", "Hodik", "1");
        assertFalse(validator.validate(orderForm));
    }

    @Test
    public void validateNull() {
        OrderForm orderForm = new OrderForm(null, null, null);
        assertTrue(validator.validate(orderForm));
    }

    @Test
    public void validateEmpty() {

        OrderForm orderForm = new OrderForm("", "", "");
        assertTrue(validator.validate(orderForm));
    }

    @Test
    public void validateOnlyNamesRights() {
        OrderForm orderForm = new OrderForm("Timofey", "Hodik", "dada");
        assertTrue(validator.validate(orderForm));
    }

    @Test
    public void stringParamValidate() {
        assertFalse(validator.stringParamValidate("Timofey", "[A-Z][a-z]+"));
    }

    @Test
    public void stringParamValidateTicket() {
        assertFalse(validator.stringParamValidate("1", "[1-9]{1}[0-9]*"));
    }
}