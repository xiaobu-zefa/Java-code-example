package site.xiaobu.elasticsearch;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bondex.oms.sc.esentity.ScOrderDocType;
import com.bondex.oms.sc.index.ScOrderIndex;
import lombok.SneakyThrows;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import site.xiaobu.elasticsearch.index.DocumentTypeIndex;
import site.xiaobu.elasticsearch.type.DocumentType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private DocumentTypeIndex documentTypeIndex;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ScOrderIndex scOrderIndex;

    @Test
    public void testSave() {
        DocumentType documentType = new DocumentType();
        documentType.setId("2");
        documentType.setName("泰罗");
        documentType.setRemark("泰罗是奥特之父的6儿子");
        documentType.setCreateTime(LocalDateTime.now());
        documentType.setModifyTime(LocalDateTime.now());
        documentType.setIsPrivate(false);
        documentType.setIsImportant(true);

        documentType = documentTypeIndex.save(documentType);
        System.out.println(documentType.getId());
    }

    @Test
    public void testFindAll() {
        Iterable<DocumentType> types = documentTypeIndex.findAll();
        for (DocumentType type : types) {
            System.out.print(type.getName());
            System.out.println(type.getCreateTime());
        }

        System.out.println("==================================== 自定义查询条件 ====================================");
        Criteria criteria = new Criteria();
        criteria.and(new Criteria("id").is("2"));
        criteria.and(new Criteria("isPrivate").in(true, false));
        criteria.and(new Criteria("name").is("泰罗"));
        criteria.and(new Criteria().contains("奥特曼").or(new Criteria("泰罗")));


        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<DocumentType> hits = elasticsearchRestTemplate.search(criteriaQuery, DocumentType.class);
        List<DocumentType> documentTypes = hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        for (DocumentType documentType : documentTypes) {
            System.out.println(documentType.getName());
        }
    }

    @SneakyThrows
    @Test
    public void testScOrderSpringEsServiceSearch() {
        Criteria criteria = new Criteria();
        criteria.and(new Criteria("airLineName").is("台湾中华航空股份有限公司"));

        SearchHits<ScOrderDocType> searchHits = elasticsearchRestTemplate.search(new CriteriaQuery(criteria), ScOrderDocType.class);
        System.out.println(searchHits);
    }

    @SneakyThrows
    @Test
    public void testScOrderSpringEsServiceSearchId() {
        ScOrderDocType scOrderDocType = elasticsearchRestTemplate.get("1003", ScOrderDocType.class);
        System.out.println(scOrderDocType);
    }

    @SneakyThrows
    @Test
    public void testScOrderSpringEsServiceSearchPage() {

        Criteria criteria = new Criteria();
        criteria.and(new Criteria("quotationNo").is("QM22112900210"));
        SearchHits<ScOrderDocType> searchHits = elasticsearchRestTemplate.search(new CriteriaQuery(criteria, PageRequest.of(1,2)), ScOrderDocType.class);
        System.out.println(searchHits);
    }

    @SneakyThrows
    @Test
    public void testScOrderEsServiceSave() {
        ScOrderDocType scOrderDocType = buildDoc();
        scOrderDocType.setScOrderId(1002L);
        scOrderDocType.setDSStatus(ListUtil.list(false,"状态1","状态2","状态3"));
        scOrderDocType.setISStatus(ListUtil.list(false,"状态1","状态2","状态3"));
        scOrderDocType.setWSStatus(ListUtil.list(false,"状态1","状态2","状态3"));
        scOrderDocType.setServiceList(ListUtil.list(false,"服务1","服务2","服务3"));

        Field[] fields = ReflectUtil.getFields(ScOrderDocType.class);
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object o = field.get(scOrderDocType);

            if (type.isAssignableFrom(String.class)) {
                if (StrUtil.isBlank((String) o)) {
                    field.set(scOrderDocType, IdUtil.fastUUID());
                }
            }

            if (type.isAssignableFrom(Long.class)) {
                if (o == null) {
                    field.set(scOrderDocType, 1558915L);
                }
            }

            if (type.isAssignableFrom(BigDecimal.class)) {
                if (o == null) {
                    field.set(scOrderDocType, BigDecimal.ZERO);
                }
            }

            if (type.isAssignableFrom(Boolean.class)) {
                if (o == null) {
                    field.set(scOrderDocType, true);
                }
            }

            if (type.isAssignableFrom(Integer.class)) {
                if (o == null) {
                    field.set(scOrderDocType, 2002);
                }
            }

            if (type.isAssignableFrom(Date.class)) {
                if (o == null) {
                    field.set(scOrderDocType, DateUtil.date());
                }
            }

            field.setAccessible(false);
        }

        scOrderIndex.save(scOrderDocType);
    }

    @SneakyThrows
    @Test
    public void testScOrderEsServiceSaveAllFieldIsNull() {
        ScOrderDocType scOrderDocType = new ScOrderDocType();
        scOrderDocType.setScOrderId(1003L);

        scOrderIndex.save(scOrderDocType);
    }
    private ScOrderDocType buildDoc(){
        String json = "{\n" +
                "    \"scOrderId\": 21122,\n" +
                "    \"customerId\": \"28069\",\n" +
                "    \"customerName\": \"CD显示操作/李娟\",\n" +
                "    \"orderNo\": \"OMS22112502686\",\n" +
                "    \"source\": \"SC\",\n" +
                "    \"customerNo\": \"\",\n" +
                "    \"masterNo\": \"297-16226556\",\n" +
                "    \"consignorName\": \"韩中国际货运（上海）有限公司青岛分公司\",\n" +
                "    \"createName\": \"CD显示操作/李娟\",\n" +
                "    \"createBy\": \"28069\",\n" +
                "    \"gmtCreate\": \"2022-11-25 16:42:44\",\n" +
                "    \"gmtModified\": \"2022-11-29 16:56:46\",\n" +
                "    \"modifiedName\": \"CD显示操作/李娟\",\n" +
                "    \"modifiedBy\": \"28069\",\n" +
                "    \"saleName\": \"肖宇翔\",\n" +
                "    \"saleCode\": \"28032\",\n" +
                "    \"settlementStatus\": \"0\",\n" +
                "    \"orderStatus\": \"2\",\n" +
                "    \"tradeClauseName\": \"\",\n" +
                "    \"quotationNo\": \"QM22112900210\",\n" +
                "    \"versionNum\": \"20221129135149\",\n" +
                "    \"isTaxFree\": \"0\",\n" +
                "    \"businessTypeName\": null,\n" +
                "    \"productOpname\": null,\n" +
                "    \"productOpid\": null,\n" +
                "    \"docName\": null,\n" +
                "    \"docOpid\": null,\n" +
                "    \"seaOperatorName\": null,\n" +
                "    \"seaOperatorId\": null,\n" +
                "    \"overseaAgentName\": null,\n" +
                "    \"bookingAgentName\": null,\n" +
                "    \"agentName\": null,\n" +
                "    \"soNo\": null,\n" +
                "    \"mblNo\": null,\n" +
                "    \"containerTypeCode\": null,\n" +
                "    \"carrierName\": null,\n" +
                "    \"vesselNo\": null,\n" +
                "    \"vesselName\": null,\n" +
                "    \"voyageCode\": null,\n" +
                "    \"voyageNo\": null,\n" +
                "    \"customsVoyageNo\": null,\n" +
                "    \"carrierAgentName\": null,\n" +
                "    \"etd\": null,\n" +
                "    \"delayedSailingDay\": null,\n" +
                "    \"atd\": null,\n" +
                "    \"siClosing\": null,\n" +
                "    \"cyCutOff\": null,\n" +
                "    \"carrierLineName\": null,\n" +
                "    \"portOfLoadingName\": null,\n" +
                "    \"portOfTransshipmentName\": null,\n" +
                "    \"portOfDischargeName\": null,\n" +
                "    \"portOfDeliveryName\": null,\n" +
                "    \"tradeLineName\": null,\n" +
                "    \"terminalName\": null,\n" +
                "    \"signingType\": null,\n" +
                "    \"signingTypeNameEn\": null,\n" +
                "    \"freightClauseName\": null,\n" +
                "    \"deliveryMethod\": null,\n" +
                "    \"deliveryMethodName\": null,\n" +
                "    \"ssReceiverPartyNameEn\": null,\n" +
                "    \"ssDeliveryPartyNameEn\": null,\n" +
                "    \"polName\": \"重庆江北国际机场\",\n" +
                "    \"potName\": \"台湾桃园国际机场\",\n" +
                "    \"portOfDestName\": \"河内内拜机场\",\n" +
                "    \"isOrder\": true,\n" +
                "    \"splitMark\": \"0\",\n" +
                "    \"sendAirEntrustStatus\": 0,\n" +
                "    \"sendAirEntrustDesc\": \"\",\n" +
                "    \"sendPFeeStatus\": 0,\n" +
                "    \"sendPFeeDesc\": \"\",\n" +
                "    \"routeOperatorName\": \"CQ空运操作/李雪\",\n" +
                "    \"deliveryCenterOpName\": \"CQ空运操作/彭婷婷\",\n" +
                "    \"deliveryCenterOpId\": \"23123\",\n" +
                "    \"chargingStatus\": \"1\",\n" +
                "    \"chargingDesc\": \"\",\n" +
                "    \"agencyOverseasName\": \"\",\n" +
                "    \"payType\": \"PP\",\n" +
                "    \"airLineName\": \"台湾中华航空股份有限公司\",\n" +
                "    \"flightNo\": \"CI5998\",\n" +
                "    \"flightDate\": \"2022-11-30 00:00:00\",\n" +
                "    \"arrivedDate\": null,\n" +
                "    \"productType\": \"10305\",\n" +
                "    \"productName\": \"\",\n" +
                "    \"airCostUnitPrice\": 13,\n" +
                "    \"saleUnitPrice\": 0,\n" +
                "    \"receiverPartyNameEn\": \"SAMSUNG SDS VIETNAM CO LTD\",\n" +
                "    \"deliveryPartyNameEn\": \"KC INTERNATIONAL LOGISTICS SHANGHAI CO LTD QINGDAO BRANCH\",\n" +
                "    \"typeName1\": null,\n" +
                "    \"typeName2\": null,\n" +
                "    \"typeName3\": null,\n" +
                "    \"typeName4\": null,\n" +
                "    \"typeName5\": null,\n" +
                "    \"tmsOrderTypeName\": null,\n" +
                "    \"pickupDate\": null,\n" +
                "    \"tmsOperatorName\": null,\n" +
                "    \"tmsOperatorId\": null,\n" +
                "    \"isDispatchStowage\": null,\n" +
                "    \"dispatchStowageNo\": null,\n" +
                "    \"cmsOrderTypeName\": \"一般贸易\",\n" +
                "    \"tradeType\": \"1\",\n" +
                "    \"accountNo\": \"\",\n" +
                "    \"customsBroker\": \"0\",\n" +
                "    \"warehouseName\": null,\n" +
                "    \"wmsType\": null,\n" +
                "    \"ioType\": null,\n" +
                "    \"insuranceNo\": \"\",\n" +
                "    \"insuredName\": \"韩中国际货运（上海）有限公司青岛分公司\",\n" +
                "    \"insuredTel\": \"15066261108\",\n" +
                "    \"insuranceType\": \"3\",\n" +
                "    \"invoiceCurrency\": \"USD\",\n" +
                "    \"insuranceRatio\": \"10\",\n" +
                "    \"invoiceAmount\": 78748.8,\n" +
                "    \"insuranceAmount\": 86623.68,\n" +
                "    \"sendOrderEntrustStatus\": \"1\",\n" +
                "    \"sendOrderEntrustDesc\": \"处理成功\",\n" +
                "    \"sendFeeStatus\": \"1\",\n" +
                "    \"sendFeeDesc\": \"\",\n" +
                "    \"chargedWeight\": 195,\n" +
                "    \"units\": 1,\n" +
                "    \"grossWeight\": 132,\n" +
                "    \"volume\": 1.09,\n" +
                "    \"serviceList\": [\n" +
                "    \t\t\"status1\",\"status2\"\n" +
                "    ],\n" +
                "    \"orderTypeName\": \"出口\",\n" +
                "    \"routeAcceptanceStatus\": \"1\",\n" +
                "    \"shippingStatus\": null,\n" +
                "    \"serviceStatus\": null,\n" +
                "    \"updateStatus\": null,\n" +
                "    \"airStatus\": \"订舱：订舱出号；地面：空运代操作任务提交；舱单：舱单暂存成功；\",\n" +
                "    \"customsStatus\": \"报关1：报关提交；\",\n" +
                "    \"warehouseStatus\": null,\n" +
                "    \"insuranceStatus\": \"保险1：保险提交；\",\n" +
                "    \"transportStatus\": null,\n" +
                "    \"pautomaticBillingStatus\": 0,\n" +
                "    \"pautomaticBillingDesc\": \"\"\n" +
                "}";

        ScOrderDocType docType = JSONUtil.toBean(json, ScOrderDocType.class);
        return docType;
    }

    @Test
    public void testHighClient(){
        System.out.println("Test Result:");
    }
}
