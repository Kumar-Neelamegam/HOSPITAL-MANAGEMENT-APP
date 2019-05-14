/*
  File generated by Magnet rest2mobile 1.1 - Nov 13, 2017 6:43:40 PM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatIdDocIdMtestId.controller.api;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.controller.AbstractControllerSchemaFactory;
import com.magnet.android.mms.controller.ControllerFactory;
import com.magnet.android.mms.controller.RequestSchema;

import java.util.Collections;

import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatIdDocIdMtestId.model.beans.PosPatIdDocIdMtestIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatIdDocIdMtestId.model.beans.PosPatIdDocIdMtestIdResult;

public class PostPatIdDocIdMtestIdFactory extends ControllerFactory<PostPatIdDocIdMtestId> {
    public PostPatIdDocIdMtestIdFactory(MagnetMobileClient magnetClient) {
        super(PostPatIdDocIdMtestId.class, PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory.getInstance().getSchema(), magnetClient);
    }

    // Schema factory for controller PostPatIdDocIdMtestId
    public static class PostPatIdDocIdMtestIdSchemaFactory extends AbstractControllerSchemaFactory {
        private static PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory __instance;

        private PostPatIdDocIdMtestIdSchemaFactory() {
        }

        static PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory getInstance() {
            synchronized (PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory.class) {
                if (null == PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory.__instance) {
                    PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory.__instance = new PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory();
                }

                return PostPatIdDocIdMtestIdFactory.PostPatIdDocIdMtestIdSchemaFactory.__instance;
            }
        }

        protected final void initSchemaMaps() {
            synchronized (this) {
                if (null != this.schema) {
                    return;
                }

                this.schema = new RequestSchema();
                this.schema.setRootPath("");

                //controller schema for controller method posPatIdDocIdMtestId
                RequestSchema.JMethod method1 = this.addMethod("postDoctorIdPatidMtestId",
                        "importMastersSP/postDoctorIdPatidMtestId",
                        "POST",
                        PosPatIdDocIdMtestIdResult.class,
                        null,
                        Collections.singletonList("application/json"),
                        Collections.singletonList("application/json"));
                method1.setBaseUrl(BaseConfig.AppNodeIP);
                method1.addParam("body",
                        "PLAIN",
                        PosPatIdDocIdMtestIdRequest.class,
                        null,
                        "",
                        true);
            }
        }

    }

}
