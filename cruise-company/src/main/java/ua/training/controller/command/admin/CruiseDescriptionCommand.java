package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.model.entity.Cruise;
import ua.training.service.CruiseService;

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
        String descriptionRu = request.getParameter("descriptionRu");
        String descriptionEng = request.getParameter("descriptionEng");
        if (!valid(descriptionEng, descriptionRu)) {
            return "edit-description.jsp";
        }
        cruise.setDescriptionEng(descriptionEng);
        cruise.setDescriptionRu(descriptionRu);
        cruiseService.updateCruise(cruise);
        return "edit-description.jsp";
    }

    private boolean valid(String descriptionEng, String descriptionRu) {
        if (Objects.isNull(descriptionEng) || Objects.isNull(descriptionRu)) {
            return false;
        }
        return true;
    }
}
