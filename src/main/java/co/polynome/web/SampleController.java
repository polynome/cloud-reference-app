package co.polynome.web;

import co.polynome.domain.Rapper;
import co.polynome.service.RapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;

@Controller
public class SampleController {
    @Autowired
    private RapperRepository rappers;

    @RequestMapping("/")
    @ResponseBody
    @Transactional(readOnly = true)
    String list() throws SQLException {
        final Page<Rapper> rappers = this.rappers.findAll(new PageRequest(0, 10));
        StringBuilder result = new StringBuilder("<html><body>");
        result.append("<ul>");
        for (final Rapper r : rappers) {
            result.append("<li>");
            result.append(r.getName());
            result.append("</li>");
        }
        result.append("</ul>");
        result.append("</body></html>");


        return result.toString();
    }
}
