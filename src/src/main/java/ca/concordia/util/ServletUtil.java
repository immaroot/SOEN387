package ca.concordia.util;

import ca.concordia.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
    public static void respond(Result result, HttpServletResponse response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String content = mapper.writeValueAsString(result);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
