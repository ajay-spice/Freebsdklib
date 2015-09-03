package in.freeb.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "status", "errorCode", "message", "udf1", "udf2", "udf3",
		"udf4", "udf5", "payload" })

/**
 * The {@code FreeBOfferData} is a local representation of data that can be saved and retrieved from
 * the server.
 *
 * The basic workflow for creating new data is to construct a new object {@code FreeBOfferData}, use
 * {@link #put(String, Object)} to fill it with data, and then use {@link #get()} to
 * for accessing existing data.
 *

 */
public class FreeBOfferData implements Parcelable {

	@JsonProperty("status")
	private String status;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("message")
	private String message;
	@JsonProperty("udf1")
	private String udf1;
	@JsonProperty("udf2")
	private String udf2;
	@JsonProperty("udf3")
	private String udf3;
	@JsonProperty("udf4")
	private String udf4;
	@JsonProperty("udf5")
	private String udf5;
	@JsonProperty("payload")
	private Payload payload;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The errorCode
	 */
	@JsonProperty("errorCode")
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 * @param errorCode
	 *            The errorCode
	 */
	@JsonProperty("errorCode")
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @return The message
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return The udf1
	 */
	@JsonProperty("udf1")
	public String getUdf1() {
		return udf1;
	}

	/**
	 * 
	 * @param udf1
	 *            The udf1
	 */
	@JsonProperty("udf1")
	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	/**
	 * 
	 * @return The udf2
	 */
	@JsonProperty("udf2")
	public String getUdf2() {
		return udf2;
	}

	/**
	 * 
	 * @param udf2
	 *            The udf2
	 */
	@JsonProperty("udf2")
	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	/**
	 * 
	 * @return The udf3
	 */
	@JsonProperty("udf3")
	public String getUdf3() {
		return udf3;
	}

	/**
	 * 
	 * @param udf3
	 *            The udf3
	 */
	@JsonProperty("udf3")
	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	/**
	 * 
	 * @return The udf4
	 */
	@JsonProperty("udf4")
	public String getUdf4() {
		return udf4;
	}

	/**
	 * 
	 * @param udf4
	 *            The udf4
	 */
	@JsonProperty("udf4")
	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	/**
	 * 
	 * @return The udf5
	 */
	@JsonProperty("udf5")
	public String getUdf5() {
		return udf5;
	}

	/**
	 * 
	 * @param udf5
	 *            The udf5
	 */
	@JsonProperty("udf5")
	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	/**
	 * 
	 * @return The payload
	 */
	@JsonProperty("payload")
	public Payload getPayload() {
		return payload;
	}

	/**
	 * 
	 * @param payload
	 *            The payload
	 */
	@JsonProperty("payload")
	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Generated("org.jsonschema2pojo")
	@JsonPropertyOrder({ "promoImageAction", "packageName",
			"isMoneyBasedOffer", "alreadyConsumed", "offerAppAction",
			"timesInSeconds", "iconUrl", "actionText", "rgbCode", "currency",
			"offerCategory", "offerType", "checkInstallApps",
			"shortDescription", "offerPrice", "serverReferenceId",
			"promoImage", "longDescription", "altText", "offerName",
			"linkName", "offerId" })
	public static class FetchOffer implements Parcelable {

		@JsonProperty("promoImageAction")
		private String promoImageAction;
		@JsonProperty("packageName")
		private String packageName;
		@JsonProperty("isMoneyBasedOffer")
		private String isMoneyBasedOffer;
		@JsonProperty("alreadyConsumed")
		private String alreadyConsumed;
		@JsonProperty("offerAppAction")
		private String offerAppAction;
		@JsonProperty("timesInSeconds")
		private String timesInSeconds;
		@JsonProperty("iconUrl")
		private String iconUrl;
		@JsonProperty("actionText")
		private String actionText;
		@JsonProperty("rgbCode")
		private String rgbCode;
		@JsonProperty("currency")
		private String currency;
		@JsonProperty("offerCategory")
		private String offerCategory;
		@JsonProperty("offerType")
		private String offerType;
		@JsonProperty("checkInstallApps")
		private String checkInstallApps;
		@JsonProperty("shortDescription")
		private String shortDescription;
		@JsonProperty("offerPrice")
		private String offerPrice;
		@JsonProperty("serverReferenceId")
		private String serverReferenceId;
		@JsonProperty("promoImage")
		private String promoImage;
		@JsonProperty("longDescription")
		private String longDescription;
		@JsonProperty("altText")
		private String altText;
		@JsonProperty("offerName")
		private String offerName;
		@JsonProperty("linkName")
		private String linkName;
		@JsonProperty("offerId")
		private String offerId;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * 
		 * @return The promoImageAction
		 */
		@JsonProperty("promoImageAction")
		public String getPromoImageAction() {
			return promoImageAction;
		}

