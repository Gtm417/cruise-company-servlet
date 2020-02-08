package ua.training.controller.form.validation;

import org.junit.Test;
import ua.training.controller.form.TicketForm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicketFormValidatorTest {

    @Test
    public void validate() {
        TicketForm form = new TicketForm("NAME", "dad", "sdasd");
        TicketFormValidator formValidator = new TicketFormValidator();
        assertTrue(!formValidator.validate(form));
    }

    @Test
    public void validateRight() {
        TicketForm form = new TicketForm("NAME", "1", "1");
        TicketFormValidator formValidator = new TicketFormValidator();
        assertFalse(!formValidator.validate(form));
    }

    @Test
    public void validateNull() {
        TicketForm form = new TicketForm(null, null, null);
        TicketFormValidator formValidator = new TicketFormValidator();
        assertTrue(!formValidator.validate(form));
    }

    @Test
    public void validateEmpty() {
        TicketForm form = new TicketForm("", "", "");
        TicketFormValidator formValidator = new TicketFormValidator();
        assertTrue(!formValidator.validate(form));
    }


    @Test
    public void validateNotRightNumbers() {
        TicketForm form = new TicketForm("NAME", "-10", "31231");
        TicketFormValidator formValidator = new TicketFormValidator();
        assertTrue(!formValidator.validate(form));
    }

    @Test
    public void validateNotRightNumbersStruct() {
        TicketForm form = new TicketForm("NAME", "020", "002");
        TicketFormValidator formValidator = new TicketFormValidator();
        assertTrue(!formValidator.validate(form));
    }

    @Test
    public void validateRightNumbers() {
        TicketForm form = new TicketForm("NAME", "50", "10");
        TicketFormValidator formValidator = new TicketFormValidator();
        assertFalse(!formValidator.validate(form));
    }

}