package in.freeb.sdk.interfaces;


/**
 * A {@code FreeBRegistrationListener} Callback methods defined in this interface to respond the user with desired result of registration.
 *
 * It is used to do something after a background task completes. End users will
 * use a specific subclass of {@code FreeBRegistrationListener}.
 */

public interface FreeBRegistrationListener {

    /**
     *
     * If the operation was unsuccessful, {@code t} will contain information about the operation
     * failure.
     *
     * @param code-registration faild error code
     * @param errorMessage-error message for registartion failure
     */
	public void onRegistrationFailed(String code, String errorMessage);

    /**
     * If the operation is successful, {@code t} will contain information about the operation
     * Success.
     * @param code-successfull registration code
     * @param errorMessage-succesfull registered response
     */

	public void onRegistrationSuccess(String code, String errorMessage);

}
