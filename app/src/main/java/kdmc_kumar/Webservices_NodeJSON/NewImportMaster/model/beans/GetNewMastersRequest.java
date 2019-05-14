/**
 * File generated by Magnet rest2mobile 1.1 - 17 Apr, 2018 9:56:32 AM
 * @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.NewImportMaster.model.beans;


/**
 * Generated from json example
{
  "IsUpdateMax" : "0",
  "TableId" : "0",
  "MasterId" : "0"
}

 */

public class GetNewMastersRequest {

  
@com.google.gson.annotations.SerializedName("IsUpdateMax")
  private String isUpdateMax;

  
@com.google.gson.annotations.SerializedName("MasterId")
  private String masterId;

  
@com.google.gson.annotations.SerializedName("TableId")
  private String tableId;

  public String getIsUpdateMax() {
    return this.isUpdateMax;
  }
  public String getMasterId() {
    return this.masterId;
  }
  public String getTableId() {
    return this.tableId;
  }


  public void setIsUpdateMax(String isUpdateMax) {
    this.isUpdateMax = isUpdateMax;
  }

  public void setMasterId(String masterId) {
    this.masterId = masterId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }

  /**
  * Builder for GetNewMastersRequest
  **/
  public static class GetNewMastersRequestBuilder {
    private final GetNewMastersRequest toBuild = new GetNewMastersRequest();

    public GetNewMastersRequestBuilder() {
    }

    public GetNewMastersRequest build() {
      return this.toBuild;
    }

    public GetNewMastersRequest.GetNewMastersRequestBuilder isUpdateMax(String value) {
        this.toBuild.isUpdateMax = value;
      return this;
    }
    public GetNewMastersRequest.GetNewMastersRequestBuilder masterId(String value) {
        this.toBuild.masterId = value;
      return this;
    }
    public GetNewMastersRequest.GetNewMastersRequestBuilder tableId(String value) {
        this.toBuild.tableId = value;
      return this;
    }
  }
}