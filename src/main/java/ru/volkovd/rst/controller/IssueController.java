package ru.volkovd.rst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.volkovd.rst.models.Issue;
import ru.volkovd.rst.repo.IssueRepository;

import java.util.Optional;

@Controller
public class IssueController {
    private final IssueRepository issueRepository;

    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping("/issues")
    public String getIssues(Model model) {
        Iterable<Issue> issues = issueRepository.findAll();
        model.addAttribute("issues", issues);
        return "/issues";
    }
    @PostMapping("/issue/new")
    public String addIssue(@RequestParam Long issueNumber) {
        Issue issue = new Issue(issueNumber);
        issueRepository.save(issue);
        return "redirect:/issues";
    }
    @DeleteMapping("/issue/{id}")
    public String removePost(@PathVariable(value = "id") Long id)
    {
        Optional<Issue> issueFindResult = issueRepository.findById(id);
        if (issueFindResult.isPresent()) {
            Issue issue = issueFindResult.get();
            issueRepository.delete(issue);
        }
        return "redirect:/issues";
    }
    @GetMapping("/issue/{id}/edit")
    public String getEditForm (@PathVariable Long id, Model model) {
        Issue issue = issueRepository.findById(id).get();
        model.addAttribute("issue", issue);
        return "editIssue";
    }
    @PatchMapping("/issue/{id}/edit")
    public String editIssue (@PathVariable Long id,
                             @RequestParam Long issueNumber,
                             Model model) {
        Optional<Issue> issueFindResult = issueRepository.findById(id);
            Issue issue = issueFindResult.get();
            issue.setNumber(issueNumber);
            issueRepository.save(issue);
            return "redirect:/issues";
    }
}
