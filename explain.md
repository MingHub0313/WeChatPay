```
GET /sell~~/buyer/product/list~~
```
无参

返参
```
{
    "code": 0,
    "msg": "成功",
    "date": [
        {
            "name": "美食天地",
            "type": 2,
            "foods": [
                {
                    "id": "1234510",
                    "name": "拌面",
                    "price": 4.5,
                    "description": "美味面食",
                    "icon": "http://www.面条"
                },
                {
                    "id": "123457",
                    "name": "皮蛋粥",
                    "price": 9.9,
                    "description": "营养很好",
                    "icon": "http://www.皮蛋粥.jpg"
                }
            ]
        },
        {
            "name": "女生最爱",
            "type": 3,
            "foods": [
                {
                    "id": "123456",
                    "name": "薯条",
                    "price": 14.9,
                    "description": "很美味",
                    "icon": "http://www.薯条.jpg"
                },
                {
                    "id": "123458",
                    "name": "鸡蛋",
                    "price": 2.5,
                    "description": "正宗土鸡蛋",
                    "icon": "http://www.鸡蛋.jpg"
                },
                {
                    "id": "123459",
                    "name": "油条",
                    "price": 3,
                    "description": "油而不腻",
                    "icon": "http://www.油条"
                }
            ]
        }
    ]
}
```
说明

```
*先查询所有的上架商品
    findUpAll()
* 根据商品的信息获取对应的商品类目Id的集合
    productInfoList.stream()
        .map(e -> e.getCategoryType())
        .collect(Collectors.toList());
* 根据类目Id查询类目对象集合
    findByCategoryTypeIn(categoryTypeList);
* 封装数据
    注意不要在for循环中查询数据库
        创建返回的数据对象 List<ProductVo> productVOList
        1.创建返回的模型对象 ProductVo (categoryName、categoryType、productInfoVOList)
        2.循环类目集合为类目名称和类目Id赋值
        3.创建商品的Vo对象[List<ProductInfoVO>](productId、productName、productPrice、productDescription、productIcon)
        4.循环 List<ProductInfo> 第一步查询的商品信息
        5.商品的类目Id与第二步查询的类目集合比较
        6.如果是使用BeanUtils方法  取出4中的对象 赋值给 new 的新对象 ProductInfoVO
        7.将赋值的对象 add 到 3中创建的对象
        8.在2循环内 4循环外 ProductVo 的 setProductInfoVOList 赋值 (3中创建的对象)
        9.productVOList.add(productVo);
* 将数据封装到统一返回的方法中
    ResultVoUtil类 的 success方法
```
```
POST /sell~~//buyer/order/create~~
```
参数
```
name: "张三"
phone: "18868862211"
address: "浙江"
openid: "ew3euwhd7sjw9diwkq" //用户的微信openid
items: [{
    productId: "1423113435324",
    productQuantity: 2 //购买数量
}]
```
返参
```
{
    "code": 0,
    "msg": "成功",
    "date": {
        "orderId": "1559812231863854373"
    }
}
```
说明
```
* 判断(基本参数) 前台传参是否有误 使用 BindingResult对象的 hasErrors() 方法
* 判断(购物车参数) 是否有误 [重点]
* 通过工具方法 [OrderForm2OrderDTOConverter] 将前台传递的参数放入 OrderDTO对象中
* 将构建的参数 通过 service层 访问数据库
    一、
        1.调用 create(OrderDTO orderDTO)
        2.初始化 订单总价  随机生成 orderId
        3.获取参数中的 商品信息集合
        4.循环遍历 集合 根据 商品 productId 查询商品详情[不可能由前台传递某些重要的数据]  然后判空
        5.计算订单总价格 orderAmount
        6.封装订单详情的数据 随机生成详情 detailId、orderId
        7.通过 BeanUtils 方法copy 对象 productInfo-->orderDetail
        8.订单详情入库save(orderDetail)
    二、
        1.给 订单属性赋值 orderId
        2.通过 BeanUtils 方法copy 对象 orderDTO-->orderMaster
        3.单个属性赋值 orderAmount、orderStatus、payStatus
    三、
        1.去减库存量 使用stream() 方法 获取购物车对象集合
        2.调用 decreaseStock() 参数 List<CartDTO> cartDTOList
        3.循环遍历购物车  根据 商品 productId 查询商品信息
        4.剩余库存量=查询库存量-购买量[这里要使用 redis 锁机制 防止超卖]
        5.将剩余库存量存入商品表
* 从返回的对象中获取 orderId 当作返回给前台的参数
```
```
GET /sell~~//buyer/order/list~~
```
参数
```
openid :用户Id
page   :当前页数
size   :每页显示记录数
```

