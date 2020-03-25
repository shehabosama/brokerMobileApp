package com.android.jobber.common.network;


import com.android.jobber.common.model.ChatModel.ChatRoom;
import com.android.jobber.common.model.ChatModel.Messages;
import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.JobberUsersResponse;
import com.android.jobber.common.model.LoginResponse;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.MyFavoriteResponse;
import com.android.jobber.common.model.RequestResponse;
import com.android.jobber.common.model.ReviewResponse;
import com.android.jobber.common.model.User;
import com.android.jobber.common.model.verificationResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface API {

    @POST("Login_user.php")
    Call<LoginResponse> loginUser(@Body User user);//to send request and response the message if the account is login or not

    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);// to send request and response the message if the user inserted in database or not
    @FormUrlEncoded
    @POST("index.php")
    Call<verificationResponse> sendVerification(@Field("email") String email);

    @FormUrlEncoded
    @POST("verification.php")
    Call<verificationResponse> reciveVerification(@Field("email") String email ,@Field("random") String verification);
    @FormUrlEncoded
    @POST("ResetPassword.php")
    Call<MainResponse> updatPassword(@Field("email") String email,@Field("password")String password) ;//to send request and response the message if the post update or not

    @Multipart
    @POST("upload-flats.php")
    Call<MainResponse> uploadImages(@Part List<MultipartBody.Part> images,
                                    @Part("user_id") int user_id,
                                    @Part("flat_dscription") String flat_dscription,
                                    @Part("flat_location") String flat_location,
                                    @Part("flat_price") double flat_price,
                                    @Part("type_flat") int type_flat,
                                    @Part("lat") double lat,
                                    @Part("lang") double lang,
                                    @Part("date") String sqlDate,
                                    @Part("product_type") int product_type);


    @POST("get-all-flats.php")
    Call<FlatsResponse> getAllFlats();
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> getUserFlats(@Field("user_id") String userId);
    @FormUrlEncoded
    @POST("delete-product.php")
    Call<MainResponse> deleteProduct(@Field("product_id") int product_id);
    @POST("add-chat-rooms.php")
    Call<MainResponse> add_chat_rooms(@Body ChatRoom chatRoom);
    @POST("add-message.php")
    Call<MainResponse> add_Message(@Body Messages messages);

    @FormUrlEncoded
    @POST("get-messages.php")
    Call<List<Messages>> getMessages(@Field("room_id") int roomId);
    @FormUrlEncoded
    @POST("delete-room.php")
    Call<MainResponse> deleteChatRoom(@Field("id") int id);//to send request and response the message if the post delete successfully or not
    @POST("get-all-chat-rooms.php")
    Call<List<ChatRoom>> getAllChatRooms(@Query("page") int pageNumber);

    @FormUrlEncoded
    @POST("update-user-information.php")
    Call<MainResponse> updateUserInformation(@Field("user_id") int userId,
                                             @Field("user_name")String userName,
                                             @Field("user_address")String userAddress,
                                             @Field("user_gender")String userGender,
                                             @Field("user_phone")String userPhone,@Field("password")String user_password);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByAll(@Field("type_flat") String type_flat,
                                             @Field("product_price_from")String product_price_from,
                                             @Field("product_price_to")String product_price_to,
                                             @Field("product_owner_ship")String product_owner_ship,
                                             @Field("product_country")String product_country);


    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByTypeProductAndPrice(@Field("type_flat") String type_flat,
                                             @Field("product_price_from")String product_price_from,
                                             @Field("product_price_to")String product_price_to);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByOwnerTypeAndTypeProductAndPrice(@Field("type_flat") String type_flat,
                                                            @Field("product_price_from")String product_price_from,
                                                            @Field("product_price_to")String product_price_to,
                                                             @Field("product_owner_ship")String product_owner_ship );
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByTypeOwnerAndPrice(@Field("product_owner_ship") String product_owner_ship,
                                                            @Field("product_price_from")String product_price_from,
                                                            @Field("product_price_to")String product_price_to);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByTypeCountryAndPrice(@Field("product_country") String product_country,
                                                          @Field("product_price_from")String product_price_from,
                                                          @Field("product_price_to")String product_price_to);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByProductTypeAndOwner(@Field("type_flat") String type_flat,
                                                            @Field("product_owner_ship")String product_owner_ship);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByProductTypeAndCountry(@Field("type_flat") String type_flat,
                                                              @Field("product_country")String product_country);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByOwnerAndCountry(@Field("product_owner_ship") String product_owner_ship,
                                                              @Field("product_country")String product_country);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByTypeProduct(@Field("type_flat") String type_flat);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByTypeOwnerShipType(@Field("product_owner_ship") String type_flat);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByCountry(@Field("product_country") String product_country);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterTimeLineByPrice(
            @Field("product_price_from")String product_price_from,
            @Field("product_price_to")String product_price_to);
    @FormUrlEncoded
    @POST("get-all-flats.php")
    Call<FlatsResponse> filterCountryAndProductTypeAndOwner(@Field("product_country") String product_country,
                                                            @Field("product_owner_ship")String product_owner_ship,
                                                            @Field("type_flat")String type_flat);
    @FormUrlEncoded
    @POST("add-product-request.php")
    Call<MainResponse> addOrderRequest(@Field("sender_user_id") String sender_user_id,
                                       @Field("receive_user_id") String receive_user_id,
                                       @Field("order_id") String order_id,@Field("notifiy_token")String token,@Field("times_tamp")String timesTamp);

    @FormUrlEncoded
    @POST("update_token.php")
    Call<MainResponse> updateToken(@Field("user_id")int userId, @Field("token")String token);
    @FormUrlEncoded
    @POST("get-user-favorites.php")
    Call<MyFavoriteResponse> getAllFavorites(@Field("user_id")int userId);
    @FormUrlEncoded
    @POST("add-favorite-product.php")
    Call<MainResponse> FevoriteOpration(@Field("user_id")int user_id,@Field("product_id")int product_id,@Field("delete")String delete);
    @FormUrlEncoded
    @POST("add-favorite-product.php")
    Call<MainResponse> FevoriteOpration(@Field("user_id")int user_id,@Field("product_id")int product_id);

    @FormUrlEncoded
    @POST("get-order-request.php")
    Call<RequestResponse> getSenderUserRequest(@Field("sender_user_id")int user_id);
    @FormUrlEncoded
    @POST("get-order-request.php")
    Call<RequestResponse> getReceiverUserRequest(@Field("receive_user_id")int user_id);
    @FormUrlEncoded
    @POST("update-order-status.php")
    Call<MainResponse> updateRequestStatus(@Field("sender_user_id")int user_id,
                                              @Field("confirmation")String confirmation,
                                              @Field("product_id")String productId,
                                              @Field("request_id")String requestId,
                                              @Field("token")String token);
    @FormUrlEncoded
    @POST("delete-order-request.php")
    Call<MainResponse> deleteOrderRequest(@Field("request_id") int request_id);

    @FormUrlEncoded
    @POST("add-user-review.php")
    Call<MainResponse> rateUs(@Field("normal_user_id") int normal_user,@Field("admin_user_id") int admin_user_id,@Field("rate_degree")String rate_degree,@Field("review_description")String rate_description);

    @FormUrlEncoded
    @POST("get-user-review.php")
    Call<ReviewResponse> getRates(@Field("admin_user_id")int admin_user_id);

    @Multipart
    @POST("add-profile-image.php")
    Call<MainResponse> updateProfileImage(@Header("Cookie") String sessionIdAndRz,
                                          @Part MultipartBody.Part file,
                                          @Part("items") RequestBody items,
                                          @Part("isAny") RequestBody isAny, @Part("user_id") String user);
    @POST("get-all-users.php")
    Call<JobberUsersResponse> getAllJobberUsers();
}
