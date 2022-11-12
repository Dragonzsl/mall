package com.shilin.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * mybatis plus逻辑删除
 * 1、配置全局的逻辑删除规则（可省略）
 * 2、配置逻辑删除组件（3.1.1开始不需要配置）
 * 3、实体类字段加上 @TableLogic 注解
 *
 * JSR303
 * 1、给Bean添加校验注解：javax.validation.constraints，并定义自己的message提示
 * 2、开启校验功能@Valid
 * 		效果：校验错误后会有默认的响应；
 * 3、给校验的bean后紧跟一个BingingResult，就可以获取到校验的结果
 * 4、分组校验
 * 		1)、@NotBlank(message = "品牌名不能为空",groups = {AddGroup.class,UpdateGroup.class})
 * 			给校验注解标注什么情况需要进行校验
 * 		2)、@Validated({AddGroup.class})
 * 		3)、默认没有指定分组的校验注解，在分组校验情况下不生效，只会在没有标注分组的@Validated才会生效
 * 5、自定义校验
 * 		1）、编写一个自定义校验注解
 * 		2）、编写一个自定义校验器
 * 		3）、关联自定义的校验器和自定义的校验注解
 *      '@Documented
 * 		'@Constraint(validatedBy = {ListValueConstraintValidator.class【可以指定多个不同的校验器，适配不同类型的校验】})
 * 		'@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
 * 		'@Retention(RetentionPolicy.RUNTIME)
 * 		public @interface ListValue
 *
 * 整合redis
 * 		1、引入 spring-boot-starter-data-redis
 * 		2、配置redis的host等信息
 * 		3、使用springboot自动配置好的StringRedisTemplate来操作redis
 *
 * 整合redisson
 * 		1、引入依赖
 * 		<dependency>
 * 		    <groupId>org.redisson</groupId>
 * 		    <artifactId>redisson</artifactId>
 * 		    <version>3.13.6</version>
 * 		</dependency>
 * 		2、配置redisson
 * 			给容器中配置一个RedissonClient实例即可
 *
 * 整合SpringCache简化缓存开发
 * 		1、引入依赖
 * 			spring-boot-starter-cache
 * 		2、写配置
 * 			1）、自动配置了那些
 * 				CacheAutoConfiguration中导入了RedisCacheConfiguration
 * 				自动配置好了RedisCacheManager
 * 			2）、配置使用redis作为缓存
 * 					spring.cache.type=redis
 * 			3）、测试使用缓存
 *              '@Cacheable: Triggers cache population.触发将数据保存到缓存的操作
 * 				'@CacheEvict: Triggers cache eviction.触发将数据从缓存删除的操作
 * 				'@CachePut: Updates the cache without interfering with the method execution.不影响方法执行更新缓存
 * 				'@Caching: Regroups multiple cache operations to be applied on a method.组合以上多个操作
 * 				'@CacheConfig: Shares some common cache-related settings at class-level.在类级别共享缓存的相同配置
 * 				（1）、开启缓存功能 @EnableCaching
 * 				（2）、只需要使用注解就能完成缓存操作
 * 			4）、原理
 *				CacheAutoConfiguration -> RedisCacheConfiguration -> 自动配置了RedisCacheManager ->
 *				初始化所有缓存 -> 每个缓存决定使用什么配置 -> 如果RedisCacheConfiguration有就用自己的，没有就用默认配置
 *				-> 想改缓存配置，只需要给容器中放一个RedisCacheConfiguration即可 -> 就会应用到当前RedisCacheManager
 *				管理的所有缓存分区中
 *
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.shilin.gulimall.product.feign")
@MapperScan("com.shilin.gulimall.product.dao")
@EnableRedisHttpSession
public class GulimallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallProductApplication.class, args);
	}

}
