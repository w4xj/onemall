package cn.iocoder.mall.productservice.service.spu;

import cn.iocoder.common.framework.exception.util.ServiceExceptionUtil;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.productservice.convert.spu.ProductSpuConvert;
import cn.iocoder.mall.productservice.dal.mysql.dataobject.spu.ProductSpuDO;
import cn.iocoder.mall.productservice.dal.mysql.mapper.spu.ProductSpuMapper;
import cn.iocoder.mall.productservice.service.spu.bo.ProductSpuBO;
import cn.iocoder.mall.productservice.service.spu.bo.ProductSpuCreateBO;
import cn.iocoder.mall.productservice.service.spu.bo.ProductSpuPageBO;
import cn.iocoder.mall.productservice.service.spu.bo.ProductSpuUpdateBO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.mall.productservice.enums.ProductErrorCodeConstants.PRODUCT_SPU_NOT_EXISTS;

/**
* 商品 SPU Service
*/
@Service
@Validated
public class ProductSpuService {

    @Autowired
    private ProductSpuMapper productSpuMapper;

    /**
    * 创建商品 SPU
    *
    * @param createBO 创建商品 SPU BO
    * @return 商品 SPU
    */
    public ProductSpuBO createProductSpu(@Valid ProductSpuCreateBO createBO) {
        // 插入到数据库
        ProductSpuDO productSpuDO = ProductSpuConvert.INSTANCE.convert(createBO);
        productSpuMapper.insert(productSpuDO);
        // 返回
        return ProductSpuConvert.INSTANCE.convert(productSpuDO);
    }

    /**
    * 更新商品 SPU
    *
    * @param updateBO 更新商品 SPU BO
    */
    public void updateProductSpu(@Valid ProductSpuUpdateBO updateBO) {
        // 校验更新的商品 SPU是否存在
        if (productSpuMapper.selectById(updateBO.getId()) == null) {
            throw ServiceExceptionUtil.exception(PRODUCT_SPU_NOT_EXISTS);
        }
        // 更新到数据库
        ProductSpuDO updateObject = ProductSpuConvert.INSTANCE.convert(updateBO);
        productSpuMapper.updateById(updateObject);
    }

    /**
    * 获得商品 SPU
    *
    * @param productSpuId 商品 SPU编号
    * @return 商品 SPU
    */
    public ProductSpuBO getProductSpu(Integer productSpuId) {
        ProductSpuDO productSpuDO = productSpuMapper.selectById(productSpuId);
        return ProductSpuConvert.INSTANCE.convert(productSpuDO);
    }

    /**
    * 获得商品 SPU列表
    *
    * @param productSpuIds 商品 SPU编号列表
    * @return 商品 SPU列表
    */
    public List<ProductSpuBO> listProductSpus(List<Integer> productSpuIds) {
        List<ProductSpuDO> productSpuDOs = productSpuMapper.selectBatchIds(productSpuIds);
        return ProductSpuConvert.INSTANCE.convertList(productSpuDOs);
    }

    /**
    * 获得商品 SPU分页
    *
    * @param pageBO 商品 SPU分页查询
    * @return 商品 SPU分页结果
    */
    public PageResult<ProductSpuBO> pageProductSpu(ProductSpuPageBO pageBO) {
        IPage<ProductSpuDO> productSpuDOPage = productSpuMapper.selectPage(pageBO);
        return ProductSpuConvert.INSTANCE.convertPage(productSpuDOPage);
    }

}
