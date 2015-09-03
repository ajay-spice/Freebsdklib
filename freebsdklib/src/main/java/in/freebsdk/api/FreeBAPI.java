package in.freebsdk.api;

import com.google.gson.JsonObject;

import in.freeb.sdk.model.FreeBOfferData;
import in.freeb.sdk.model.FreeBOfferDetailData;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;
/**
*  This interface contains callback function which run in accordance to the response sent by the server.
*/
public interface FreeBAPI {
    /**
     * To fetch the available offers from the server
     * @param data offers multiparttypedoutput data
     * @param callback  callback for available offers object
     */

	@Headers({ "Accept-Encoding : gzip",
			"Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200" })

	@POST("/zip/fetchOffersAction")
	void downloadOffers(@Body MultipartTypedOutput data,
			Callback<FreeBOfferData> callback);


    /**
     * To fetch the offer Details
     * @param data offers multiparttypedoutput data
     * @param callback  callback for offer detail object
     */
	@POST("/individualOffers")
	void downloadOffersDetail(@Body MultipartTypedOutput data,
			Callback<FreeBOfferDetailData> callback);

    /**
     * To do the registration process
     * @param data pass the registration params in multiparttypedoutput array
     * @return jsonobject registration response sent by the server
     */
	@POST("/registerAction")
	JsonObject registrationWithSystem(@Body MultipartTypedOutput data);


}
