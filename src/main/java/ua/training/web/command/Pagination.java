package ua.training.web.command;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

public class Pagination {


    public static final String PAGE_ATTR = "page";
    public static final String SIZE_ATTR = "size";
    public static final String NUMBER_OF_PAGES_ATTR = "numberOfPages";
    public static final String COMMAND_ATTR = "command";

    public void paginate(int page, int size, long entries, HttpServletRequest request, String command) {
        int numberOfPages = (int) Math.ceil((double) entries / size);

        request.setAttribute(PAGE_ATTR, page);
        request.setAttribute(SIZE_ATTR, size);
        request.setAttribute(NUMBER_OF_PAGES_ATTR, numberOfPages);
        request.setAttribute(COMMAND_ATTR, command);
    }

    public boolean validatePaginationParams(String page, String size) {
        if (isNull(page) || isNull(size)) {
            return false;
        }
        int pageVal;
        int sizeVal;
        try {
            pageVal = Integer.parseInt(page);
            sizeVal = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return false;
        }
        return pageVal > 0 && sizeVal > 0;
    }

}
