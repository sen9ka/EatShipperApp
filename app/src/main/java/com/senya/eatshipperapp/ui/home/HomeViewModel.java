package com.senya.eatshipperapp.ui.home;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.senya.eatshipperapp.callback.IShippingOrderCallbackListener;
import com.senya.eatshipperapp.common.Common;
import com.senya.eatshipperapp.model.ShippingOrderModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IShippingOrderCallbackListener {

    private MutableLiveData<List<ShippingOrderModel>> shippingOrderMutableData;
    private MutableLiveData<String> messageError;

    private IShippingOrderCallbackListener listener;

    public HomeViewModel() {
        shippingOrderMutableData = new MutableLiveData<>();
        messageError = new MutableLiveData<>();
        listener = this;
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    public MutableLiveData<List<ShippingOrderModel>> getShippingOrderMutableData(String shipperPhone) {

        if(shipperPhone != null && !TextUtils.isEmpty(shipperPhone))
            loadOrderByShipper(shipperPhone);
        return shippingOrderMutableData;
    }

    private void loadOrderByShipper(String shipperPhone) {
        List<ShippingOrderModel> tempList = new ArrayList<>();
        Query orderRef = FirebaseDatabase.getInstance().getReference(Common.RESTAURANT_REF)
                .child(Common.currentRestaurant.getUid())
                .child(Common.SHIPPING_ORDER_REF)
                .orderByChild("shipperPhone")
                .equalTo(Common.currentShipperUser.getPhone());
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot orderSnapshot : snapshot.getChildren())
                {
                    ShippingOrderModel shippingOrderModel = orderSnapshot.getValue(ShippingOrderModel.class);
                    shippingOrderModel.setKey(orderSnapshot.getKey());
                    tempList.add(shippingOrderModel);
                }
                listener.onShippingOrderLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onShippingOrderLoadFailed(error.getMessage());
            }
        });
    }

    @Override
    public void onShippingOrderLoadSuccess(List<ShippingOrderModel> shippingOrderModelList) {
        shippingOrderMutableData.setValue(shippingOrderModelList);
    }

    @Override
    public void onShippingOrderLoadFailed(String message) {
        messageError.setValue(message);
    }
}