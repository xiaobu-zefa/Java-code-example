package com.bondex.oms.sc.esentity;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "sc_order_1", type = "_doc", createIndex = false)
public class ScOrderDocType implements Serializable {
    /**
     * 基础信息--订单表主键
     */
    @Id
    @Field(type = FieldType.Long)
    private Long scOrderId;

    @Field(type = FieldType.Keyword)
    private String ATAirOperationStatus;

    @Field(type = FieldType.Keyword)
    private String ATBookingStatus;

    @Field(type = FieldType.Keyword)
    private String ATManifestStatus;

    @Field(type = FieldType.Keyword)
    private String ATStatus;

    @Field(type = FieldType.Text)
    private List<String> DSStatus;

    @Field(type = FieldType.Text)
    private List<String> ISStatus;

    @Field(type = FieldType.Keyword)
    private String TSStatus;

    @Field(type = FieldType.Text)
    private List<String> WSStatus;

    @Field(type = FieldType.Keyword)
    private String accountNo;

    @Field(type = FieldType.Keyword)
    private String agencyOverseasCode;

    @Field(type = FieldType.Keyword)
    private String agencyOverseasName;

    @Field(type = FieldType.Keyword)
    private String agentName;

    @Field(type = FieldType.Double)
    private BigDecimal airCostUnitPrice;

    @Field(type = FieldType.Keyword)
    private String airLineName;

    @Field(type = FieldType.Keyword)
    private String airStatus;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @Field(type = FieldType.Keyword)
    private Date arrivedDate;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date atd;

    @Field(type = FieldType.Keyword)
    private String bookingAgentName;

    @Field(type = FieldType.Keyword)
    private String businessTypeCode;

    @Field(type = FieldType.Keyword)
    private String businessTypeName;

    @Field(type = FieldType.Keyword)
    private String carrierAgentName;

    @Field(type = FieldType.Keyword)
    private String carrierCode;

    @Field(type = FieldType.Keyword)
    private String carrierLineName;

    @Field(type = FieldType.Keyword)
    private String carrierName;

    @Field(type = FieldType.Double)
    private BigDecimal chargedWeight;

    @Field(type = FieldType.Keyword)
    private String chargingDesc;

    @Field(type = FieldType.Keyword)
    private String chargingStatus;

    @Field(type = FieldType.Keyword)
    private String cmsOrderTypeName;

    @Field(type = FieldType.Keyword)
    private String consignorCode;

    @Field(type = FieldType.Keyword)
    private String consignorName;

    @Field(type = FieldType.Keyword)
    private String containerTypeCode;

    @Field(type = FieldType.Keyword)
    private String createBy;

    @Field(type = FieldType.Keyword)
    private String createName;

    @Field(type = FieldType.Keyword)
    private String customerId;

    @Field(type = FieldType.Keyword)
    private String customerName;

    @Field(type = FieldType.Keyword)
    private String customerNo;

    @Field(type = FieldType.Keyword)
    private String customsBroker;

    @Field(type = FieldType.Keyword)
    private String customsStatus;

    @Field(type = FieldType.Keyword)
    private String customsVoyageNo;

    @Field(type = FieldType.Keyword)
    private String cyCutOff;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date delayedSailingDay;

    @Field(type = FieldType.Keyword)
    private String deliveryCenterOpId;

    @Field(type = FieldType.Keyword)
    private String deliveryCenterOpName;

    @Field(type = FieldType.Keyword)
    private String deliveryMethod;

    @Field(type = FieldType.Keyword)
    private String deliveryMethodName;

    @Field(type = FieldType.Keyword)
    private String deliveryPartyNameEn;

    @Field(type = FieldType.Keyword)
    private String dispatchStowageNo;

    @Field(type = FieldType.Keyword)
    private String docName;

    @Field(type = FieldType.Keyword)
    private String docOpid;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date etd;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date flightDate;

    @Field(type = FieldType.Keyword)
    private String flightNo;

    @Field(type = FieldType.Keyword)
    private String freightClauseName;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date gmtCreate;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date gmtModified;

