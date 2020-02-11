package ua.training.web.verification.request;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class BuySubmitRequestVerifier implements Verifier {
    @Override
    public boolean verify(HttpServletRequest request) {
        return Objects.isNull(request.getSession().getAttribute("order"))
                || Objects.isNull(request.getSession().getAttribute("cruise"));

    }
}
