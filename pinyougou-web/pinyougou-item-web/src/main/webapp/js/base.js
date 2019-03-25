/** 定义基础模块 */
var app = angular.module('pinyougou', []);

/** $sce服务写成过滤器 */
app.filter('trustHtml', function($sce){
    return function(html){
        return $sce.trustAsHtml(html);
    }
});