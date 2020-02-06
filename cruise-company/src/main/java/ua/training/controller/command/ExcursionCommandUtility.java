package ua.training.controller.command;

import ua.training.controller.command.verify.request.Verify;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.ExcursionNotFound;
import ua.training.exception.UnreachableRequest;
import ua.training.model.entity.Excursion;
import ua.training.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ExcursionCommandUtility {
    private final Verify verify;
    private final ExcursionService excursionService;

    public ExcursionCommandUtility(Verify verify, ExcursionService excursionService) {
        this.verify = verify;
        this.excursionService = excursionService;
    }


     public Excursion validAndGetExcursion(HttpServletRequest request) throws ExcursionNotFound {
        verify.verify(request);
        long id = Long.parseLong(request.getParameter("id"));
        return excursionService.findById(id);
    }

}
