package image.web;

import image.processing.ConvexHull;
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
import java.util.List;

/**
 * Created by Raihan on 23-Jan-16.
 */

@MultipartConfig
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("imageFile");
        if(filePart.getSize() > 0) {
            InputStream fileContent = filePart.getInputStream();
            Image img = ImageIO.read(fileContent);
            request.getSession().setAttribute("image", img);
        }
        request.getSession().setAttribute("color", request.getParameter("color"));
        request.getSession().setAttribute("tolerance", request.getParameter("tolerance"));

        Image sessionImage = (Image) request.getSession().getAttribute("image");

        if(sessionImage == null){
            request.setAttribute("errorMessage", "Please upload an image");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }

        ImageProcessor ipInput = new ImageProcessor(sessionImage);
        ImageProcessor ipOutput = new ImageProcessor(sessionImage);

        java.util.List<Point> lst = ipOutput.getPixelsByColor(new Color(130, 61, 30), 5f);
        //java.util.List<Point> lst = ipOutput.getEdges(0.007f);

        //lst = PointProcessor.groupPoints(lst);
        List<Point> convexHull = ConvexHull.generateConvexHull((ArrayList<Point>) lst);
        ipOutput.drawLine(convexHull, new Color(0, 255, 25));

        request.setAttribute("inputImage", ipInput.getSrcData());
        request.setAttribute("outputImage", ipOutput.getSrcData());

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
