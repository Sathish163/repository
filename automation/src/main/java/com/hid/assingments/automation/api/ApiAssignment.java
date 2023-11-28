package com.hid.assingments.automation.api;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApiAssignment {

    @Test
    public void verifyCoinMapApi() {
        // Step 1: Make a GET call to the API
        Response response = RestAssured.get("https://coinmap.org/api/v1/venues/");

        // Assertion 1: Check if the request was successful (status code 200)
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve data from the API");

        // Step 2: Get the count of categories
        List<String> categories = response.jsonPath().getList("venues.category");
        Set<String> uniqueCategories = new HashSet<>(categories);

        // Assertion 2: Check if the count of categories is greater than 0
        Assert.assertTrue(uniqueCategories.size() > 0, "No categories found in the response");

        // Step 3: Get names and geo locations of the "food" category
        List<String> foodNames = response.jsonPath().getList("venues.findAll { it.category == 'food' }.name");
        List<String> foodLocations = response.jsonPath().getList("venues.findAll { it.category == 'food' }.geolocation_degrees");

        // Assertion 3: Check if the count of 'food' category is greater than 0
        Assert.assertTrue(foodNames.size() > 0, "No venues found in the 'food' category");

        // Print the results
        System.out.println("Categories count: " + uniqueCategories.size());
        System.out.println("Names and geo locations of 'food' category:");
        for (int i = 0; i < foodNames.size(); i++) {
            System.out.println("Name: " + foodNames.get(i) + ",                  Geo Location: " + foodLocations.get(i));
        }
    }
}
