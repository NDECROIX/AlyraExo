
package dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "vladimir_club_cost",
    "asset_age_days",
    "categories",
    "sectors"
})
public class MiscData {

    @JsonProperty("vladimir_club_cost")
    private Double vladimirClubCost;
    @JsonProperty("asset_age_days")
    private Integer assetAgeDays;
    @JsonProperty("categories")
    private List<String> categories = null;
    @JsonProperty("sectors")
    private List<String> sectors = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("vladimir_club_cost")
    public Double getVladimirClubCost() {
        return vladimirClubCost;
    }

    @JsonProperty("vladimir_club_cost")
    public void setVladimirClubCost(Double vladimirClubCost) {
        this.vladimirClubCost = vladimirClubCost;
    }

    @JsonProperty("asset_age_days")
    public Integer getAssetAgeDays() {
        return assetAgeDays;
    }

    @JsonProperty("asset_age_days")
    public void setAssetAgeDays(Integer assetAgeDays) {
        this.assetAgeDays = assetAgeDays;
    }

    @JsonProperty("categories")
    public List<String> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @JsonProperty("sectors")
    public List<String> getSectors() {
        return sectors;
    }

    @JsonProperty("sectors")
    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
