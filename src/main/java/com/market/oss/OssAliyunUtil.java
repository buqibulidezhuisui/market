package com.market.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The type Oss aliyun util.
 *
 * @author Ruizhi
 * @date 2019.01.17
 */
public class OssAliyunUtil {


    /**
     * 上传文件
     *
     * @param file the file
     * @return the string
     */
    public static String upload(MultipartFile file) {
        String url = null;
        try {
            url = upload(getKey(OssAliyunField.PREFIX, ossFileUtils.getSuffix(file)),
                    file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * 上传文件流
     *
     * @param inputStream the input stream
     * @return the string
     */
    public static String uploadImage(InputStream inputStream) {
        String url = null;
        url = upload(getKey(OssAliyunField.PREFIX, "jpg"), inputStream);
        return url;
    }


    /**
     * 上传文件基础方法
     *
     * @param accessKeyId     the access key id
     * @param accessKeySecret the access key secret
     * @param bucketName      the bucket name
     * @param endpoint        the endpoint
     * @param styleName       the style name
     * @param key             the key
     * @param inputStream     the input stream
     * @return the string
     */
    public  static String upload(String accessKeyId, String accessKeySecret, String bucketName, String endpoint, String
            styleName, String key, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        String url = null;
        OSSClient ossClient =new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 带进度条的上传
            ossClient.putObject(new PutObjectRequest(bucketName, key, inputStream));
        } catch (OSSException oe) {
            oe.printStackTrace();
            key = null;
        } catch (ClientException ce) {
            ce.printStackTrace();
            key = null;
        } catch (Exception e) {
            e.printStackTrace();
            key = null;
        } finally {
            ossClient.shutdown();
        }
        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuffer sb = new StringBuffer();
            sb.append("http://").append(bucketName).append(".").append(endpoint).append("/").append(key);
            if (StringUtils.isNotBlank(styleName)) {
                sb.append("/").append(styleName);
            }
            url = sb.toString();
        }
        return url;
    }


    /**
     * 指定文件名上传
     *
     * @param key         the key
     * @param inputStream the input stream
     * @return the string
     */
    public  static String upload(String key, InputStream inputStream) {
        return upload(OssAliyunField.ACCESSKEYID, OssAliyunField.ACCESSKEYSECRET, OssAliyunField.BUCKETNAME, OssAliyunField.ENDPOINT,
                OssAliyunField.STYLENAME, key, inputStream);
    }


    /**
     * 删除文件基础方法
     *
     * @param accessKeyId     the access key id
     * @param accessKeySecret the access key secret
     * @param bucketName      the bucket name
     * @param endpoint        the endpoint
     * @param key             the key
     */
    public  static void delete(final String accessKeyId, final String accessKeySecret, final String bucketName,
                              final String endpoint, final String key) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 删除Object
        ossClient.deleteObject(bucketName, key);
        // 关闭client
        ossClient.shutdown();
    }


    /**
     * 删除单个文件
     *
     * @param key the key
     */
    public  static void delete(String key) {
        delete(OssAliyunField.ACCESSKEYID, OssAliyunField.ACCESSKEYSECRET, OssAliyunField.BUCKETNAME, OssAliyunField.ENDPOINT, key);
    }


    /**
     * 删除多个文件基础方法
     *
     * @param accessKeyId     the access key id
     * @param accessKeySecret the access key secret
     * @param bucketName      the bucket name
     * @param endpoint        the endpoint
     * @param keys            the keys
     */
    public  static void delete(final String accessKeyId, final String accessKeySecret, final String bucketName,
                              final String endpoint, final List<String> keys) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 删除Objects
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName)
                .withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        // 关闭client
        ossClient.shutdown();
    }


    /**
     * 删除多个文件
     *
     * @param keys the keys
     */
    public  static void delete( List<String> keys) {
        delete(OssAliyunField.ACCESSKEYID, OssAliyunField.ACCESSKEYSECRET, OssAliyunField.BUCKETNAME, OssAliyunField.ENDPOINT, keys);
    }


    /**
     * 获取文件key
     *
     * @param prefix the prefix
     * @param suffix the suffix
     * @return the key
     * @author Ruizhi
     */
    public  static String getKey(final String prefix, final String suffix) {
        //生成uuid,替换 - 的目的是因为后期可能会用 - 将key进行split，然后进行分类统计
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.formatDate(new Date(), "yyyyMMdd") + "-" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "-" + path;
        }
        if (suffix != null) {
            if (suffix.startsWith(".")) {
                path = path + suffix;
            } else {
                path = path + "." + suffix;
            }
        }
        return path;
    }

}

