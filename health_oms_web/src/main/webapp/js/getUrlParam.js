function getUrlParam(paraName) {
    var url = document.location.toString();
    // alert(url);
    //arrObj[0] = http://localhost/pages/setmeal_detail.html
    //arrObj[1] = id=3&name=jack
    var arrObj = url.split("?");
    if (arrObj.length > 1) {
        //arrPara [0] = id=3
        //arrPara [1] = name=jack
        var arrPara = arrObj[1].split("&");
        var arr;
        for (var i = 0; i < arrPara.length; i++) {
            //i = 0
            //arr[0] = id
            //arr[1] = 3
            arr = arrPara[i].split("=");
            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}
