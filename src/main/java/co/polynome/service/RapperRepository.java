package co.polynome.service;

import co.polynome.domain.Rapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface RapperRepository extends Repository<Rapper, Long> {
    Page<Rapper> findAll(Pageable pageable);
    Rapper save(Rapper rapper);
}
