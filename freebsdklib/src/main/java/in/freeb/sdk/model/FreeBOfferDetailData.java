package in.freeb.sdk.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

/**
 * The {@code FreeBOfferDetailData} is a local representation of data that can be saved and retrieved from
 * the server.
 *
 * The basic workflow for creating new data is to construct a new object {@code FreeBOfferDetailData}, use
 * {@link #setMessage(String)} etc to fill it with data, and then use {@link #getMessage()} etc to
 * for accessing existing data.
 *

 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"status",
"errorcode",
"message",
"payload"
})
public class FreeBOfferDetailData {

@JsonProperty("status")
private String status;
@JsonProperty("errorcode")
private String errorcode;
@JsonProperty("message")
private String message;
@JsonProperty("payload")
private Payload payload;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The status
*/
@JsonProperty("status")
public String getStatus() {
return status;
}

/**
* 
* @param status
* The status
*/
@JsonProperty("status")
public void setStatus(String status) {
this.status = status;

}

/**
* 
* @return
* The errorcode
*/
@JsonProperty("errorcode")
public String getErrorcode() {
return errorcode;
}

/**
* 
* @param errorcode
* The errorcode
*/
@JsonProperty("errorcode")
public void setErrorcode(String errorcode) {
this.errorcode = errorcode;
}

/**
* 
* @return
* The message
*/
@JsonProperty("message")
public String getMessage() {
return message;
}

/**
* 
* @param message
* The message
*/
@JsonProperty("message")
public void setMessage(String message) {
this.message = message;
}

/**
* 
* @return
* The payload
*/
@JsonProperty("payload")
public Payload getPayload() {
return payload;
}

/**
* 
* @param payload
* The payload
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
@JsonPropertyOrder({
"bannerAction",
"description",
"promoImage"
})
public class Payload {

@JsonProperty("bannerAction")
private String bannerAction;
@JsonProperty("description")
private String description;
@JsonProperty("promoImage")
private String promoImage;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The bannerAction
*/
@JsonProperty("bannerAction")
public String getBannerAction() {
return bannerAction;
}

/**
* 
* @param bannerAction
* The bannerAction
*/
@JsonProperty("bannerAction")
public void setBannerAction(String bannerAction) {
this.bannerAction = bannerAction;
}

/**
* 
* @return
* The description
*/
@JsonProperty("description")
public String getDescription() {
return description;
}

/**
* 
* @param description
* The description
*/
@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

/**
* 
* @return
* The promoImage
*/
@JsonProperty("promoImage")
public String getPromoImage() {
return promoImage;
}

/**
* 
* @param promoImage
* The promoImage
*/
@JsonProperty("promoImage")
public void setPromoImage(String promoImage) {
this.promoImage = promoImage;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}
}
}