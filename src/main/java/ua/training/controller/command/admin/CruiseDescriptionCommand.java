package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.validation.RequestParameterValidator;
import ua.training.model.entity.Cruise;
import ua.training.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CruiseDescriptionCommand implements Command {
    private final CruiseService cruiseService;
    private final RequestParameterValidator validator;

    public CruiseDescriptionCommand(CruiseService cruiseService, RequestParameterValidator validator) {

        this.cruiseService = cruiseService;
        this.validator = validator;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Cruise cruise = CommandUtility.checkCruiseInSession(request);
        if (!validator.validate(request).isEmpty()){
            System.out.println(validator.getValidationMessages());
            request.setAttribute("errors", true);
            return "edit-description.jsp";
        }
        String descriptionRu = request.getParameter("descriptionRu");
        String descriptionEng = request.getParameter("descriptionEng");
//        if (!valid(descriptionEng, descriptionRu)) {
//            return "edit-description.jsp";
//        }
        cruise.setDescriptionEng(descriptionEng);
        cruise.setDescriptionRu(descriptionRu);
        cruiseService.updateCruise(cruise);
        return "edit-description.jsp";
    }

    private boolean valid(String descriptionEng, String descriptionRu) {
        return !Objects.isNull(descriptionEng) && !Objects.isNull(descriptionRu);
    }
}
