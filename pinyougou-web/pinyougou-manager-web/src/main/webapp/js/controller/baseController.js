/** 定义基础的控制器  */
app.controller('baseController', function($scope){


    // 定义分页指令需要的配置信息对象(JSON)
    $scope.paginationConf = {
        currentPage : 1, // 当前页码 (默认值：1)
        totalItems : 0, // 总记录数 (默认值：0)
        itemsPerPage: 10, // 页大小 (默认值: 15)
        perPageOptions : [10,15,20,25,30], // 页码下拉列表框([10, 15, 20, 30, 50])
        onChange : function(){ // 当页码发生改变时会调用的函数
            // 调用分页查询品牌方法
            $scope.reload();
        }
    };
    /** 重新加载数据方法 */
    $scope.reload = function(){
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage);
    };

    // 定义ids数组
    $scope.ids = [];

    // 为checkbox绑定点击事件封装用户选中的id
    $scope.updateSelection = function($event, id){
        // $event事件对象 (判断checkbox是否选中)
        //alert($event.target); // 获取dom元素
        if ($event.target.checked){
            // 选中了checkbox
            // 往数组中添加元素
            $scope.ids.push(id);
        }else{
            // 没有选中checkbox
            // 从数据中删除元素
            // 获取一个元素在数组中的索引号
            var idx = $scope.ids.indexOf(id);
            // 删除元素
            // 第一个参数：索引号
            // 第二个参数：删除的个数
            $scope.ids.splice(idx,1);
        }
    };

    /** 提取数组中json某个属性，返回拼接的字符串(逗号分隔) */
    $scope.jsonArr2Str = function(jsonArrStr, key){
        // jsonArrStr: json数组字符串
        // [{"id":3,"text":"三星"},{"id":2,"text":"华为"},{"id":5,"text":"OPPO"},
        // {"id":4,"text":"小米"},{"id":9,"text":"苹果"},{"id":8,"text":"魅族"},
        // {"id":6,"text":"360"},{"id":10,"text":"VIVO"},{"id":11,"text":"诺基亚"}]
        // 转化成json数组
        var jsonArr = JSON.parse(jsonArrStr);
        var res = [];
        // 迭代数组
        for (var i = 0; i < jsonArr.length; i++){
            // 获取数组元素 {"id":3,"text":"三星"}
            var json = jsonArr[i];
            // 获取text的值
            res.push(json[key]);
        }
        // join()： 把数组中元素用逗号分隔，得到字符串
        return res.join(",");
    };

});