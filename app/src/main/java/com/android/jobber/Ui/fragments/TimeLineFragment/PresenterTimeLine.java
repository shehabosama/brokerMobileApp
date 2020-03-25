package com.android.jobber.Ui.fragments.TimeLineFragment;

import android.text.TextUtils;

import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.MyFavoriteResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterTimeLine implements TimeLineContract.Presenter {
    private TimeLineContract.Model.onFinishedListener mModel;
    private TimeLineContract.View mView;

    public PresenterTimeLine(TimeLineContract.Model.onFinishedListener mModel, TimeLineContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetAllFlats(final String userId) {
        mView.showProgress();
        WebService.getInstance(true).getApi().getAllFlats().enqueue(new Callback<FlatsResponse>() {
            @Override
            public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                performGetFavoriteProduct(userId,response.body());

            }

            @Override
            public void onFailure(Call<FlatsResponse> call, Throwable t) {

                mModel.onFailuer(t);
                mView.hideProgress();
            }
        });



    }

    @Override
    public void performDeleteProduct(String userId) {
        if(TextUtils.isEmpty(userId)){
            mModel.onFinished("something went wrong..");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().deleteProduct(Integer.parseInt(userId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);

                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {

                    mModel.onFailuer(t);
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performFilterProducts(String type_flats, String price_from, String price_to, String owner_ship, String product_country, final String userId) {
        if(!TextUtils.isEmpty(price_from) || !TextUtils.isEmpty(price_to)){

            if(TextUtils.isEmpty(price_to) || TextUtils.isEmpty(price_from)){
                mModel.onFinished("Please if you want filter by price select from and to price");
            }else{
                if(!TextUtils.isEmpty(type_flats)&&TextUtils.isEmpty(owner_ship)&& TextUtils.isEmpty(product_country)){
                    WebService.getInstance(true).getApi().filterTimeLineByTypeProductAndPrice(type_flats,price_from,price_to).enqueue(new Callback<FlatsResponse>() {
                        @Override
                        public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                            performGetFavoriteProduct(userId,response.body());
                        }

                        @Override
                        public void onFailure(Call<FlatsResponse> call, Throwable t) {
                            mView.hideProgress();
                        }
                    });
                }else if(!TextUtils.isEmpty(owner_ship)&&TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(product_country)){
                    WebService.getInstance(true).getApi().filterTimeLineByTypeOwnerAndPrice(owner_ship,price_from,price_to).enqueue(new Callback<FlatsResponse>() {
                        @Override
                        public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                            performGetFavoriteProduct(userId,response.body());
                        }

                        @Override
                        public void onFailure(Call<FlatsResponse> call, Throwable t) {
                            mView.hideProgress();
                        }
                    });

                }else if(!TextUtils.isEmpty(product_country)&&TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(owner_ship)){

                    WebService.getInstance(true).getApi().filterTimeLineByTypeCountryAndPrice(product_country,price_from,price_to).enqueue(new Callback<FlatsResponse>() {
                        @Override
                        public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                            performGetFavoriteProduct(userId,response.body());
                        }

                        @Override
                        public void onFailure(Call<FlatsResponse> call, Throwable t) {
                            mView.hideProgress();
                        }
                    });

                }else if (!TextUtils.isEmpty(type_flats)&&!TextUtils.isEmpty(owner_ship)&&!TextUtils.isEmpty(price_from)&&!TextUtils.isEmpty(price_to)&&TextUtils.isEmpty(product_country)){
                    WebService.getInstance(true).getApi().filterTimeLineByOwnerTypeAndTypeProductAndPrice(type_flats,price_from,price_to,owner_ship).enqueue(new Callback<FlatsResponse>() {
                        @Override
                        public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                            performGetFavoriteProduct(userId,response.body());

                        }

                        @Override
                        public void onFailure(Call<FlatsResponse> call, Throwable t) {
                            mView.hideProgress();
                        }
                    });
                }else if (!TextUtils.isEmpty(type_flats)&&!TextUtils.isEmpty(owner_ship)&&!TextUtils.isEmpty(price_from)&&!TextUtils.isEmpty(price_to)&&!TextUtils.isEmpty(product_country)){
                    WebService.getInstance(true).getApi().filterTimeLineByAll(type_flats,price_from,price_to,owner_ship,product_country).enqueue(new Callback<FlatsResponse>() {
                        @Override
                        public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                            performGetFavoriteProduct(userId,response.body());

                        }

                        @Override
                        public void onFailure(Call<FlatsResponse> call, Throwable t) {
                            mView.hideProgress();
                        }
                    });
                }else{
                    WebService.getInstance(true).getApi().filterTimeLineByPrice(price_from,price_to).enqueue(new Callback<FlatsResponse>() {
                        @Override
                        public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                            performGetFavoriteProduct(userId,response.body());
                        }

                        @Override
                        public void onFailure(Call<FlatsResponse> call, Throwable t) {
                            mView.hideProgress();
                        }
                    });
                }

            }
        }else{
           // Log.e(TAG, "performFilterProducts: +"+product_country+" "+type_flats+" "+owner_ship+" "+price_from+" "+price_to );
            if(!TextUtils.isEmpty(product_country)&&TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(owner_ship)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){

                WebService.getInstance(true).getApi().filterTimeLineByCountry(product_country).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());
                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }else if(!TextUtils.isEmpty(type_flats)&&TextUtils.isEmpty(product_country)&& TextUtils.isEmpty(owner_ship)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){

                WebService.getInstance(true).getApi().filterTimeLineByTypeProduct(type_flats).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });

            }else if(!TextUtils.isEmpty(owner_ship)&&TextUtils.isEmpty(product_country)&& TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){

                WebService.getInstance(true).getApi().filterTimeLineByTypeOwnerShipType(owner_ship).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });

            }else if(!TextUtils.isEmpty(owner_ship)&&!TextUtils.isEmpty(product_country)&& TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){

                WebService.getInstance(true).getApi().filterTimeLineByOwnerAndCountry(owner_ship,product_country).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }else if(!TextUtils.isEmpty(owner_ship)&&!TextUtils.isEmpty(product_country)&& TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){

                WebService.getInstance(true).getApi().filterTimeLineByOwnerAndCountry(owner_ship,product_country).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }else if(!TextUtils.isEmpty(owner_ship)&&!TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(product_country)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){
                WebService.getInstance(true).getApi().filterTimeLineByProductTypeAndOwner(type_flats,owner_ship).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }else if(!TextUtils.isEmpty(product_country)&&!TextUtils.isEmpty(type_flats)&& TextUtils.isEmpty(owner_ship)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){
                WebService.getInstance(true).getApi().filterTimeLineByProductTypeAndCountry(type_flats,product_country).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }else if(!TextUtils.isEmpty(product_country)&&!TextUtils.isEmpty(type_flats)&&!TextUtils.isEmpty(owner_ship)&& TextUtils.isEmpty(price_from)&& TextUtils.isEmpty(price_to)){
                WebService.getInstance(true).getApi().filterCountryAndProductTypeAndOwner(product_country,owner_ship,type_flats).enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());
                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }else {

                WebService.getInstance(true).getApi().getAllFlats().enqueue(new Callback<FlatsResponse>() {
                    @Override
                    public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                        performGetFavoriteProduct(userId,response.body());

                    }

                    @Override
                    public void onFailure(Call<FlatsResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }


        }

    }

    @Override
    public void performOrderRequest(String senderId, String receiverId, String orderId, String token,String timesTamp) {
        if(TextUtils.isEmpty(senderId)||TextUtils.isEmpty(receiverId)||TextUtils.isEmpty(orderId)||TextUtils.isEmpty(token)|| TextUtils.isEmpty(timesTamp)){
            mModel.onFinished("Something Went Wrong..");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().addOrderRequest(senderId,receiverId,orderId,token,timesTamp).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                    assert response.body() != null;
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performAddItemFavorite(String userId, String productId) {

        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(productId)){
            mModel.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().FevoriteOpration(Integer.parseInt(userId),Integer.parseInt(productId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }


    @Override
    public void performDeleteItemFavorite(String userId, String productId, String delete) {

        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(productId)){
            mModel.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().FevoriteOpration(Integer.parseInt(userId),Integer.parseInt(productId),delete).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performGetFavoriteProduct(String user_id, final FlatsResponse flatsResponse) {
        mView.showProgress();

        if(TextUtils.isEmpty(user_id)){
            mModel.onFinished("Please Make sure are you blocked or not");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().getAllFavorites(Integer.parseInt(user_id)).enqueue(new Callback<MyFavoriteResponse>() {
                @Override
                public void onResponse(Call<MyFavoriteResponse> call, Response<MyFavoriteResponse> response) {
                    //  mModel.loadFavoritesCarList(response.body());
                    mModel.loadAllFlats(flatsResponse,response.body());

                }

                @Override
                public void onFailure(Call<MyFavoriteResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }
}
