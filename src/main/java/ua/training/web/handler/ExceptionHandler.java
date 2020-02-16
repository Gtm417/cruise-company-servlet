package ua.training.web.handler;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.CommandConstants.REDIRECT_COMMAND;

public class ExceptionHandler {
    public static final String DEFAULT_REQUEST_TYPE = "forward";

    // for logging
    private Exception exception;
    private String page;
    private String requestType;

    public ExceptionHandler(Exception exception, String page) {
        this.exception = exception;
        this.page = page;
        this.requestType = DEFAULT_REQUEST_TYPE;
    }

    public ExceptionHandler(Exception exception, String page, String requestType) {
        this.exception = exception;
        this.page = page;
        this.requestType = requestType;
    }

    public String handling(HttpServletRequest request) {
        //todo log
        request.getSession().setAttribute("exception", true);
        if (requestType.equals(REDIRECT_COMMAND)) {
            return "redirect:" + page;
        }
        return page;
    }
}
