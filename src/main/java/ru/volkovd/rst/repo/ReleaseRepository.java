package ru.volkovd.rst.repo;

import org.springframework.data.repository.CrudRepository;
import ru.volkovd.rst.models.Issue;
import ru.volkovd.rst.models.Release;

public interface ReleaseRepository extends CrudRepository<Release, Long> {
}
