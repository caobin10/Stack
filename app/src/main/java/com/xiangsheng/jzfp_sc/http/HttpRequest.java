package com.xiangsheng.jzfp_sc.http;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.ByteString;

/**
 * Created by Administrator on 2018/2/26.
 */

public class HttpRequest
{
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    private final Request request;

    private HttpRequest(Request request) {
        this.request = request;
    }

//    public RequestCall build(){
//        return new RequestCall().buildCall();
//    }

    public Request getRequest() {
        return request;
    }

    public Request clone() {
        return request.newBuilder().build();
    }

    private static Request buildRequest(String url, Object tag) {
        return buildRequest(url, null, tag);
    }

    private static Request buildRequest(String url, Map<String, String> headers, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        return builder.tag(tag).build();
    }

    public static Request buildRequest(String url, Map<String, String> headers, String method, MediaType contentType, Object content, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = null;
        if (content instanceof byte[]) {
            requestBody = RequestBody.create(contentType, (byte[]) content);
        } else if (content instanceof String) {
            requestBody = RequestBody.create(contentType, (String) content);
        } else if (content instanceof File) {
            requestBody = RequestBody.create(contentType, (File) content);
        } else if (content instanceof ByteString) {
            requestBody = RequestBody.create(contentType, (ByteString) content);
        }
        return buildRequest(url, headers, method != null ? method : "POST", requestBody, tag);
    }

    private static Request buildRequest(String url, Map<String, String> headers, String method, RequestBody requestBody, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        builder.method(method != null ? method : "POST", requestBody);
        return builder.tag(tag).build();
    }

    private static Request buildRequest(String url, Map<String, String> headers, String method, Map<String, String> params, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (params != null) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.method(method != null ? method : "POST", formBuilder.build());
        }
        return builder.tag(tag).build();
    }

    private static Request buildRequest(String url, Map<String, String> headers, String method, Map<String, String> params, Map<String, File> fileParams, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (params != null || fileParams != null) {
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }

            if (fileParams != null) {
                for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                    File file = entry.getValue();
                    multipartBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MEDIA_TYPE_STREAM, file));
                }
            }
            builder.method(method != null ? method : "POST", multipartBuilder.build());
        }
        return builder.tag(tag).build();
    }

    private static Request buildRequest(String url, Map<String, String> headers, String method, Map<String, String> params, Map<String, File> fileParams, Map<String, Map<String, File>> filesParams, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (params != null || fileParams != null || filesParams != null) {
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }

            if (fileParams != null) {
                for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                    File file = entry.getValue();
                    multipartBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MEDIA_TYPE_STREAM, file));
                }
            }

            if (filesParams != null) {
                for (Map.Entry<String, Map<String, File>> entry : filesParams.entrySet()) {
                    Map<String, File> files = entry.getValue();
                    for (Map.Entry<String, File> e : files.entrySet()) {
                        multipartBuilder.addFormDataPart(entry.getKey(), e.getKey(), RequestBody.create(MEDIA_TYPE_STREAM, e.getValue()));
                    }
                }
            }
            builder.method(method != null ? method : "POST", multipartBuilder.build());
        }
        return builder.tag(tag).build();
    }


    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = null;
        try {
            contentType = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (contentType == null) {
            contentType = "contentType = application/octet-stream";
        }
        return contentType;
    }


    public static class Builder {
        private String url;
        private String method;
        private MediaType mediaType;
        private Object content;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Map<String, Map<String, File>> filesParams;
        private Map<String, File> fileParams;
        private Object tag;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder content(MediaType mediaType, Object content) {
            this.mediaType = mediaType;
            this.content = content;
            return this;
        }

        public Builder addParam(String key, File file) {
            if (this.fileParams == null) {
                this.fileParams = new HashMap<>();
            }
            this.fileParams.put(key, file);
            return this;
        }

        public Builder addParam(String key, Map<String, File> files) {
            if (this.filesParams == null) {
                this.filesParams = new HashMap<>();
            }
            this.filesParams.put(key, files);
            return this;
        }

        public Builder addParam(String key, String value) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            this.params.put(key, value);
            return this;
        }

        public Builder addParams(Map<String, String> params) {
            if (params != null && !params.isEmpty()) {
                if (this.params == null) {
                    this.params = new HashMap<>();
                }
                this.params.putAll(params);
            }
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder addHeader(String name, String value) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(name, value);
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            if (headers != null && !headers.isEmpty()) {
                if (this.headers == null) {
                    this.headers = new HashMap<>();
                }
                this.headers.putAll(headers);
            }
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }


        public HttpRequest build() {
            if (content != null && (params != null || fileParams != null || fileParams != null)) {
                throw new NullPointerException("content == null");
            }
            if (content != null) {
                new HttpRequest(buildRequest(url, headers, method, mediaType, content, tag));
            } else if (fileParams != null || filesParams != null) {
                return new HttpRequest(buildRequest(url, headers, method, params, fileParams, filesParams, tag));
            } else if (params != null) {
                return new HttpRequest(buildRequest(url, headers, method, params, tag));
            }
            return new HttpRequest(buildRequest(url, headers, tag));
        }
    }
}
