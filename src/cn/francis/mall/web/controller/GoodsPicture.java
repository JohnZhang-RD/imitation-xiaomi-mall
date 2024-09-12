package cn.francis.mall.web.controller;

import cn.francis.mall.utils.FileUtils;
import cn.francis.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Name: GoodsPicture
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/10 - 20:44
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */


@WebServlet("/goodspicture")
public class GoodsPicture extends BaseServlet {

    // goodspicture?method=getPicture&pic=${goods.picture}
    public String getPicture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pic = request.getParameter("pic");
        if (StringUtils.isEmpty(pic)) {
            request.setAttribute("msg", "图片名称为空");
            return "/message.jsp";
        }

        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            String basePath = this.getServletContext().getRealPath("WEB-INF/goods/covers");
            // 切分
            String filename = pic.substring(pic.indexOf("_") + 1);
//        System.out.println(filename);
            String realPath = FileUtils.makePath(basePath, filename);
            String path = realPath + File.separator + pic;
//        System.out.println(path);
            File file = new File(path);

//        if (file == null) {
//            request.setAttribute("msg", "图片不存在");
//            return "/message.jsp";
//        }
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024 * 8];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            request.setAttribute("msg", e.getMessage());
            return "/message.jsp";
        } finally {
            outputStream.close();
            fileInputStream.close();
        }
        return null;
    }

}