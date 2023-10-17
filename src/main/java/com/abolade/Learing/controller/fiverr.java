package com.abolade.Learing.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class fiverr {



    @GetMapping("/welcome")
    public String showForm(Model model) {


        model.addAttribute("user","");

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "welcome";
    }

    public void get() {

    }

    public Map<String, Integer> get(){

        List<String> products = voterRepository.findAllDevops();
        // Create a map to store product occurrences
        Map<String, Integer> productCountMap = new HashMap<>();

        // Iterate through the list and update the occurrence count
        for (String product : products) {
            // If the product is already in the map, increment the count
            if (productCountMap.containsKey(product)) {
                int count = productCountMap.get(product);
                productCountMap.put(product, count + 1);
            } else {
                // If the product is not in the map, add it with a count of 1
                productCountMap.put(product, 1);
            }
        }

        // Display the occurrence count for each product
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            String product = entry.getKey();
            int count = entry.getValue();
            System.out.println(product + ": " + count);
        }

        return productCountMap;
    }


}
