package ua.training.web.verification.request;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static ua.training.web.AttributeConstants.SESSION_CRUISE_ATTR;
import static ua.training.web.AttributeConstants.SESSION_ORDER_ATTR;

public class BuySubmitRequestVerifier implements Verifier {
    @Override
    public boolean verify(HttpServletRequest request) {
        return Objects.isNull(request.getSession().getAttribute(SESSION_ORDER_ATTR))
                || Objects.isNull(request.getSession().getAttribute(SESSION_CRUISE_ATTR));

    }
}
