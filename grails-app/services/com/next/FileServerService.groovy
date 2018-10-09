package com.next

import com.sun.image.codec.jpeg.JPEGCodec
import com.sun.image.codec.jpeg.JPEGImageEncoder
import grails.converters.JSON
import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage

@Transactional
@CompileStatic
@TypeChecked
class FileServerService
{

    static scope = "singleton"

    private static final String BOUNDARY = UUID.randomUUID().toString()
    private static final String PREFIX = "--"
    private static final String LINE_END = "\r\n"
    private static final String CONTENT_TYPE = "multipart/form-data"

    def upload(String avatar, String fileType)
    {
        URL url = new URL("http://s27.zhongjiaxin.com/fs/attachment/upload")
        String code = "bc6f36e9-9e28-4c67-874f-e77748978d20"
        def params = "file=" + URLEncoder.encode(avatar, "UTF-8") + "&fileType=" + URLEncoder.encode(fileType,
                                                                                                     "UTF-8") + "&code=" + URLEncoder.encode(code, "UTF-8")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.outputStream.withWriter { Writer writer -> writer.write params }

        def externalId = ""
        def response = connection.inputStream.withReader { Reader reader -> reader.text }
        if (response)
        {
            externalId = JSON.parse(response).getAt("externalId")
        }
        println "fileName: " + externalId
        return externalId
    }

