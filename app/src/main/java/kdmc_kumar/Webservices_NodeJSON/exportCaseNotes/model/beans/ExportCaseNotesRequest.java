/*
  File generated by Magnet rest2mobile 1.1 - 27 Mar, 2018 5:10:19 PM
  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.exportCaseNotes.model.beans;


/**
 * Generated from json example
{
  "MethodName" : "INSERT_ALL_CASENOTES",
  "JsonValue" : ""
}

 */

public class ExportCaseNotesRequest {

  
@com.google.gson.annotations.SerializedName("JsonValue")
  private String jsonValue;

  
@com.google.gson.annotations.SerializedName("MethodName")
  private String methodName;

    public ExportCaseNotesRequest() {
    }

    public final String getJsonValue() {
    return this.jsonValue;
  }
  public final String getMethodName() {
    return this.methodName;
  }

  public final void setJsonValue(String jsonValue) {
    this.jsonValue = jsonValue;
  }

  public final void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  /**
  * Builder for ExportCaseNotesRequest
  **/
  public static class ExportCaseNotesRequestBuilder {
    private final ExportCaseNotesRequest toBuild = new ExportCaseNotesRequest();

    public ExportCaseNotesRequestBuilder() {
    }

    public final ExportCaseNotesRequest build() {
      return this.toBuild;
    }

    public final ExportCaseNotesRequest.ExportCaseNotesRequestBuilder jsonValue(String value) {
        this.toBuild.setJsonValue(value);
      return this;
    }
    public final ExportCaseNotesRequest.ExportCaseNotesRequestBuilder methodName(String value) {
        this.toBuild.setMethodName(value);
      return this;
    }
  }
}
