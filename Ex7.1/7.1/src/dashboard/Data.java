
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
    "id",
    "symbol",
    "name",
    "market_data",
    "supply",
    "blockchain_stats_last_24_hours",
    "all_time_high",
    "developer_activity",
    "roi_data",
    "misc_data"
})
public class Data {

    @JsonProperty("id")
    private String id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("market_data")
    private MarketData marketData;
    @JsonProperty("supply")
    private Supply supply;
    @JsonProperty("blockchain_stats_last_24_hours")
    private BlockchainStatsLast24Hours blockchainStatsLast24Hours;
    @JsonProperty("all_time_high")
    private AllTimeHigh allTimeHigh;
    @JsonProperty("developer_activity")
    private DeveloperActivity developerActivity;
    @JsonProperty("roi_data")
    private RoiData roiData;
    @JsonProperty("misc_data")
    private MiscData miscData;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("market_data")
    public MarketData getMarketData() {
        return marketData;
    }

    @JsonProperty("market_data")
    public void setMarketData(MarketData marketData) {
        this.marketData = marketData;
    }

    @JsonProperty("supply")
    public Supply getSupply() {
        return supply;
    }

    @JsonProperty("supply")
    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    @JsonProperty("blockchain_stats_last_24_hours")
    public BlockchainStatsLast24Hours getBlockchainStatsLast24Hours() {
        return blockchainStatsLast24Hours;
    }

    @JsonProperty("blockchain_stats_last_24_hours")
    public void setBlockchainStatsLast24Hours(BlockchainStatsLast24Hours blockchainStatsLast24Hours) {
        this.blockchainStatsLast24Hours = blockchainStatsLast24Hours;
    }

    @JsonProperty("all_time_high")
    public AllTimeHigh getAllTimeHigh() {
        return allTimeHigh;
    }

    @JsonProperty("all_time_high")
    public void setAllTimeHigh(AllTimeHigh allTimeHigh) {
        this.allTimeHigh = allTimeHigh;
    }

    @JsonProperty("developer_activity")
    public DeveloperActivity getDeveloperActivity() {
        return developerActivity;
    }

    @JsonProperty("developer_activity")
    public void setDeveloperActivity(DeveloperActivity developerActivity) {
        this.developerActivity = developerActivity;
    }

    @JsonProperty("roi_data")
    public RoiData getRoiData() {
        return roiData;
    }

    @JsonProperty("roi_data")
    public void setRoiData(RoiData roiData) {
        this.roiData = roiData;
    }

    @JsonProperty("misc_data")
    public MiscData getMiscData() {
        return miscData;
    }

    @JsonProperty("misc_data")
    public void setMiscData(MiscData miscData) {
        this.miscData = miscData;
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
