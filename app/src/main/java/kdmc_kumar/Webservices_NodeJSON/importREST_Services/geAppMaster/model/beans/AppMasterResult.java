/*
  File generated by Magnet rest2mobile 1.1 - 9 Feb, 2018 2:56:41 AM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.geAppMaster.model.beans;


/**
 * Generated from json example
 {
 "Results" : ""
 }

 */

public class AppMasterResult {


    @com.google.gson.annotations.SerializedName("Results")
    private String results;

    public AppMasterResult() {
    }

    public final String getResults() {
        return this.results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    /**
     * Builder for AppMasterResult
     **/
    public static class AppMasterResultBuilder {
        private final AppMasterResult toBuild = new AppMasterResult();

        public AppMasterResultBuilder() {
        }

        public final AppMasterResult build() {
            return this.toBuild;
        }

        public final AppMasterResult.AppMasterResultBuilder results(String value) {
            this.toBuild.setResults(value);
            return this;
        }
    }
}
