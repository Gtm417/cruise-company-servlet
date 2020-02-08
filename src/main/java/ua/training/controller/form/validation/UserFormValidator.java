package ua.training.controller.form.validation;

import ua.training.controller.form.UserForm;


public class UserFormValidator implements Validator<UserForm> {

    public static final String LOGIN_REGEX = "^[A-Za-z0-9_-]{3,16}$";
    public static final String PASSWORD_REGEX = "^[A-Za-z0-9_-]{5,18}$";

    public static final String WRONG_NAME_MESSAGE = "Login should contains latin letters and numbers. Min values of characters 3. Max values of characters 16";
    public static final String WRONG_PASSWORD_MESSAGE = "Password should contains latin letters and numbers. Min values of characters 6. Max values of characters 18";


    @Override
    public boolean validate(UserForm form) {

        return stringParamValidate(form.getLogin(), LOGIN_REGEX)
                || stringParamValidate(form.getPassword(), PASSWORD_REGEX);
    }


}
