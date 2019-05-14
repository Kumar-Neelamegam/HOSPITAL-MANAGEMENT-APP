/*
  File generated by Magnet rest2mobile 1.1 - Nov 13, 2017 1:42:33 PM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api;

import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;

import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PatientIdListResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PostPatientIdListRequest;

public interface PostPatientIdList {

    /**
     * Generated from URL POST http://192.168.137.143:1234/importMasters/postPatientIdList
     * POST importMasters/postPatientIdList
     * @param body  style:PLAIN
     * @param listener
     * @return PatientIdListResult
     */
    Call<PatientIdListResult> postPatientIdList(
            PostPatientIdListRequest body,
            StateChangedListener listener
    );


}