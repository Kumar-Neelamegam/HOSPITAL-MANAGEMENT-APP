/*
  File generated by Magnet rest2mobile 1.1 - Nov 14, 2017 10:57:15 AM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans;


/**
 * Generated from json example
 {
 "Results" : ""
 }

 */

public class PatidMtestidResult {


    @com.google.gson.annotations.SerializedName("Results")
    private String results;

    public PatidMtestidResult() {
    }

    public final String getResults() {
        return this.results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    /**
     * Builder for PatidMtestidResult
     **/
    public static class PatidMtestidResultBuilder {
        private final PatidMtestidResult toBuild = new PatidMtestidResult();

        public PatidMtestidResultBuilder() {
        }

        public final PatidMtestidResult build() {
            return this.toBuild;
        }

        public final PatidMtestidResult.PatidMtestidResultBuilder results(String value) {
            this.toBuild.setResults(value);
            return this;
        }
    }
}