		/**
		 * 
		 * @param promoImageAction
		 *            The promoImageAction
		 */
		@JsonProperty("promoImageAction")
		public void setPromoImageAction(String promoImageAction) {
			this.promoImageAction = promoImageAction;
		}

		/**
		 * 
		 * @return The packageName
		 */
		@JsonProperty("packageName")
		public String getPackageName() {
			return packageName;
		}

		/**
		 * 
		 * @param packageName
		 *            The packageName
		 */
		@JsonProperty("packageName")
		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		/**
		 * 
		 * @return The isMoneyBasedOffer
		 */
		@JsonProperty("isMoneyBasedOffer")
		public String getIsMoneyBasedOffer() {
			return isMoneyBasedOffer;
		}

		/**
		 * 
		 * @param isMoneyBasedOffer
		 *            The isMoneyBasedOffer
		 */
		@JsonProperty("isMoneyBasedOffer")
		public void setIsMoneyBasedOffer(String isMoneyBasedOffer) {
			this.isMoneyBasedOffer = isMoneyBasedOffer;
		}

		/**
		 * 
		 * @return The alreadyConsumed
		 */
		@JsonProperty("alreadyConsumed")
		public String getAlreadyConsumed() {
			return alreadyConsumed;
		}

		/**
		 * 
		 * @param alreadyConsumed
		 *            The alreadyConsumed
		 */
		@JsonProperty("alreadyConsumed")
		public void setAlreadyConsumed(String alreadyConsumed) {
			this.alreadyConsumed = alreadyConsumed;
		}

		/**
		 * 
		 * @return The offerAppAction
		 */
		@JsonProperty("offerAppAction")
		public String getOfferAppAction() {
			return offerAppAction;
		}

		/**
		 * 
		 * @param offerAppAction
		 *            The offerAppAction
		 */
		@JsonProperty("offerAppAction")
		public void setOfferAppAction(String offerAppAction) {
			this.offerAppAction = offerAppAction;
		}

		/**
		 * 
		 * @return The timesInSeconds
		 */
		@JsonProperty("timesInSeconds")
		public String getTimesInSeconds() {
			return timesInSeconds;
		}

		/**
		 * 
		 * @param timesInSeconds
		 *            The timesInSeconds
		 */
		@JsonProperty("timesInSeconds")
		public void setTimesInSeconds(String timesInSeconds) {
			this.timesInSeconds = timesInSeconds;
		}

		/**
		 * 
		 * @return The iconUrl
		 */
		@JsonProperty("iconUrl")
		public String getIconUrl() {
			return iconUrl;
		}

		/**
		 * 
		 * @param iconUrl
		 *            The iconUrl
		 */
		@JsonProperty("iconUrl")
		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		/**
		 * 
		 * @return The actionText
		 */
		@JsonProperty("actionText")
		public String getActionText() {
			return actionText;
		}

		/**
		 * 
		 * @param actionText
		 *            The actionText
		 */
		@JsonProperty("actionText")
		public void setActionText(String actionText) {
			this.actionText = actionText;
		}

		/**
		 * 
		 * @return The rgbCode
		 */
		@JsonProperty("rgbCode")
		public String getRgbCode() {
			return rgbCode;
		}

		/**
		 * 
		 * @param rgbCode
		 *            The rgbCode
		 */
		@JsonProperty("rgbCode")
		public void setRgbCode(String rgbCode) {
			this.rgbCode = rgbCode;
		}

		/**
		 * 
		 * @return The currency
		 */
		@JsonProperty("currency")
		public String getCurrency() {
			return currency;
		}

		/**
		 * 
		 * @param currency
		 *            The currency
		 */
		@JsonProperty("currency")
		public void setCurrency(String currency) {
			this.currency = currency;
		}

