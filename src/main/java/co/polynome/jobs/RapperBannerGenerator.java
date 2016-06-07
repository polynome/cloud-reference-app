package co.polynome.jobs;

import co.polynome.domain.Rapper;
import co.polynome.service.RapperRepository;
import com.github.lalyos.jfiglet.FigletFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RapperBannerGenerator {
    @Autowired
    public RapperRepository rapperRepository;

    // TODO make transactional
    public void run(Long primaryKey) {
        try {
            final Rapper rapper = rapperRepository.findOne(primaryKey);
            rapper.setBanner(FigletFont.convertOneLine(rapper.getName()));
            rapperRepository.save(rapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
