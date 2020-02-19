package ua.training.web.form.validation;

import org.junit.Test;
import ua.training.web.form.TicketForm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicketFormValidatorTest {
    TicketFormValidator formValidator = new TicketFormValidator();
    @Test
    public void validate() {
        TicketForm form = new TicketForm("NAME", "dad", "sdasd");
        assertFalse(formValidator.validate(form));
    }

    @Test
    public void validateRight() {
        TicketForm form = new TicketForm("NAME", "1", "1");
        assertTrue(formValidator.validate(form));
    }

    @Test
    public void validateNull() {
        TicketForm form = new TicketForm(null, null, null);
        assertFalse(formValidator.validate(form));
    }

    @Test
    public void validateEmpty() {
        TicketForm form = new TicketForm("", "", "");
        assertFalse(formValidator.validate(form));
    }


    @Test
    public void validateNotRightNumbers() {
        TicketForm form = new TicketForm("NAME", "-10", "31231");
        assertFalse(formValidator.validate(form));
    }

    @Test
    public void validateNotRightNumbersStruct() {
        TicketForm form = new TicketForm("NAME", "020", "002");
        assertFalse(formValidator.validate(form));
    }

    @Test
    public void validateRightNumbers() {
        TicketForm form = new TicketForm("NAME", "50", "10");
        assertTrue(formValidator.validate(form));
    }

}