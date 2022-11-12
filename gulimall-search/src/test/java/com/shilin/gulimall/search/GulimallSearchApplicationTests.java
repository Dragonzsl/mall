package com.shilin.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.shilin.gulimall.search.config.GulimallElasticSearchConf;
import lombok.Data;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class GulimallSearchApplicationTests {

	@Qualifier("esRestClient")
	@Autowired
	private RestHighLevelClient client;
	@Test
	void contextLoads() throws IOException {
//		System.out.println(client);

		IndexRequest indexRequest = new IndexRequest("users");
		indexRequest.id("1");
		User user = new User();
		user.setAge(20);
		user.setGender("男");
		user.setName("Tom");
		String jsonString = JSON.toJSONString(user);
		indexRequest.source(jsonString, XContentType.JSON);
		IndexResponse response = client.index(indexRequest, GulimallElasticSearchConf.COMMON_OPTIONS);
		System.out.println(response);
	}

	@Test
	void searchData() throws IOException {
		SearchRequest searchRequest = new SearchRequest("newbank");

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.matchQuery("address", "mill"));

		TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
		AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");

		builder.aggregation(ageAgg);
		builder.aggregation(balanceAvg);

		searchRequest.source(builder);

		SearchResponse searchResponse = client.search(searchRequest, GulimallElasticSearchConf.COMMON_OPTIONS);
//		SearchHits hits = searchResponse.getHits();
//		for (SearchHit hit : hits) {
//			String sourceAsString = hit.getSourceAsString();
//			JSON.parseObject(sourceAsString,xxx.class);
//		}


		Aggregations aggregations = searchResponse.getAggregations();
		Terms ageAgg1 = aggregations.get("ageAgg");
		Avg balanceAvg1 = aggregations.get("balanceAvg");
		List<? extends Terms.Bucket> buckets = ageAgg1.getBuckets();
		for (Terms.Bucket bucket : buckets) {
			System.out.println("年龄：" + bucket.getKeyAsString() + "--->" + bucket.getDocCount());
		}
		System.out.println("薪资平均值: " + balanceAvg1.getValueAsString());

//		System.out.println(searchResponse);
	}

	@Data
	static
	class User{
		private String name;
		private String gender;
		private Integer age;
	}

}
