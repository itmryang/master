
import com.skt.SktSearchApplication;
import com.skt.pojo.Spu;
import com.skt.bo.SpuBo;
import com.skt.search.client.GoodsClient;
import com.skt.search.pojo.Goods;
import com.skt.search.reponsitory.GoodsRepository;
import com.skt.search.service.SearchService;
import org.elasticsearch.index.mapper.ParseContext;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import skt.common.pojo.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SktSearchApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsReponsitory;

    @Autowired
    private ElasticsearchRestTemplate template;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex(){
        // 创建索引库，以及映射
        this.template.indexOps(Goods.class);
         /* 如果已经创建了index,可用此方法设置mapping
         */
        IndexCoordinates indexCoordinates = IndexCoordinates.of("goods");//已创建的索引
        // 根据索引实体，获取mapping字段
        Document mapping = this.template.indexOps(indexCoordinates).createMapping(Goods.class);
        // 创建索引mapping

        this.template.indexOps(indexCoordinates).putMapping(mapping);

    }
    @Test
    public void run(){
        Integer page = 1;
        ArrayList<Goods> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        do {

            Goods goods = new Goods();
            goods.setAll("yangjian");
            goods.setId(page.longValue());
            goods.setCreateTime(new Date());
            map.put("yangjian"+page,"test");
            goods.setSpecs(map);
            list.add(goods);
            this.goodsReponsitory.saveAll(list);

            // 每次循环页码加1
           page++;
            System.out.println("goods = " + page);
        } while (true);
    }

}
