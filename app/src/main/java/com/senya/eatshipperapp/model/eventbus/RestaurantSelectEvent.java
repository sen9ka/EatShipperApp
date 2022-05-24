package com.senya.eatshipperapp.model.eventbus;

import com.senya.eatshipperapp.model.RestaurantModel;

public class RestaurantSelectEvent {
    private RestaurantModel restaurantModel;

    public RestaurantSelectEvent(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
