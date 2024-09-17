package org.blazejherzog.jobmatcher.web;

import lombok.NonNull;
import org.springframework.web.servlet.ModelAndView;

public class ResponseView extends ModelAndView {

    private static final String REDIRECT_PREFIX = "redirect:";

    private ResponseView(String viewName) {
        super(viewName);
        if (!viewName.contains(REDIRECT_PREFIX)) {
            getModel().put("menu", MenuView.of(viewName));
        }
    }

    public static ResponseView of(@NonNull String viewName) {
        return new ResponseView(viewName);
    }

    public static ResponseView redirect(@NonNull String viewName) {
        return new ResponseView(String.format("%s%s", REDIRECT_PREFIX, viewName));
    }

    public ResponseView add(String key, Object value) {
        getModel().put(key, value);
        return this;
    }
}
