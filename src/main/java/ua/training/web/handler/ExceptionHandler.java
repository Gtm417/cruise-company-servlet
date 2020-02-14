package ua.training.web.handler;

import javax.servlet.http.HttpServletRequest;

public class ExceptionHandler {
    // for logging
    private Exception exception;
    private String page;

    public ExceptionHandler(Exception exception, String page) {
        this.exception = exception;
        this.page = page;
    }

    public String handling(HttpServletRequest request) {
        //todo log
        request.getSession().setAttribute("exception", true);
        return "redirect:" + page;
    }
}