		/**
		 * 
		 * @return The offerCategory
		 */
		@JsonProperty("offerCategory")
		public String getOfferCategory() {
			return offerCategory;
		}

		/**
		 * 
		 * @param offerCategory
		 *            The offerCategory
		 */
		@JsonProperty("offerCategory")
		public void setOfferCategory(String offerCategory) {
			this.offerCategory = offerCategory;
		}

		/**
		 * 
		 * @return The offerType
		 */
		@JsonProperty("offerType")
		public String getOfferType() {
			return offerType;
		}

		/**
		 * 
		 * @param offerType
		 *            The offerType
		 */
		@JsonProperty("offerType")
		public void setOfferType(String offerType) {
			this.offerType = offerType;
		}

		/**
		 * 
		 * @return The checkInstallApps
		 */
		@JsonProperty("checkInstallApps")
		public String getCheckInstallApps() {
			return checkInstallApps;
		}

		/**
		 * 
		 * @param checkInstallApps
		 *            The checkInstallApps
		 */
		@JsonProperty("checkInstallApps")
		public void setCheckInstallApps(String checkInstallApps) {
			this.checkInstallApps = checkInstallApps;
		}

		/**
		 * 
		 * @return The shortDescription
		 */
		@JsonProperty("shortDescription")
		public String getShortDescription() {
			return shortDescription;
		}

		/**
		 * 
		 * @param shortDescription
		 *            The shortDescription
		 */
		@JsonProperty("shortDescription")
		public void setShortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
		}

		/**
		 * 
		 * @return The offerPrice
		 */
		@JsonProperty("offerPrice")
		public String getOfferPrice() {
			return offerPrice;
		}

		/**
		 * 
		 * @param offerPrice
		 *            The offerPrice
		 */
		@JsonProperty("offerPrice")
		public void setOfferPrice(String offerPrice) {
			this.offerPrice = offerPrice;
		}

		/**
		 * 
		 * @return The serverReferenceId
		 */
		@JsonProperty("serverReferenceId")
		public String getServerReferenceId() {
			return serverReferenceId;
		}

		/**
		 * 
		 * @param serverReferenceId
		 *            The serverReferenceId
		 */
		@JsonProperty("serverReferenceId")
		public void setServerReferenceId(String serverReferenceId) {
			this.serverReferenceId = serverReferenceId;
		}

		/**
		 * 
		 * @return The promoImage
		 */
		@JsonProperty("promoImage")
		public String getPromoImage() {
			return promoImage;
		}

		/**
		 * 
		 * @param promoImage
		 *            The promoImage
		 */
		@JsonProperty("promoImage")
		public void setPromoImage(String promoImage) {
			this.promoImage = promoImage;
		}

		/**
		 * 
		 * @return The longDescription
		 */
		@JsonProperty("longDescription")
		public String getLongDescription() {
			return longDescription;
		}

		/**
		 * 
		 * @param longDescription
		 *            The longDescription
		 */
		@JsonProperty("longDescription")
		public void setLongDescription(String longDescription) {
			this.longDescription = longDescription;
		}

		/**
		 * 
		 * @return The altText
		 */
		@JsonProperty("altText")
		public String getAltText() {
			return altText;
		}

		/**
		 * 
		 * @param altText
		 *            The altText
		 */
		@JsonProperty("altText")
		public void setAltText(String altText) {
			this.altText = altText;
		}

		/**
		 * 
		 * @return The offerName
		 */
		@JsonProperty("offerName")
		public String getOfferName() {
			return offerName;
		}

		/**
		 * 
		 * @param offerName
		 *            The offerName
		 */
		@JsonProperty("offerName")
		public void setOfferName(String offerName) {
			this.offerName = offerName;
		}

		/**
		 * 
		 * @return The linkName
		 */
		@JsonProperty("linkName")
		public String getLinkName() {
			return linkName;
		}

		/**
		 * 
		 * @param linkName
		 *            The linkName
		 */
		@JsonProperty("linkName")
		public void setLinkName(String linkName) {
			this.linkName = linkName;
		}

		/**
		 * 
		 * @return The offerId
		 */
		@JsonProperty("offerId")
		public String getOfferId() {
			return offerId;
		}

