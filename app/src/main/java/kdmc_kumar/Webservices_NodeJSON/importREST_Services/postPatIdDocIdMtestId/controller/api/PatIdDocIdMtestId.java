/*
  File generated by Magnet rest2mobile 1.1 - Dec 15, 2017 6:58:30 PM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatIdDocIdMtestId.controller.api;

import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;

import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatIdDocIdMtestId.model.beans.PatIdDocIdMtestIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatIdDocIdMtestId.model.beans.PostPatIdDocIdMtestIdRequest;

interface PatIdDocIdMtestId {

    /**
     * Generated from URL POST http://192.168.137.143:1234/exportMasters/postData
     * POST exportMasters/postData
     * @param contentType (original name : Content-Type) style:HEADER
     * @param body  style:PLAIN
     * @param listener
     * @return PatIdDocIdMtestIdResult
     */
    Call<PatIdDocIdMtestIdResult> postPatIdDocIdMtestId(
            String contentType,
            PostPatIdDocIdMtestIdRequest body,
            StateChangedListener listener
    );


}