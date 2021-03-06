/*
  File generated by Magnet rest2mobile 1.1 - Nov 13, 2017 12:23:03 PM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.controller.AbstractControllerSchemaFactory;
import com.magnet.android.mms.controller.ControllerFactory;
import com.magnet.android.mms.controller.RequestSchema;

import java.util.Collections;

import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.IsUpdateMaxResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.PostIsUpdateMaxRequest;

public class PostIsUpdateMaxFactory extends ControllerFactory<PostIsUpdateMax> {
    public PostIsUpdateMaxFactory(MagnetMobileClient magnetClient) {
        super(PostIsUpdateMax.class, PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory.getInstance().getSchema(), magnetClient);
    }

    // Schema factory for controller PostIsUpdateMax
    public static class PostIsUpdateMaxSchemaFactory extends AbstractControllerSchemaFactory {
        private static PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory __instance;

        private PostIsUpdateMaxSchemaFactory() {
        }

        static PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory getInstance() {
            synchronized (PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory.class) {
                if (null == PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory.__instance) {
                    PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory.__instance = new PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory();
                }

                return PostIsUpdateMaxFactory.PostIsUpdateMaxSchemaFactory.__instance;
            }
        }

        protected final void initSchemaMaps() {
            synchronized (this) {
                if (null != this.schema) {
                    return;
                }

                this.schema = new RequestSchema();
                this.schema.setRootPath("");

                //controller schema for controller method postIsUpdateMax
                RequestSchema.JMethod method1 = this.addMethod("postIsUpdateMax",
                        "importMastersSP/postIsUpdateMax",
                        "POST",
                        IsUpdateMaxResult.class,
                        null,
                        Collections.singletonList("application/json"),
                        Collections.singletonList("application/json"));
                method1.setBaseUrl(BaseConfig.AppNodeIP);
                method1.addParam("body",
                        "PLAIN",
                        PostIsUpdateMaxRequest.class,
                        null,
                        "",
                        true);
            }
        }

    }

}