		/**
		 * 
		 * @param offerId
		 *            The offerId
		 */
		@JsonProperty("offerId")
		public void setOfferId(String offerId) {
			this.offerId = offerId;
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

		public FetchOffer(Parcel in) {

			promoImageAction = in.readString();
			packageName = in.readString();
			isMoneyBasedOffer = in.readString();
			alreadyConsumed = in.readString();
			offerAppAction = in.readString();
			timesInSeconds = in.readString();
			iconUrl = in.readString();
			actionText = in.readString();
			rgbCode = in.readString();
			currency = in.readString();
			offerCategory = in.readString();
			offerType = in.readString();
			checkInstallApps = in.readString();
			shortDescription = in.readString();
			offerPrice = in.readString();
			serverReferenceId = in.readString();
			promoImage = in.readString();
			longDescription = in.readString();
			altText = in.readString();
			offerName = in.readString();
			linkName = in.readString();
			offerId = in.readString();

		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {

			dest.writeString(promoImageAction);
			dest.writeString(packageName);
			dest.writeString(isMoneyBasedOffer);
			dest.writeString(alreadyConsumed);
			dest.writeString(offerAppAction);
			dest.writeString(timesInSeconds);
			dest.writeString(iconUrl);
			dest.writeString(actionText);
			dest.writeString(rgbCode);
			dest.writeString(currency);
			dest.writeString(offerCategory);
			dest.writeString(offerType);
			dest.writeString(checkInstallApps);
			dest.writeString(shortDescription);
			dest.writeString(offerPrice);
			dest.writeString(serverReferenceId);
			dest.writeString(promoImage);
			dest.writeString(longDescription);
			dest.writeString(altText);
			dest.writeString(offerName);
			dest.writeString(linkName);
			dest.writeString(offerId);

		}

		public static final Parcelable.Creator<FetchOffer> CREATOR = new Creator<FetchOffer>() {
			public FetchOffer createFromParcel(Parcel in) {
				return new FetchOffer(in);
			}

			public FetchOffer[] newArray(int size) {
				return new FetchOffer[size];
			}
		};

	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Generated("org.jsonschema2pojo")
	@JsonPropertyOrder({ "fetchOffers" })
	public static class Payload implements Parcelable {

		@JsonProperty("fetchOffers")
		private List<FetchOffer> fetchOffers = new ArrayList<FetchOffer>();
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * 
		 * @return The fetchOffers
		 */
		@JsonProperty("fetchOffers")
		public List<FetchOffer> getFetchOffers() {
			return fetchOffers;
		}

		/**
		 * 
		 * @param fetchOffers
		 *            The fetchOffers
		 */
		@JsonProperty("fetchOffers")
		public void setFetchOffers(List<FetchOffer> fetchOffers) {

			this.fetchOffers = fetchOffers;
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {

		}

		public Payload(Parcel in) {
            try {
                fetchOffers = (List<FetchOffer>) in.readParcelable(Payload.class.getClassLoader());
            }catch(Exception e){

            }

		}

		public static final Parcelable.Creator<Payload> CREATOR = new Creator<Payload>() {

			@Override
			public Payload[] newArray(int size) {
				return new Payload[size];
			}

			@Override
			public Payload createFromParcel(Parcel source) {
				return new Payload(source);
			}
		};

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(status);
		dest.writeString(errorCode);
		dest.writeString(message);

		dest.writeString(udf1);
		dest.writeString(udf2);
		dest.writeString(udf3);
		dest.writeString(udf4);
		dest.writeString(udf5);

		dest.writeParcelable(payload, flags);

	}

	public FreeBOfferData(Parcel in) {

		status = in.readString();
		errorCode = in.readString();
		message = in.readString();

		udf1 = in.readString();
		udf2 = in.readString();
		udf3 = in.readString();
		udf4 = in.readString();
		udf5 = in.readString();

		payload = in.readParcelable(FreeBOfferData.class.getClassLoader());
	}

	public static final Parcelable.Creator<FreeBOfferData> CREATOR = new Creator<FreeBOfferData>() {

		@Override
		public FreeBOfferData[] newArray(int size) {
			return new FreeBOfferData[size];
		}

		@Override
		public FreeBOfferData createFromParcel(Parcel source) {
			return new FreeBOfferData(source);
		}
	};

}