package com.shilin.gulimall.product;

import com.shilin.gulimall.product.dao.AttrGroupDao;
import com.shilin.gulimall.product.dao.SkuSaleAttrValueDao;
import com.shilin.gulimall.product.service.BrandService;
import com.shilin.gulimall.product.service.CategoryService;
import com.shilin.gulimall.product.vo.SkuItemSaleAttrVo;
import com.shilin.gulimall.product.vo.SpuItemBaseAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
class GulimallProductApplicationTests {

    @Resource
    BrandService brandService;

    @Resource
//    private OSSClient ossClient;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Resource
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Resource
    AttrGroupDao attrGroupDao;

    @Test
    void testAttrGroupDao(){
        List<SpuItemBaseAttrVo> list = attrGroupDao.getAttrGroupWithAttrsBySpuIdAndCatalogId(4L, 225L);
        System.out.println(list);
    }

    @Test
    void testSkuSaleAttrValueDao(){
        List<SkuItemSaleAttrVo> skuItemSaleAttrVos = skuSaleAttrValueDao.selectBySpuId(4L);
        System.out.println(skuItemSaleAttrVos);
    }
    @Test
    void testRedisson(){
        System.out.println(redissonClient);
    }

    @Test
    void testRedis(){
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set("key", "value");
        System.out.println(opsForValue.get("key"));

    }

    @Test
    public void test2(){
        Long[] catalogPath = categoryService.findCatelogPath(225L);
        log.info("?????????*****" + Arrays.toString(catalogPath));
    }
    @Test
    public void test1() throws FileNotFoundException {
        /*// Endpoint????????????????????????Region???????????????????????????
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
		// ?????????AccessKey?????????API????????????????????????????????????????????????????????????????????????RAM???????????????API????????????????????????
		// ????????? https://ram.console.aliyun.com ?????????
        String accessKeyId = "ll";
        String accessKeySecret = "ll";

		// ??????OSSClient?????????
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// ??????????????????

        ossClient.putObject("gulimall-shilin", "illust_57793944_20200806_091052.png", inputStream);

		// ??????OSSClient???
        ossClient.shutdown();*/
        /*InputStream inputStream = new FileInputStream("C:\\Users\\shilin\\Desktop\\??????\\???????????????\\illust_75863098_20200806_090703.png");
        ossClient.putObject("gulimall-shilin","illust_75863098_20200806_090703.png",inputStream);*/
        System.out.println("????????????");
    }

    @Test
    void contextLoads() {
        /*BrandEntity brandEntity = new BrandEntity();
//		brandEntity.setName("??????");
//		brandService.save(brandEntity);
//		brandEntity.setBrandId(1L);
//		brandEntity.setDescript("??????1");
//		brandService.updateById(brandEntity);
        List<BrandEntity> brandEntities = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        brandEntities.forEach(System.out::println);*/
    }

}