返参
```
{
    "code": 0,
    "msg": "成功",
    "date": [
        {
            "orderId": "1559805683894683431",
            "buyerName": "张三",
            "buyerPhone": "18868822111",
            "buyerAddress": "浙江",
            "buyerOpenid": "ew3euwhd7sjw9diwkq",
            "orderAmount": 19.8,
            "orderStatus": 0,
            "payStatus": 0,
            "createTime": 1559805752,
            "updateTime": 1559805752
        },
        {
            "orderId": "1559812231863854373",
            "buyerName": "哈哈",
            "buyerPhone": "19615681347",
            "buyerAddress": "安徽",
            "buyerOpenid": "ew3euwhd7sjw9diwkq",
            "orderAmount": 9,
            "orderStatus": 0,
            "payStatus": 0,
            "createTime": 1559812285,
            "updateTime": 1559812285
        }
    ]
}
```
说明
```
* 判断 openid 是否为空
* 封装分页参数 new PageRequest(page, size)
* 访问 Service 层
    一、
        1.调用jpa方法访问数据库
        2.调用工具类将查询到的数据转换为 OrderDTO
        3.使用 PageImpl<>() 返回 因为 PageImpl实现了Page <>
* 将返回的 Page<OrderDTO> 中的 内容(getContent) 放入统一的返回方法中
```

```
GET /sell~~//buyer/order/detail~~
```
参数
```
openid  : 用户的openid
orderId : 订单Id
```

返参
```
SUCCESS
{
    "code": 0,
    "msg": "成功",
    "date": {
        "orderId": "1559805683894683431",
        "buyerName": "张三",
        "buyerPhone": "18868822111",
        "buyerAddress": "浙江",
        "buyerOpenid": "ew3euwhd7sjw9diwkq",
        "orderAmount": 19.8,
        "orderStatus": 0,
        "payStatus": 0,
        "createTime": 1559805752,
        "updateTime": 1559805752,
        "orderDetailList": [
            {
                "detailId": "1559805696631658569",
                "orderId": "1559805683894683431",
                "productId": "123457",
                "productName": "皮蛋粥",
                "productPrice": 9.9,
                "productQuantity": 2,
                "productIcon": "http://www.皮蛋粥.jpg",
                "createTime": 1559805752000,
                "updateTime": 1559805752000
            }
        ]
    }
}
FAIL
{
    "timestamp": 1560132282531,
    "status": 500,
    "error": "Internal Server Error",
    "exception": "com.zmm.sell.exception.SellException",
    "message": "该订单不属于当前用户",
    "path": "/sell/buyer/order/detail"
}
```
说明
```
不安全的做法:调用jpa的 findOne(orderId)方法
安全做法:
* 调用Service的方法
* Service层做出校验 判断查询的订单是否属于当前用户
* 如果是才显示,否则不显示
```
```
POST /sell~~//buyer/order/cancel~~
```
参数
```
openid  : 用户的openid
orderId : 订单Id
```
返参
```
{
    "code": 0,
    "msg": "成功",
    "data": []
}
```
说明
```
不安全的做法:直接调用service的 cancel(orderDTO)方法
安全做法:
* 在service中首先校验(判断)该订单是否属于当前用户
    如果不是:则抛异常
    如果是  :则调用service的 cancel(orderDTO) 方法
* cancel说明
    1.判断当前订单的状态是否是新订单 如果不是则抛异常
    2.如果是新订单 修改状态 属性赋值 [BeanUtils.copyProperties(orderDTO, orderMaster)] save(orderMaster)
    3.判断是否更新成功 如果更新失败抛异常
    4.如果更新成功返还库存 
    5.判断订单详情中是否存在该商品 如果无 则抛异常
    6.如果存在 从订单中获取购物车的数据 调用增库存的方法 increaseStock(cartDTOList)
    7.如果支付 需要有退款操作
* increaseStock 说明
    1.循环遍历购物车数据
    2.根据商品Id 查询商品数据
    3.如果为空 则抛异常 商品不存在
    4.如果存在原库存加上购买数量 [此处还需要使用redis 锁 防止超卖 ]
    5.更新数据库
```