    @Field(type = FieldType.Double)
    private BigDecimal grossWeight;

    @Field(type = FieldType.Double)
    private BigDecimal insuranceAmount;

    @Field(type = FieldType.Keyword)
    private String insuranceNo;

    @Field(type = FieldType.Keyword)
    private String insuranceRatio;

    @Field(type = FieldType.Keyword)
    private String insuranceStatus;

    @Field(type = FieldType.Keyword)
    private String insuranceType;

    @Field(type = FieldType.Keyword)
    private String insuredName;

    @Field(type = FieldType.Keyword)
    private String insuredTel;

    @Field(type = FieldType.Double)
    private BigDecimal invoiceAmount;

    @Field(type = FieldType.Keyword)
    private String invoiceCurrency;

    @Field(type = FieldType.Keyword)
    private String ioType;

    @Field(type = FieldType.Boolean)
    private Boolean isDispatchStowage;

    @Field(type = FieldType.Boolean)
    private Boolean isOrder;

    @Field(type = FieldType.Keyword)
    private String isTaxFree;

    @Field(type = FieldType.Keyword)
    private String masterNo;

    @Field(type = FieldType.Keyword)
    private String mblNo;

    @Field(type = FieldType.Keyword)
    private String modifiedBy;

    @Field(type = FieldType.Keyword)
    private String modifiedName;

    @Field(type = FieldType.Keyword)
    private String orderNo;

    @Field(type = FieldType.Keyword)
    private String orderStatus;

    @Field(type = FieldType.Keyword)
    private String orderType;

    @Field(type = FieldType.Keyword)
    private String orderTypeName;

    @Field(type = FieldType.Keyword)
    private String originOrderNo;

    @Field(type = FieldType.Keyword)
    private String overseaAgentName;

    @Field(type = FieldType.Keyword)
    private String pAutomaticBillingDesc;

    @Field(type = FieldType.Integer)
    private Integer pAutomaticBillingStatus;

    @Field(type = FieldType.Keyword)
    private String payType;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date pickupDate;

    @Field(type = FieldType.Keyword)
    private String pickupDeliveryCenterOpId;

    @Field(type = FieldType.Keyword)
    private String pickupDeliveryCenterOpName;

    @Field(type = FieldType.Keyword)
    private String polCode;

    @Field(type = FieldType.Keyword)
    private String polName;

    @Field(type = FieldType.Keyword)
    private String portOfDelivery;

    @Field(type = FieldType.Keyword)
    private String portOfDeliveryName;

    @Field(type = FieldType.Keyword)
    private String portOfDestCode;

    @Field(type = FieldType.Keyword)
    private String portOfDestName;

    @Field(type = FieldType.Keyword)
    private String portOfDischarge;

    @Field(type = FieldType.Keyword)
    private String portOfDischargeName;

    @Field(type = FieldType.Keyword)
    private String portOfLoading;

    @Field(type = FieldType.Keyword)
    private String portOfLoadingName;

    @Field(type = FieldType.Keyword)
    private String portOfTransshipmentName;

    @Field(type = FieldType.Keyword)
    private String potCode;

    @Field(type = FieldType.Keyword)
    private String potName;

    @Field(type = FieldType.Keyword)
    private String product;

    @Field(type = FieldType.Keyword)
    private String productName;

    @Field(type = FieldType.Keyword)
    private String productOpid;

    @Field(type = FieldType.Keyword)
    private String productOpname;

    @Field(type = FieldType.Keyword)
    private String productType;

    @Field(type = FieldType.Keyword)
    private String quotationNo;

    @Field(type = FieldType.Keyword)
    private String receiverPartyNameEn;

    @Field(type = FieldType.Keyword)
    private String routeAcceptanceStatus;

    @Field(type = FieldType.Keyword)
    private String routeOperatorId;

    @Field(type = FieldType.Keyword)
    private String routeOperatorName;

    @Field(type = FieldType.Keyword)
    private String saleCode;

    @Field(type = FieldType.Keyword)
    private String saleName;

    @Field(type = FieldType.Double)
    private BigDecimal saleUnitPrice;

