package com.senya.eatshipperapp.model.eventbus;

import com.senya.eatshipperapp.model.RestaurantModel;

import java.util.List;

public interface IRestaurantCallbackListener {
    void onRestaurantLoadSuccess(List<RestaurantModel> restaurantModelList);
    void onRestaurantLoadFailed(String message);
}
