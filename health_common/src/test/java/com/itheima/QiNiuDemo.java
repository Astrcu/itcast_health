package com.itheima;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class QiNiuDemo {
    /*public static void main(String[] args) {
        //上传文件


        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "vnWXRBScT02lzkZQd6rQGe93YAzKdEV2k28lQDtd";
        String secretKey = "6fRbpWqRjdPkCXcovFoQju7lNrZhJcfYo6vGIeO3";
        //空间名称
        String bucket = "xss-health";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //文件的路径
        String localFilePath = "C:\\Users\\Thompson\\Desktop\\1.png";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        //文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }*/

//删除文件
    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

        String accessKey = "vnWXRBScT02lzkZQd6rQGe93YAzKdEV2k28lQDtd";
        String secretKey = "6fRbpWqRjdPkCXcovFoQju7lNrZhJcfYo6vGIeO3";

        String bucket = "xss-health";
        String key = "FtowRm5n-i5_f0CnneOjj6HiZVH7";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }

}
