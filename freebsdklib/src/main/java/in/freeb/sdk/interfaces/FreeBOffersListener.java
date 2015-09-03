package in.freeb.sdk.interfaces;



/**
 * A {@code FreeBOffersListener}  Callback methods defined in this interface to respond the user in accordance to the desired result sent by the server for fetching Offers.
 *
 * It is used to do something after a background task completes. End users will
 * use a specific subclass of {@code FreeBOffersListener}.
 */

public interface FreeBOffersListener {

    /**
     * If the operation was unsuccessful, {@code t} will contain information about the operation
     * failure.
     * @param code-fail to show offers code
     * @param errorMessage-offer load  failure response.
     */
    public void onOffersFailed(String code, String errorMessage);

    /**
     * If the operation is successful, {@code t} will contain information about the operation
     * Success.
     *
     * @param code-successfully offers loaded code
     * @param errorMessage-sucess response when offers available to user
     */

    public void onOffersLoaded(String code, String errorMessage);

    /***
     * Function will itself called when an offer will successfully installed through FreeB SDK and {@code t} will contain information about the operation
     * Success.
     * @param code-success full install offer code
     * @param response-success full offer install response
     */

    public void onOffersInstallSuccess(String code, String response);

    /***
     * Function will call itself called when an offer will fail to install through FreeB SDK and {@code t} will contain information about the operation
     * failure.
     * @param code-offer install failure code
     * @param response-offer install failure response
     */
    public void onOffersInstallFailure(String code, String response);

    /***
     * show the available offers in the
     * application.
     */
    public void onShowOffers();

    /***
     * When user show interest in a particular offer but not consume that offer.
     * @param code -no offer install code
     * @param errorMessage -no offer install response
     */
    public void noOfferInstalled(String code, String errorMessage);

//	public void onOffersInteraction(String code, String errorMessage);

    /***
     * if the user exit the offer screen or on back pressed.
     * @param code-no offer install code
     * @param response-no offer install response
     */
    public void onLeaveApplication(String code, String response);

    /***
     *  On dismiss offers progress dialog.
     * @param errorMessage-error message on progress dialog dismiss
     */

    public void onDialogDismiss(String errorMessage);
}
