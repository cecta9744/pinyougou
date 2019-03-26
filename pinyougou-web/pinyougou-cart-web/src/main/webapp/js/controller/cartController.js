// 购物车控制器
app.controller('cartController', function ($scope, $controller, baseService) {
    // 继承baseController
    $controller('baseController', {$scope: $scope});

    // 查询用户的购物车
    $scope.findCart = function () {
        baseService.sendGet("/cart/findCart").then(function (response) {
            // 获取响应数据
            $scope.carts = response.data;

            // 定义json对象封装统计的结果
            $scope.totalEntity = {totalNum: 0, totalMoney: 0};

            // 循环用户的购物车数组
            for (var i = 0; i < $scope.carts.length; i++) {
                // 获取数组中的元素(一个商家的购物车)
                var cart = $scope.carts[i];
                // 循环该商家的购物车数组
                for (var j = 0; j < cart.orderItems.length; j++) {
                    // 获取一个商品
                    var orderItem = cart.orderItems[j];

                    // 统计购买总数量
                    $scope.totalEntity.totalNum += orderItem.num;
                    // 统计购买总金额
                    $scope.totalEntity.totalMoney += orderItem.totalFee;

                }
            }

            /** 调用生成二维数组元素个数 */
            $scope.createArr($scope.carts);
        });
    };

    // 购买数量增减与删除
    $scope.addCart = function (itemId, num) {
        baseService.sendGet("/cart/addCart?itemId="
            + itemId + "&num=" + num).then(function (response) {
            // 获取响应数据
            if (response.data) {
                // 重新加载购物车数据
                $scope.findCart();
            }
        });
    };

    /*********************************** 用户提交的购物车 *****************************************/
    /*


    /**************************************** 定义数组结构 *****************************************/
    // /** 定义存储用户选中商品的ids */
    $scope.ids = [];
    // 定义checkbox是否选中的数组
    $scope.checkedArr = [];
    // /** 商家是否选中 */
    $scope.sellerChckArr = [];
    // /** 全选按钮：默认全不选 */
    $scope.ckAll = false;

    /****************************** 根据用户购物车生成二维数组中的一维数组的元素个数 **********************************/
    /** 根据用户购物车创建ids 的元素数组个数 */
    $scope.createArr = function (carts) {
        // 初始化数组
        $scope.ids = [];
        $scope.checkedArr = [];
        $scope.sellerChckArr = [];

        // 迭代购物车的，创建ids、checkedArr中的元素数组个数
        for (var i = 0; i < carts.length; i++) {
            // 往数组中的添加元素数组
            $scope.ids.push(new Array());
            $scope.checkedArr.push(new Array());
            $scope.sellerChckArr.push(false);
        }
    };

    /******************************** 全选全不选 **********************************/
    $scope.updateSelection = function ($event, itemId, cartIndex, index) {
        // 判断checkbox是否选中 dom
        // $event.target: dom
        if ($event.target.checked) { // 选中
            // 往数组中添加元素
            $scope.ids[cartIndex].push(itemId);
        } else { // 没有选中
            // 得到该元素在数组中的索引号
            var idx = $scope.ids[cartIndex].indexOf(itemId);
            // 删除数组元素
            $scope.ids[cartIndex].splice(idx, 1);
        }
        // 重新赋值，再次绑定checkbox
        $scope.checkedArr[cartIndex][index] = $event.target.checked;

        // 让商家全选按钮是否选中,再次绑定checkbox
        $scope.sellerChckArr[cartIndex] = $scope.ids[cartIndex].length == $scope.carts[cartIndex].orderItems.length;
        // 调用监听，确定全选框状态的方法
        $scope.checkAllUn();
    };


    /** 监听商家的选中集合，确定全选框的转状态 */
    $scope.checkAllUn = function () {
        $scope.ckAll = true;
        // 迭代商家状态的集合
        for (var i = 0; i < $scope.sellerChckArr.length; i++) {
            // 判断是否存在 不一致的Boolean值
            if (!$scope.sellerChckArr[i]) {
                $scope.ckAll = false;
            }
        }
    };

    /** 商家全选 */
    $scope.sellerCheckAll = function ($event, cartIndex) {
        // 清空用户选择的ids
        $scope.ids[cartIndex] = [];
        // 循环当前页数据数组
        for (var i = 0; i < $scope.carts[cartIndex].orderItems.length; i++) {
            var cart = $scope.carts[cartIndex].orderItems[i];
            // 初始化数组
            $scope.checkedArr[cartIndex][i] = $event.target.checked;
            // 判断是否选中
            if ($event.target.checked) {
                // {id}
                $scope.ids[cartIndex].push($scope.carts[cartIndex].orderItems[i].itemId);
            }

        }
        // // 让商家全选按钮是否选中,再次绑定checkbox
        $scope.sellerChckArr[cartIndex] = $scope.ids[cartIndex].length == $scope.carts[cartIndex].orderItems.length;
        // 调用监听，确定全选框状态的方法
        $scope.checkAllUn();

    };


    /** 列表全选 */
    $scope.checkAll = function ($event) {
        // // 清空用户选择的ids
        //$scope.ids = [];
        // 循环当前页数据数组
        for (var i = 0; i < $scope.carts.length; i++) {
            // 初始化数组
            $scope.sellerChckArr[i] = $event.target.checked;
            // 判断是否选中
            if ($event.target.checked) {
                for (var j = 0; j < $scope.carts.length; i++) {
                    $scope.sellerCheckAll($event, i);
                }
            } else {
                for (var j = 0; j < $scope.carts.length; i++) {
                    $scope.sellerCheckAll($event, i);
                }
            }
        }
        // 重新赋值，再次绑定checkbox
        $scope.checkAllUn();
    };

});