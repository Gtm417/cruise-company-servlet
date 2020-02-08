package ua.training.controller.verification.request;

import ua.training.exception.AccessDenied;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class BuySubmitRequestVerify implements Verify {
    @Override
    public void verify(HttpServletRequest request) {
        if (Objects.isNull(request.getSession().getAttribute("order"))
                || Objects.isNull(request.getSession().getAttribute("cruise"))
                || Objects.isNull(request.getSession().getAttribute("selectedExcursions"))) {
            request.setAttribute("notAllData", true);
            //todo log
          throw new AccessDenied("Not Enough Data For Request");
        }
    }
}
