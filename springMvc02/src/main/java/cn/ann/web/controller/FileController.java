package cn.ann.web.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

}

