package com.nesa.interview.assettracking.controller;

import com.nesa.interview.assettracking.model.Asset;
import com.nesa.interview.assettracking.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assets-ui")
public class AssetUIController {

    @Autowired
    private AssetRepository repository;


    @GetMapping
    public String viewAssets(Model model) {
        model.addAttribute("assets", repository.findAll());
        model.addAttribute("newAsset", new Asset()); // For the 'Add' form
        return "assets";
    }


    @PostMapping("/add")
    public String addAsset(@ModelAttribute Asset asset) {
        repository.save(asset);
        return "redirect:/assets-ui";
    }


    @GetMapping("/edit/{id}")
    public String editAssetPage(@PathVariable Long id, Model model) {
        Asset asset = repository.findById(id).orElseThrow();
        model.addAttribute("asset", asset);
        return "edit-asset";
    }
    @GetMapping("/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/assets-ui";
    }
}