    def upload1(File file, Map<String, String> param)
    {
        println "==================upload1 files=============================="

        String result = ""
        HttpURLConnection conn = null
        DataOutputStream dos = null
        try
        {
            // 设置请求头信息
            URL url = new URL("https://s7a.zhongjiaxin.com/attachment/upload")
            conn = (HttpURLConnection) url.openConnection()
            conn.setReadTimeout(60000)
            conn.setConnectTimeout(60000)
            conn.setDoInput(true)
            conn.setDoOutput(true)
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Charset", "utf-8")
            conn.setRequestProperty("connection", "keep-alive")
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY)

            dos = new DataOutputStream(conn.getOutputStream())

            // 参数上传
            if (param != null && param.size() > 0)
            {
                Iterator<String> it = param.keySet().iterator()
                while (it.hasNext())
                {
                    String key = it.next()
                    String value = param.get(key)

                    StringBuffer sb = new StringBuffer()
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END)
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END)
                    sb.append(value).append(LINE_END)
                    dos.write(sb.toString().getBytes())
                }
            }

            // 文件上传
            StringBuffer sb = new StringBuffer()
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END)
            sb.append("Content-Disposition:form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END)
            sb.append("Content-Type:image/pjpeg" + LINE_END)
            sb.append(LINE_END)
            dos.write(sb.toString().getBytes())
            InputStream is = new FileInputStream(file)
            byte[] bytes = new byte[is.available()]
            int len = 0
            while ((len = is.read(bytes)) != -1)
            {
                dos.write(bytes, 0, len)
            }
            is.close()
            dos.write(LINE_END.getBytes())
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes()
            dos.write(end_data)
            dos.flush()
            dos.close()

            // 返回值处理
            if (conn.getResponseCode() == 200)
            {
                def response = conn.inputStream.withReader { Reader reader -> reader.text }
                if (response)
                {
                    def responseMessage = JSON.parse(response)
                    if (responseMessage.getAt("errorCode"))
                    {
                        println "文件上传失败：" + responseMessage.getAt("errorMessage")
                    }
                    else
                    {
                        result = responseMessage.getAt("externalId")
                        println "fileName: " + result + "." + param['fileType']
                    }
                }
            }
            else
            {
                println "文件上传失败，错误码：" + conn.getResponseCode()
            }
        }
        catch (Exception e)
        {
            println "File upload failure reason: "
            println e
        }

        return result
    }

    def upload2(File file, Map<String, String> param)
    {
        println "==================upload2 files=============================="

        String result = ""
        HttpURLConnection conn = null
        DataOutputStream dos = null
        try
        {
            // 设置请求头信息
            URL url = new URL("https://s74.zhongjiaxin.com/attachment/upload")
            conn = (HttpURLConnection) url.openConnection()
            conn.setReadTimeout(60000)
            conn.setConnectTimeout(60000)
            conn.setDoInput(true)
            conn.setDoOutput(true)
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Charset", "utf-8")
            conn.setRequestProperty("connection", "keep-alive")
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY)

            dos = new DataOutputStream(conn.getOutputStream())

            // 参数上传
            if (param != null && param.size() > 0)
            {
                Iterator<String> it = param.keySet().iterator()
                while (it.hasNext())
                {
                    String key = it.next()
                    String value = param.get(key)

                    StringBuffer sb = new StringBuffer()
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END)
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END)
                    sb.append(value).append(LINE_END)
                    dos.write(sb.toString().getBytes())
                }
            }

            // 文件上传
            StringBuffer sb = new StringBuffer()
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END)
            sb.append("Content-Disposition:form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END)
            sb.append("Content-Type:image/pjpeg" + LINE_END)
            sb.append(LINE_END)
            dos.write(sb.toString().getBytes())
            InputStream is = new FileInputStream(file)
            byte[] bytes = new byte[is.available()]
            int len = 0
            while ((len = is.read(bytes)) != -1)
            {
                dos.write(bytes, 0, len)
            }
            is.close()
            dos.write(LINE_END.getBytes())
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes()
            dos.write(end_data)
            dos.flush()
            dos.close()

            // 返回值处理
            if (conn.getResponseCode() == 200)
            {
                def response = conn.inputStream.withReader { Reader reader -> reader.text }
                if (response)
                {
                    def responseMessage = JSON.parse(response)
                    if (responseMessage.getAt("errorCode"))
                    {
                        println "文件上传失败：" + responseMessage.getAt("errorMessage")
                    }
                    else
                    {
                        result = responseMessage.getAt("externalId")
                        println "fileName: " + result + "." + param['fileType']
                    }
                }
            }
            else
            {
                println "文件上传失败，错误码：" + conn.getResponseCode()
            }
        }
        catch (Exception e)
        {
            println "File upload failure reason: "
            println e
        }

        return result
    }

    def remove(String file)
    {
        String fileName = file.split('\\.')[0]
        String fileType = file.split('\\.')[-1]
        println "删除的文件名：${fileName}.${fileType}"

        URL url = new URL("http://s27.zhongjiaxin.com/fs/attachment/remove")
        String code = "bc6f36e9-9e28-4c67-874f-e77748978d20"
        def params = "fileName=" + URLEncoder.encode(fileName, "UTF-8") + "&fileType=" + URLEncoder.encode(fileType,
                                                                                                           "UTF-8") + "&code=" + URLEncoder.encode(code, "UTF-8")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.outputStream.withWriter { Writer writer -> writer.write params }

        def response = connection.inputStream.withReader { Reader reader -> reader.text }
    }

    def compress(String fileName, Integer w, Integer h)
    {
        try
        {
            File file = new File(fileName)
            Image img = ImageIO.read(file)
            Integer width = img.getWidth(null)
            Integer height = img.getHeight(null)

            if (width / height > w / h)
            {
                h = (Integer) (height * w / width)
            }
            else
            {
                w = (Integer) (width * h / height)
            }

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
            image.getGraphics().drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null)
            // image.getGraphics().drawImage(img.getScaledInstance(w, h,  Image.SCALE_AREA_AVERAGING), 0, 0, null)

            File destFile = new File(fileName)
            FileOutputStream out = new FileOutputStream(destFile)

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out)
            encoder.encode(image)

            return fileName
        }
        catch (Exception e)
        {
            println e.message
            return ""
        }

    }

    def compress1(File file, Map<String, String> param)
    {
        try
        {
            Integer w = 100
            Integer h = 100
            Image img = ImageIO.read(file)
            Integer width = img.getWidth(null)
            Integer height = img.getHeight(null)

            if (width / height > w / h)
            {
                h = (Integer) (height * w / width)
            }
            else
            {
                w = (Integer) (width * h / height)
            }

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
            image.getGraphics().drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null)
            // image.getGraphics().drawImage(img.getScaledInstance(w, h,  Image.SCALE_AREA_AVERAGING), 0, 0, null)
            // ImageIO.write(image, "JPEG", new File(distImgPath));

            // File destFile = file
            FileOutputStream out = new FileOutputStream(file)
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out)
            encoder.encode(image)
            out.close()
            def fileName = upload2(file, param)
            return fileName
        }
        catch (Exception e)
        {
            println e.message
            return ""
        }

    }

}
