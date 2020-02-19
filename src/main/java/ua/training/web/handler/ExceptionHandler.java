package ua.training.web.handler;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.EXCEPTION_SESSION_ATTR;
import static ua.training.web.CommandConstants.REDIRECT_COMMAND;

public class ExceptionHandler {
    private static final String DEFAULT_REQUEST_TYPE = "forward";

    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class);

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
        LOGGER.info(exception.getMessage());
        request.getSession().setAttribute(EXCEPTION_SESSION_ATTR, true);
        if (requestType.equals(REDIRECT_COMMAND)) {
            return REDIRECT_COMMAND + page;
        }
        return page;
    }
}
