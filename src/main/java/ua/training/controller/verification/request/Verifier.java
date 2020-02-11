package ua.training.controller.verification.request;

import javax.servlet.http.HttpServletRequest;

public interface Verifier {
    boolean verify(HttpServletRequest request);
}
