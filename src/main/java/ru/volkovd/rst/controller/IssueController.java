package ru.volkovd.rst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.volkovd.rst.models.Issue;
import ru.volkovd.rst.models.Release;
import ru.volkovd.rst.repo.IssueRepository;
import ru.volkovd.rst.repo.ReleaseRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class IssueController {
    private final IssueRepository issueRepository;
    private final ReleaseRepository releaseRepository;

    public IssueController(IssueRepository issueRepository, ReleaseRepository releaseRepository) {
        this.issueRepository = issueRepository;
        this.releaseRepository = releaseRepository;
    }

    @GetMapping("/issues")
    public String getIssues(Model model) {
        Iterable<Issue> issues = issueRepository.findAll();
        Iterable<Release> releases = releaseRepository.findAll();
        model.addAttribute("releases", releases);
        model.addAttribute("issues", issues);
        return "/pages/issue/issues";
    }

    @PostMapping("/issue")
    public String addIssue(@RequestParam Long issueNumber,
                           @RequestParam Release release) {
        Issue issue = new Issue(issueNumber, release);
        issueRepository.save(issue);
        return "redirect:/issues";
    }

    @DeleteMapping("/issue/{id}")
    public String removeIssue(@PathVariable(value = "id") Long id) {
        Optional<Issue> issueFindResult = issueRepository.findById(id);
        if (issueFindResult.isPresent()) {
            Issue issue = issueFindResult.get();
            issueRepository.delete(issue);
        }
        return "redirect:/issues";
    }

    @GetMapping("/issue/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        Issue issue = issueRepository.findById(id).get();
        Iterable<Release> releases = releaseRepository.findAll();
        model.addAttribute("releases", releases);
        model.addAttribute("issue", issue);
        return "pages/issue/editIssue";
    }

    @PatchMapping("/issue/{id}/edit")
    public String editIssue(@PathVariable Long id,
                            @RequestParam Long issueNumber,
                            @RequestParam Release release) {
        Optional<Issue> issueFindResult = issueRepository.findById(id);
        Issue issue = issueFindResult.get();
        issue.setNumber(issueNumber);
        issue.setRelease(release);
        issueRepository.save(issue);
        return "redirect:/issues";
    }
}
