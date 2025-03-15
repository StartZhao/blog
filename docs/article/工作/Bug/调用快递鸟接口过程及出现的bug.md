---
tag:
 - Bug
---

# 调用快递鸟接口过程及出现的bug

## 1 始终找不到正确的请求参数

最后还是通过快递鸟的接口调试的代码进行修改，才拿到正确的请求参数。

```java
import java.util.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private Main() {
    }

    /**
     * 字符编码
     **/
    private static final String charset = "UTF-8";

    /**
     * 客户编码，快递鸟提供，注意保管，不要泄漏
     **/
    private static final String EBusinessID = "你的客户编码";
    /**
     * 客户秘钥，快递鸟提供，注意保管，不要泄漏
     **/
    private static final String appKey = "你的客户秘钥";
    /**
     * 接口请求地址（正式环境）
     **/
    private static final String prodRequestUrl = "https://api.kdniao.com/api/dist";

    public static void main(String[] args) throws Exception {
        // 为方便理解使用原始URL请求，此处案列仅供参考。真实使用可按贵司需要进行自行业务封装，或使用第三方工具包如OkHttp、Hutool工具类等
        // 组装应用级参数，详细字段说明参考接口文档
        String requestData = "{\"LogisticCode\":\"78879518832288\"}";
        String result = remoteRequest("8002", requestData);
        System.out.println("8002" + "结果：" + result + "\n");
    }

    /**
     * 远程请求
     *
     * @param requestType 接口码
     * @param requestData 应用级参数
     * @return java.lang.String
     * @author kdniao
     * @date 2024/12/20 17:14
     **/
    private static String remoteRequest(String requestType, String requestData) throws Exception {
        // 组装系统级参数
        Map<String, String> params = new HashMap<>(4);
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", requestType);
        params.put("RequestData", requestData);
        String dataSign = encrypt(requestData, appKey, charset);
        params.put("DataSign", urlEncoder(dataSign));
        params.put("DataType", "2");
        // 以form表单形式提交post请求，post请求体中包含了应用级参数和系统级参数
        return sendPost(prodRequestUrl, params);
    }

    /**
     * MD5加密
     * str 内容
     * charset 编码方式
     *
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : result) {
            int val = b & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * str 内容
     * charset 编码方式
     *
     * @throws UnsupportedEncodingException
     */
    private static String base64(String str, String charset) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(str.getBytes(charset));
    }

    @SuppressWarnings("unused")
    private static String urlEncoder(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, charset);
    }

    /**
     * Sign签名生成
     * <p>
     * 1、数据内容签名，加密方法为：把(请求内容(未编码)+ApiKey)进行MD5加密--32位小写，然后Base64编码，最后进行URL(utf-8)编码
     * <p>
     *
     * @param content  内容
     * @param keyValue ApiKey
     * @param charset  编码方式
     * @return java.lang.String dataSign签名
     * @author kdniao
     * @date 2024/12/20 15:25
     **/
    @SuppressWarnings("unused")
    private static String encrypt(String content, String keyValue, String charset) throws Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * url 发送请求的 URL
     * params 请求的参数集合
     *
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private static String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), charset);
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
//                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
//                System.out.println("param:" + param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
```

## 2 读取配置文件出现问题

**异常**：报的异常是请求错误

**解决方法**：最后通过debug发现，配置读取错误，导致最终MD5生成的签名错误。

## 3 由于为了编写代码不报错，将需要设置请求参数位置置为 null，导致一致提示接口为授权

**异常**：服务器不报错，但是响应不对。

**解决方法**：最后通过debug发现，请求头确实。

## 4 请求和响应的消息转换器不行，restTemplate 只支持简单类型的转换。

**异常**：服务器报消息转换器异常

**解决方法**：自定义消息转换器，或者请求使用Spring的MultiValueMap，响应使用String接收，之后再手动JSON转换。

## 5 json转换出现问题，一次是序列化有空属性也JSON转换了，一次是反序列化json字符串存在与转换对象不一样的属性

**异常**：服务器报JSON类型转换异常

**解决方法**：修改fastjson的配置，即ObjectMapper的配置