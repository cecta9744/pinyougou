// 定义控制器
app.controller('brandController', function ($scope, $controller, baseService) {

    // brandController中的$scope 需要 继承到baseController的$scope中的方法
    $controller("baseController", {$scope : $scope});

    /**  分页查询品牌 */
    $scope.search = function(page, rows){
        // 查询条件
        //alert(JSON.stringify($scope.searchEntity));
        // 发送异步请求
        baseService.findByPage("/brand/findByPage", page, rows,
            $scope.searchEntity).then(function(response){
            // 获取响应数据 response.data: 后台响应数据
            // 分页指令：totalItems 记录数 100
            // 分页数据：List<Brand> 10条件数据 [{},{}]
            // {total : 100, rows : [{},{},{}]}
            // 获取分页数据
            $scope.dataList = response.data.rows;
            // 更新分页指令的总记录数
            $scope.paginationConf.totalItems = response.data.total;
        });
    };

    // 添加或修改品牌
    $scope.saveOrUpdate = function(){
        var url = "save"; // 添加
        // 判断品牌id
        if ($scope.entity.id){ // 修改
            url = "update";
        }
        baseService.sendPost("/brand/" + url, $scope.entity)
            .then(function(response){
            // 获取响应数据 true|false
            if (response.data){
                // 重新加载数据
                $scope.reload();
            }else{
                alert("操作失败！");
            }
        });
    };

    // 修改按钮
    $scope.show = function(entity){
        // 把json对象转化成json字符串
        var jsonStr = JSON.stringify(entity);
        // 把json字符串解析成新的json对象
        $scope.entity = JSON.parse(jsonStr);
    };
    // 删除品牌
    $scope.delete = function(){
        // 判断用户是否选中了行
        if ($scope.ids.length > 0){
            if (confirm("您确定要删除吗？")){
                // 发送异步请求
                baseService.deleteById("/brand/delete", $scope.ids)
                    .then(function(response){
                    // 获取响应数据 true| false
                    if (response.data){
                        // 重新加载数据
                        $scope.reload();
                        // 清空ids数组
                        $scope.ids = [];
                    }else{
                        alert("删除失败！");
                    }
                });
            }
        }else{
            alert("亲，请选择要删除的品牌！");
        }
    };

});