
package dashboard;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "price_usd",
    "price_btc",
    "volume_last_24_hours",
    "percent_change_usd_last_24_hours",
    "percent_change_btc_last_24_hours"
})
public class MarketData {

    @JsonProperty("price_usd")
    private Double priceUsd;
    @JsonProperty("price_btc")
    private Integer priceBtc;
    @JsonProperty("volume_last_24_hours")
    private Double volumeLast24Hours;
    @JsonProperty("percent_change_usd_last_24_hours")
    private Double percentChangeUsdLast24Hours;
    @JsonProperty("percent_change_btc_last_24_hours")
    private Integer percentChangeBtcLast24Hours;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("price_usd")
    public Double getPriceUsd() {
        return priceUsd;
    }

    @JsonProperty("price_usd")
    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    @JsonProperty("price_btc")
    public Integer getPriceBtc() {
        return priceBtc;
    }

    @JsonProperty("price_btc")
    public void setPriceBtc(Integer priceBtc) {
        this.priceBtc = priceBtc;
    }

    @JsonProperty("volume_last_24_hours")
    public Double getVolumeLast24Hours() {
        return volumeLast24Hours;
    }

    @JsonProperty("volume_last_24_hours")
    public void setVolumeLast24Hours(Double volumeLast24Hours) {
        this.volumeLast24Hours = volumeLast24Hours;
    }

    @JsonProperty("percent_change_usd_last_24_hours")
    public Double getPercentChangeUsdLast24Hours() {
        return percentChangeUsdLast24Hours;
    }

    @JsonProperty("percent_change_usd_last_24_hours")
    public void setPercentChangeUsdLast24Hours(Double percentChangeUsdLast24Hours) {
        this.percentChangeUsdLast24Hours = percentChangeUsdLast24Hours;
    }

    @JsonProperty("percent_change_btc_last_24_hours")
    public Integer getPercentChangeBtcLast24Hours() {
        return percentChangeBtcLast24Hours;
    }

    @JsonProperty("percent_change_btc_last_24_hours")
    public void setPercentChangeBtcLast24Hours(Integer percentChangeBtcLast24Hours) {
        this.percentChangeBtcLast24Hours = percentChangeBtcLast24Hours;
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
