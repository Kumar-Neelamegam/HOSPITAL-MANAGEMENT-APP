/*
  File generated by Magnet rest2mobile 1.1 - Nov 24, 2017 11:07:28 AM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.ExportData.controller.api;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.controller.AbstractControllerSchemaFactory;
import com.magnet.android.mms.controller.ControllerFactory;
import com.magnet.android.mms.controller.RequestSchema;

import java.util.Collections;

import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Webservices_NodeJSON.ExportData.model.beans.Export_DatasRequest;
import kdmc_kumar.Webservices_NodeJSON.ExportData.model.beans.Export_DatasResult;


public class Export_All_DataFactory extends ControllerFactory<Export_All_Data> {
    public Export_All_DataFactory(MagnetMobileClient magnetClient) {
        super(Export_All_Data.class, Export_All_DataFactory.Export_All_DataSchemaFactory.getInstance().getSchema(), magnetClient);
    }

    // Schema factory for controller Export_All_Data
    public static class Export_All_DataSchemaFactory extends AbstractControllerSchemaFactory {
        private static Export_All_DataFactory.Export_All_DataSchemaFactory __instance;

        private Export_All_DataSchemaFactory() {
        }

        static Export_All_DataFactory.Export_All_DataSchemaFactory getInstance() {
            synchronized (Export_All_DataFactory.Export_All_DataSchemaFactory.class) {
                if (null == Export_All_DataFactory.Export_All_DataSchemaFactory.__instance) {
                    Export_All_DataFactory.Export_All_DataSchemaFactory.__instance = new Export_All_DataFactory.Export_All_DataSchemaFactory();
                }

                return Export_All_DataFactory.Export_All_DataSchemaFactory.__instance;
            }
        }

        protected final void initSchemaMaps() {
            synchronized (this) {
                if (null != this.schema) {
                    return;
                }

                this.schema = new RequestSchema();
                this.schema.setRootPath("");

                //controller schema for controller method export_Datas
                RequestSchema.JMethod method1 = this.addMethod("export_Datas",
                        "exportMasters/postData",
                        "POST",
                        Export_DatasResult.class,
                        null,
                        Collections.singletonList("application/json"),
                        Collections.singletonList("application/json"));
                method1.setBaseUrl(BaseConfig.AppNodeIP);
                method1.addParam("Content-Type",
                        "HEADER",
                        String.class,
                        null,
                        "",
                        true);
                method1.addParam("body",
                        "PLAIN",
                        Export_DatasRequest.class,
                        null,
                        "",
                        true);
            }
        }

    }

}