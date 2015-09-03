package in.freebsdk.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;




/**
* This class do the process of making a REST web API call through Retrofit. Retrofit is a REST Client for Android.
 */
public class FreeBRest {
	private static FreeBAPI REST_CLIENT;
	
	public static String ROOT ="https://www.spay.in/FreeBSDK";

    /**
     * Static Block setup RestClient
     */
	static {
		setupRestClient(); 
	}


    /**
     * Get Object of Rest Client
     * @return instance of rest client
     */
	public static FreeBAPI get() {
		return REST_CLIENT;
	}

    /**
     * Intialize the REST CLIENT
     */
	private static void setupRestClient() {
		RestAdapter.Builder builder = new RestAdapter.Builder()
		.setEndpoint(ROOT)
		.setClient(new OkClient(new OkHttpClient()));
		builder.setLogLevel(RestAdapter.LogLevel.FULL);
		RestAdapter restAdapter = builder.build();
		REST_CLIENT = restAdapter.create(FreeBAPI.class);
	}
}
