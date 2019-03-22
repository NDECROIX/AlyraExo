
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
    "transaction_volume",
    "nvt",
    "sum_of_fees",
    "median_tx_value",
    "median_tx_fee",
    "count_of_active_addresses",
    "count_of_tx",
    "count_of_payments",
    "new_issuance",
    "average_difficulty",
    "kilobytes_added",
    "count_of_blocks_added"
})
public class BlockchainStatsLast24Hours {

    @JsonProperty("transaction_volume")
    private Double transactionVolume;
    @JsonProperty("nvt")
    private Double nvt;
    @JsonProperty("sum_of_fees")
    private Double sumOfFees;
    @JsonProperty("median_tx_value")
    private Double medianTxValue;
    @JsonProperty("median_tx_fee")
    private Double medianTxFee;
    @JsonProperty("count_of_active_addresses")
    private Integer countOfActiveAddresses;
    @JsonProperty("count_of_tx")
    private Integer countOfTx;
    @JsonProperty("count_of_payments")
    private Integer countOfPayments;
    @JsonProperty("new_issuance")
    private Double newIssuance;
    @JsonProperty("average_difficulty")
    private Double averageDifficulty;
    @JsonProperty("kilobytes_added")
    private Double kilobytesAdded;
    @JsonProperty("count_of_blocks_added")
    private Integer countOfBlocksAdded;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transaction_volume")
    public Double getTransactionVolume() {
        return transactionVolume;
    }

    @JsonProperty("transaction_volume")
    public void setTransactionVolume(Double transactionVolume) {
        this.transactionVolume = transactionVolume;
    }

    @JsonProperty("nvt")
    public Double getNvt() {
        return nvt;
    }

    @JsonProperty("nvt")
    public void setNvt(Double nvt) {
        this.nvt = nvt;
    }

    @JsonProperty("sum_of_fees")
    public Double getSumOfFees() {
        return sumOfFees;
    }

    @JsonProperty("sum_of_fees")
    public void setSumOfFees(Double sumOfFees) {
        this.sumOfFees = sumOfFees;
    }

    @JsonProperty("median_tx_value")
    public Double getMedianTxValue() {
        return medianTxValue;
    }

    @JsonProperty("median_tx_value")
    public void setMedianTxValue(Double medianTxValue) {
        this.medianTxValue = medianTxValue;
    }

    @JsonProperty("median_tx_fee")
    public Double getMedianTxFee() {
        return medianTxFee;
    }

    @JsonProperty("median_tx_fee")
    public void setMedianTxFee(Double medianTxFee) {
        this.medianTxFee = medianTxFee;
    }

    @JsonProperty("count_of_active_addresses")
    public Integer getCountOfActiveAddresses() {
        return countOfActiveAddresses;
    }

    @JsonProperty("count_of_active_addresses")
    public void setCountOfActiveAddresses(Integer countOfActiveAddresses) {
        this.countOfActiveAddresses = countOfActiveAddresses;
    }

    @JsonProperty("count_of_tx")
    public Integer getCountOfTx() {
        return countOfTx;
    }

    @JsonProperty("count_of_tx")
    public void setCountOfTx(Integer countOfTx) {
        this.countOfTx = countOfTx;
    }

    @JsonProperty("count_of_payments")
    public Integer getCountOfPayments() {
        return countOfPayments;
    }

    @JsonProperty("count_of_payments")
    public void setCountOfPayments(Integer countOfPayments) {
        this.countOfPayments = countOfPayments;
    }

    @JsonProperty("new_issuance")
    public Double getNewIssuance() {
        return newIssuance;
    }

    @JsonProperty("new_issuance")
    public void setNewIssuance(Double newIssuance) {
        this.newIssuance = newIssuance;
    }

    @JsonProperty("average_difficulty")
    public Double getAverageDifficulty() {
        return averageDifficulty;
    }

    @JsonProperty("average_difficulty")
    public void setAverageDifficulty(Double averageDifficulty) {
        this.averageDifficulty = averageDifficulty;
    }

    @JsonProperty("kilobytes_added")
    public Double getKilobytesAdded() {
        return kilobytesAdded;
    }

    @JsonProperty("kilobytes_added")
    public void setKilobytesAdded(Double kilobytesAdded) {
        this.kilobytesAdded = kilobytesAdded;
    }

    @JsonProperty("count_of_blocks_added")
    public Integer getCountOfBlocksAdded() {
        return countOfBlocksAdded;
    }

    @JsonProperty("count_of_blocks_added")
    public void setCountOfBlocksAdded(Integer countOfBlocksAdded) {
        this.countOfBlocksAdded = countOfBlocksAdded;
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
