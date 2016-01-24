package image.web;

import image.processing.ImageProcessor;
import image.processing.PointProcessor;
import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Raihan on 23-Jan-16.
 */

@MultipartConfig
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("imageFile");
        InputStream fileContent = filePart.getInputStream();
        Image img = ImageIO.read(fileContent);

        ImageProcessor ipInput = new ImageProcessor(img);
        ImageProcessor ipOutput = new ImageProcessor(img);

        java.util.List<Point> lst = ipOutput.getPixelsByColor(new Color(0, 0, 0));
        lst = PointProcessor.groupPoints(lst);
        request.setAttribute("inputImage", ipInput.getSrcData());
        request.setAttribute("outputImage", ipOutput.getSrcData());

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
