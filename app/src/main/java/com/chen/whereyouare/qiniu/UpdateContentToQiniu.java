package com.chen.whereyouare.qiniu;

import android.support.annotation.NonNull;

import com.chen.whereyouare.net.QiniuApi;
import com.chen.whereyouare.net.QiniuApiHelper;
import com.chen.whereyouare.utils.Logger;
import com.chen.whereyouare.utils.ToastUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ChenHui on 2016/11/14.
 */

public class UpdateContentToQiniu {
    private static UpdateContentToQiniu instance = new UpdateContentToQiniu();

    /**
     * 追加内容
     *
     * @param fileName
     * @param content
     */
    public static void uploadContent(String fileName, String content) {
        Logger.d("上传的文件：" + fileName + "\n内容：" + content);
        try {
            String xmlContent = content;
            File file = File.createTempFile("temp", "xml");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xmlContent);
            bw.close();

            String key = fileName;
            String token = TokenHelper.getToken(key);
            instance.uploadFile(key, token, file);
        } catch (Exception e) {
            ToastUtil.show("保存失败！");
            Logger.e(e);
        }
    }

    /**
     * 追加内容
     *
     * @param fileName
     * @param content
     */
    public static void appendContent(final String fileName, final String content) {
        QiniuApi service = QiniuApiHelper.getInstanceMyPosition();

        Call<ResponseBody> call = service.loadContent(fileName, System.currentTimeMillis() + "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Logger.d("获取信息结果：" + response.isSuccessful());
                    if (response.isSuccessful()) {
                        String result = response.body().source().readUtf8();
                        boolean isNeedAdd = true;
                        if (fileName.startsWith("list_")) {
                            if (result.indexOf(content) != -1) {
                                isNeedAdd = false;//添加文件列表，如果已经存在则不追加
                            }
                        }
                        if (isNeedAdd) {
                            uploadContent(fileName, result + "," + content);
                        }
                    } else {
                        uploadContent(fileName, content);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    uploadContent(fileName, content);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("上传失败");
                uploadContent(fileName, content);
            }
        });

    }

    private void uploadFile(String key, String token, final File file) {
        // 创建上传的service实例
        QiniuApi service = QiniuApiHelper.getInstanceUpload();

        // 最后执行异步请求操作
        Map<String, RequestBody> params = new Hashtable<>();
        params.put("key", createBody(key));
        params.put("token", createBody(token));
        params.put("file", createBody(file));
        Call<ResponseBody> call = service.post(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Logger.d("上传结果：" + response.isSuccessful());
                try {
                    file.delete();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("上传失败");
            }
        });

    }


    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    private RequestBody createBody(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private RequestBody createBody(File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }

    @NonNull
    private MultipartBody.Part preparePart(String name, File file) {
        // 为file建立RequestBody实例
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }

    @NonNull
    private MultipartBody.Part preparePart(String name, String value) {
        RequestBody body = RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), value);
        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(name, value);
    }
}
