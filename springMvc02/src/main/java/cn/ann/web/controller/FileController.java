package cn.ann.web.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 16:36
 */
@Controller
public class FileController {

    @RequestMapping("/testFileupload")
    public String testFileupload(HttpServletRequest request, @RequestParam("upload") MultipartFile file) throws IOException {
        System.out.println("testFileupload running...");
        String path = request.getSession().getServletContext().getRealPath("/upload/");
        File upload = new File(path);
        if (!upload.exists()) {
            if (!upload.mkdirs()) {
                throw new RuntimeException("文件夹创建失败");
            }
        }
        // 文件名, 不带后缀名
        System.out.println(file.getName());
        // 文件的MIME类型
        System.out.println(file.getContentType());
        // 文件的全名, 带后缀
        System.out.println(file.getOriginalFilename());
        file.transferTo(new File(path + UUID.randomUUID() + file.getOriginalFilename()));

        return "redirect:/success.jsp";
    }

    @RequestMapping("/testFileupload2Server")
    public String testFileupload2Server(HttpServletRequest request, @RequestParam("upload") MultipartFile file) throws IOException {
        System.out.println("testFileupload2Server running...");
        String path = "http://localhost:9090/springMvc02_file/upload/";
        // 创建客户端对象
        Client client = Client.create();
        // 与图片服务器连接
        WebResource resource = client.resource(path + UUID.randomUUID() + file.getOriginalFilename());
        // 上传文件
        resource.put(file.getBytes());

        return "redirect:/success.jsp";
    }

    @RequestMapping("/getFileList")
    @ResponseBody
    public String[] getFileList(HttpServletRequest request) throws IOException {
        String realpath = request.getSession().getServletContext().getRealPath("//upload");
        File file = new File(realpath);
        String[] list = null;
        if (file.isDirectory()) {
            list = file.list();
        }

        return list;
    }

    @RequestMapping("/testFileDownload")
    public void testFileDownload(HttpServletRequest request, HttpServletResponse response, String filename) {
        InputStream fis = null;
        OutputStream out = null;
        try {
            String realpath = request.getSession().getServletContext().getRealPath("//upload");
            File file = new File(realpath, filename);

            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", file.length() + "");
            response.setContentType("application/x-msdownload");//MIME

            fis = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024];
            int read = -1;
            while ((read = fis.read(buff)) != -1) {
                out.write(buff, 0, read);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

