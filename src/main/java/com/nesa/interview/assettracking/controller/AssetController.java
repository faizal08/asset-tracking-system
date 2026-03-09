package com.nesa.interview.assettracking.controller;

import com.nesa.interview.assettracking.model.Asset;
import com.nesa.interview.assettracking.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private AssetRepository assetRepository;

    // (A) Create Asset
    @PostMapping
    public Asset createAsset(@RequestBody Asset asset) {
        return assetRepository.save(asset);
    }


    @GetMapping
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long id) {
        return assetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return assetRepository.findById(id).map(asset -> {
            if (updates.containsKey("name")) asset.setName((String) updates.get("name"));
            if (updates.containsKey("type")) asset.setType((String) updates.get("type"));
            if (updates.containsKey("status")) asset.setStatus((String) updates.get("status"));
            return ResponseEntity.ok(assetRepository.save(asset));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        if (assetRepository.existsById(id)) {
            assetRepository.deleteById(id); // This triggers our @SQLDelete (Update is_deleted = true)
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}