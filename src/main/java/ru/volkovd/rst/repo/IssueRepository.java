package ru.volkovd.rst.repo;

import org.springframework.data.repository.CrudRepository;
import ru.volkovd.rst.models.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {
}
