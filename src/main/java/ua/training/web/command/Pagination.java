package ua.training.web.command;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

public interface Pagination {

    default void paginate(int page, int size, long entries, HttpServletRequest request, String command) {
        int numberOfPages = (int) Math.ceil((double) entries / size);

        request.setAttribute("page", page);
        request.setAttribute("size", size);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("command", command);
    }

    default boolean validatePaginationParams(String page, String size) {
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
