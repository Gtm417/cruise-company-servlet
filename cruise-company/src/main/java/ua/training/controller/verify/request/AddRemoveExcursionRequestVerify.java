package ua.training.controller.verify.request;

import ua.training.exception.UnreachableRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AddRemoveExcursionRequestVerify implements Verify {
    @Override
    public void verify(HttpServletRequest request) {
        try{
            if(Objects.isNull(request.getSession().getAttribute("selectedExcursions"))){
                throw new UnreachableRequest();
            }
            Long.parseLong(request.getParameter("id"));
        }catch (NumberFormatException ex){
            throw new UnreachableRequest();
        }
    }
}
