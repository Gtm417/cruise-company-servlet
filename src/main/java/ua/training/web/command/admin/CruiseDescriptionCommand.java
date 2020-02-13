package ua.training.web.command.admin;

import ua.training.entity.Cruise;
import ua.training.service.CruiseService;
import ua.training.web.command.Command;
import ua.training.web.command.CommandUtility;
import ua.training.web.form.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CruiseDescriptionCommand implements Command {

    private final CruiseService cruiseService;

    public CruiseDescriptionCommand(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Cruise cruise = CommandUtility.checkCruiseInSession(request);
        if (!getRequestValidator().validate(request)) {
            request.setAttribute("errors", true);
            return "edit-description.jsp";
        }
        String descriptionRu = request.getParameter("descriptionRu");
        String descriptionEng = request.getParameter("descriptionEng");

        cruise.setDescriptionEng(descriptionEng);
        cruise.setDescriptionRu(descriptionRu);
        cruiseService.updateCruise(cruise);
        return "edit-description.jsp";
    }

    private boolean validForm(HttpServletRequest request) {
        return !validateParam(request.getParameter("descriptionEng")) &&
                !validateParam(request.getParameter("descriptionRu"));
    }

    private boolean validateParam(String param) {
        return !Objects.isNull(param) && param.isEmpty();
    }

    private Validator<HttpServletRequest> getRequestValidator() {
        return this::validForm;
    }


}
