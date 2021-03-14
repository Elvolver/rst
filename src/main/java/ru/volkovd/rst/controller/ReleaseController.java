package ru.volkovd.rst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.volkovd.rst.models.Release;
import ru.volkovd.rst.repo.ReleaseRepository;

import java.util.Optional;

@Controller
public class ReleaseController {
    private final ReleaseRepository releaseRepository;

    public ReleaseController(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @GetMapping("/releases")
    public String getReleases(Model model) {
        Iterable<Release> releases = releaseRepository.findAll();
        model.addAttribute("releases", releases);
        return "/pages/release/releases";
    }

    @PostMapping("/release")
    public String addRelease(@RequestParam String releaseTitle) {
        Release release = new Release(releaseTitle);
        releaseRepository.save(release);
        return "redirect:/releases";
    }

    @DeleteMapping("/release/{id}")
    public String removeRelease(@PathVariable(value = "id") Long id) {
        Optional<Release> releaseFindResult = releaseRepository.findById(id);
        if (releaseFindResult.isPresent()) {
            Release release = releaseFindResult.get();
            releaseRepository.delete(release);
        }
        return "redirect:/releases";
    }

    @GetMapping("/release/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        Release release = releaseRepository.findById(id).get();
        model.addAttribute("release", release);
        model.addAttribute("issues", release.getIssues());
        return "pages/release/editRelease";
    }

    @PatchMapping("/release/{id}/edit")
    public String editRelease(@PathVariable Long id,
                              @RequestParam String releaseTitle) {
        Optional<Release> releaseFindResult = releaseRepository.findById(id);
        Release release = releaseFindResult.get();
        release.setTitle(releaseTitle);
        releaseRepository.save(release);
        return "redirect:/release";
    }
}
