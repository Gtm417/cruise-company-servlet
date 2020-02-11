package ua.training.controller.form.validation;

import org.junit.Test;
import ua.training.controller.form.UserForm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserFormValidatorTest {
    Validator<UserForm> validator = new UserFormValidator();
    UserForm form;

    @Test
    public void validate() {
        form = new UserForm("Admin", "Admin");
        assertFalse(validator.validate(form));
    }

    @Test
    public void validateNotRights() {
        form = new UserForm("Adn", "min");
        assertTrue(validator.validate(form));
    }

    @Test
    public void validateOnlyOneParamRight() {
        form = new UserForm("Admin", "min");
        assertTrue(validator.validate(form));
    }

    @Test
    public void validateNulls() {
        form = new UserForm(null, null);
        assertTrue(validator.validate(form));
    }

    @Test
    public void validateEmpty() {
        form = new UserForm("", "");
        assertTrue(validator.validate(form));
    }
}