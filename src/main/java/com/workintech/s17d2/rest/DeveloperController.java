package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers") // application.properties'deki prefix ile /workintech/developers olur
public class DeveloperController {

    public Map<Integer, Developer> developers;
    public final Taxable taxable;

    // Constructor Injection
    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
    }

    @GetMapping
    public List<Developer> getAll() {
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable int id) {
        return developers.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer save(@RequestBody Developer developer) {
        double finalSalary = developer.getSalary();
        Developer createdDeveloper = null;

        // Vergi hesaplama ve Nesne oluşturma mantığı
        if (developer.getExperience() == Experience.JUNIOR) {
            finalSalary -= developer.getSalary() * taxable.getSimpleTaxRate() / 100;
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), finalSalary);
        } else if (developer.getExperience() == Experience.MID) {
            finalSalary -= developer.getSalary() * taxable.getMiddleTaxRate() / 100;
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(), finalSalary);
        } else {
            finalSalary -= developer.getSalary() * taxable.getUpperTaxRate() / 100;
            createdDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), finalSalary);
        }

        developers.put(developer.getId(), createdDeveloper);
        return createdDeveloper;
    }

    @PutMapping("/{id}")
    public Developer update(@PathVariable int id, @RequestBody Developer developer) {
        if (!developers.containsKey(id)) return null;
        developer.setId(id);
        developers.put(id, developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    public Developer delete(@PathVariable int id) {
        return developers.remove(id);
    }
}