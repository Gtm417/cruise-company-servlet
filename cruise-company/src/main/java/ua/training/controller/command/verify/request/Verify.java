package ua.training.controller.command.verify.request;

import javax.servlet.http.HttpServletRequest;

public interface Verify {
    void verify(HttpServletRequest request);
}
