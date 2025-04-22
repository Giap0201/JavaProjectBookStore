package utils; // Đảm bảo tệp này nằm trong gói utils

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Lớp tiện ích chứa các hàm xử lý ảnh.
 */
public class ImageUtils {

    // --- Hằng số ---
    public static final int DEFAULT_IMAGE_WIDTH = 200; // Chiều rộng ảnh mặc định
    public static final int DEFAULT_IMAGE_HEIGHT = 200; // Chiều cao ảnh mặc định
    public static final String DEFAULT_ICON_RESOURCE_PATH = "images/icon6.png"; // Đường dẫn tới icon mặc định trong resources

    // --- Các phương thức thay đổi kích thước ---

    /**
     * Thay đổi kích thước ảnh được tải từ đường dẫn tài nguyên trong classpath.
     *
     * @param resourcePath Đường dẫn tới tài nguyên ảnh (ví dụ: "images/icon.png").
     * @param width        Chiều rộng mong muốn.
     * @param height       Chiều cao mong muốn.
     * @return ImageIcon đã thay đổi kích thước, hoặc null nếu tải thất bại.
     */
    public static ImageIcon scaleImageResource(String resourcePath, int width, int height) {
        if (resourcePath == null || resourcePath.trim().isEmpty()) {
            System.err.println("Đường dẫn tài nguyên rỗng hoặc null.");
            return null;
        }

        try {
            // Sử dụng ClassLoader để lấy tài nguyên từ classpath
            java.net.URL imgUrl = ImageUtils.class.getClassLoader().getResource(resourcePath);
            if (imgUrl == null) {
                System.err.println("Không tìm thấy tài nguyên: " + resourcePath);
                imgUrl = ClassLoader.getSystemResource(resourcePath); // Thử với System ClassLoader
                if (imgUrl == null) {
                    System.err.println("Không tìm thấy tài nguyên qua System ClassLoader: " + resourcePath);
                    return null;
                }
            }
            ImageIcon icon = new ImageIcon(imgUrl);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải tài nguyên ảnh: " + resourcePath + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Thay đổi kích thước ảnh được tải từ đường dẫn tệp trên hệ thống.
     *
     * @param filePath Đường dẫn tuyệt đối hoặc tương đối tới tệp ảnh.
     * @param width    Chiều rộng mong muốn.
     * @param height   Chiều cao mong muốn.
     * @return ImageIcon đã thay đổi kích thước, hoặc null nếu tệp không tồn tại hoặc tải thất bại.
     */
    public static ImageIcon scaleImageFromFile(String filePath, int width, int height) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("Đường dẫn tệp rỗng hoặc null.");
            return null;
        }
        try {
            File imgFile = new File(filePath);
            if (!imgFile.exists() || !imgFile.isFile()) {
                System.err.println("Tệp ảnh không tồn tại hoặc không phải là tệp: " + filePath);
                return null;
            }
            ImageIcon icon = new ImageIcon(filePath);
            // Kiểm tra ảnh có được tải đúng cách không (ImageIcon có thể không báo lỗi nhưng width/height âm)
            if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
                System.err.println("Lỗi khi đọc nội dung tệp ảnh (có thể tệp bị hỏng): " + filePath);
                return null;
            }
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Lỗi khi thay đổi kích thước ảnh từ tệp: " + filePath + " - " + e.getMessage());
            return null;
        }
    }

    // --- Các phương thức hỗ trợ ---

    /**
     * Lấy đường dẫn tuyệt đối từ URL tương đối của ảnh (thường lưu trong CSDL/model).
     * Giả định đường dẫn tương đối bắt đầu từ thư mục gốc của dự án.
     *
     * @param relativeUrl Đường dẫn tương đối (ví dụ: "images/book123/cover.jpg").
     * @return Đường dẫn tuyệt đối trên hệ thống tệp, hoặc null nếu đầu vào không hợp lệ.
     */
    public static String getAbsolutePathFromRelative(String relativeUrl) {
        if (relativeUrl == null || relativeUrl.trim().isEmpty()) {
            return null;
        }
        try {
            String projectDir = System.getProperty("user.dir");
            // Thay thế dấu gạch chéo xuôi (thường trong URL/CSDL) bằng dấu phân cách của HĐH
            String osAppropriatePath = relativeUrl.replace("/", File.separator);
            return Paths.get(projectDir, osAppropriatePath).toString();
        } catch (InvalidPathException e) {
            System.err.println("Lỗi tạo đường dẫn tuyệt đối từ URL tương đối: " + relativeUrl + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Tạo một ImageIcon giữ chỗ (ví dụ: hộp màu xám có chữ) để hiển thị lỗi.
     *
     * @param width  Chiều rộng của ảnh giữ chỗ.
     * @param height Chiều cao của ảnh giữ chỗ.
     * @param text   Văn bản hiển thị trên ảnh giữ chỗ.
     * @return ImageIcon đại diện cho ảnh giữ chỗ.
     */
    public static ImageIcon createErrorPlaceholderIcon(int width, int height, String text) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Nền
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width, height);

        // Viền
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(0, 0, width - 1, height - 1);

        // Chữ
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);

        g2d.dispose();
        return new ImageIcon(image);
    }

    /**
     * Tải và thay đổi kích thước của icon mặc định.
     *
     * @param width  Chiều rộng mong muốn.
     * @param height Chiều cao mong muốn.
     * @return ImageIcon mặc định đã thay đổi kích thước, hoặc ảnh giữ chỗ nếu tải thất bại.
     */
    public static ImageIcon getDefaultScaledIcon(String path,int width, int height) {
        ImageIcon icon = scaleImageResource(path, width, height);
        if (icon == null) {
            System.err.println("Không thể tải icon mặc định, tạo ảnh giữ chỗ.");
            // Trả về ảnh giữ chỗ nếu không tải được icon mặc định
            return createErrorPlaceholderIcon(width, height, "Lỗi Icon");
        }
        return icon;
    }
}