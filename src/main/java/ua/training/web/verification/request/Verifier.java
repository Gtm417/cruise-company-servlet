package ua.training.web.verification.request;

import javax.servlet.http.HttpServletRequest;

public interface Verifier {
    boolean verify(HttpServletRequest request);
}
