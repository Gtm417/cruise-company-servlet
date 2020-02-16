package ua.training.web.form.validation;

import ua.training.web.form.UserForm;


public class UserFormValidator implements Validator<UserForm> {

    public static final String LOGIN_REGEX = "^[A-Za-z0-9_-]{3,16}$";
    public static final String PASSWORD_REGEX = "^[A-Za-z0-9_-]{5,18}$";


    @Override
    public boolean validate(UserForm form) {

        return stringParamValidate(form.getLogin(), LOGIN_REGEX)
                || stringParamValidate(form.getPassword(), PASSWORD_REGEX);
    }


}