/*
  File generated by Magnet rest2mobile 1.1 - Nov 24, 2017 11:07:28 AM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.ExportData.controller.api;


import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;

import kdmc_kumar.Webservices_NodeJSON.ExportData.model.beans.Export_DatasRequest;
import kdmc_kumar.Webservices_NodeJSON.ExportData.model.beans.Export_DatasResult;


interface Export_All_Data {

    /**
     * Generated from URL POST http://192.168.137.85:1234/exportMasters/postData
     * POST exportMasters/postData
     * @param contentType (original name : Content-Type) style:HEADER
     * @param body  style:PLAIN
     * @param listener
     * @return Export_DatasResult
     */
    Call<Export_DatasResult> export_Datas(
            String contentType,
            Export_DatasRequest body,
            StateChangedListener listener
    );


}