    @Field(type = FieldType.Keyword)
    private String seaOperatorId;

    @Field(type = FieldType.Keyword)
    private String seaOperatorName;

    @Field(type = FieldType.Keyword)
    private String sendAirEntrustDesc;

    @Field(type = FieldType.Integer)
    private Integer sendAirEntrustStatus;

    @Field(type = FieldType.Keyword)
    private String sendFeeDesc;

    @Field(type = FieldType.Keyword)
    private String sendFeeStatus;

    @Field(type = FieldType.Keyword)
    private String sendOrderEntrustDesc;

    @Field(type = FieldType.Keyword)
    private String sendOrderEntrustStatus;

    @Field(type = FieldType.Keyword)
    private String sendPFeeDesc;

    @Field(type = FieldType.Integer)
    private Integer sendPFeeStatus;

    @Field(type = FieldType.Text)
    private List<String> serviceList;

    @Field(type = FieldType.Keyword)
    private String serviceStatus;

    @Field(type = FieldType.Keyword)
    private String settlementStatus;

    @Field(type = FieldType.Keyword)
    private String shippingStatus;

    @Field(type = FieldType.Keyword)
    private String siClosing;

    @Field(type = FieldType.Keyword)
    private String signingType;

    @Field(type = FieldType.Keyword)
    private String signingTypeNameEn;

    @Field(type = FieldType.Keyword)
    private String soNo;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Keyword)
    private String splitMark;

    @Field(type = FieldType.Keyword)
    private String ssDeliveryPartyNameEn;

    @Field(type = FieldType.Keyword)
    private String ssReceiverPartyNameEn;

    @Field(type = FieldType.Keyword)
    private String terminalName;

    @Field(type = FieldType.Keyword)
    private String tmsOperatorId;

    @Field(type = FieldType.Keyword)
    private String tmsOperatorName;

    @Field(type = FieldType.Keyword)
    private String tmsOrderTypeName;

    @Field(type = FieldType.Keyword)
    private String tradeClauseName;

    @Field(type = FieldType.Keyword)
    private String tradeLineCode;

    @Field(type = FieldType.Keyword)
    private String tradeLineName;

    @Field(type = FieldType.Keyword)
    private String tradeType;

    @Field(type = FieldType.Keyword)
    private String transportStatus;

    @Field(type = FieldType.Keyword)
    private String typeName1;

    @Field(type = FieldType.Keyword)
    private String typeName2;

    @Field(type = FieldType.Keyword)
    private String typeName3;

    @Field(type = FieldType.Keyword)
    private String typeName4;

    @Field(type = FieldType.Keyword)
    private String typeName5;

    @Field(type = FieldType.Integer)
    private Integer units;

    @Field(type = FieldType.Keyword)
    private String updateStatus;

    @Field(type = FieldType.Keyword)
    private String versionNum;

    @Field(type = FieldType.Keyword)
    private String vesselName;

    @Field(type = FieldType.Keyword)
    private String vesselNo;

    @Field(type = FieldType.Double)
    private BigDecimal volume;

    @Field(type = FieldType.Keyword)
    private String voyageCode;

    @Field(type = FieldType.Keyword)
    private String voyageNo;

    @Field(type = FieldType.Keyword)
    private String warehouseName;

    @Field(type = FieldType.Keyword)
    private String warehouseStatus;

    @Field(type = FieldType.Keyword)
    private String wmsType;

    /**
     * 节点--海运节点Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String shippingStatusName;

    /**
     * 节点--空运订单状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String airStatusName;

    /**
     * 节点--空运订舱状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String airBookingStatusName;

    /**
     * 节点--空运舱单状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String airManifestStatusName;

    /**
     * 节点--空运地面状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String airAirOperationStatusName;

    /**
     * 节点--报关状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String customsStatusName;

    /**
     * 节点--陆运状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String transportStatusName;

    /**
     * 节点--仓储状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String wmsStatusName;

    /**
     * 节点--保险状态Name（服务图标展示用）
     */
    @Field(type = FieldType.Keyword)
    private String insuranceStatusName;
}
