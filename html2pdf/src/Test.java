import java.io.*;

public class Test {

    public static void main(String[] args) {

        // pdf 생성 경로
        final String createdPath = "." + File.separator + "pdf";

        // html string
        String htmlString = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>제목</title>\n" +
                "  <meta charset=\"utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Hello</h1>\n" +
                "<div>\n" +
                "  <p>Welcome to HTML world! 저와 함께 html을 배워봅시다.</p>\n" +
                "  <img src=\"http://www.gravatar.com/avatar/edc1de56da658ae0b919a2d2ee28e26c?s=32&d=retro\" />\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        // html 폴더에 있는 html 파일
        final String htmlName = "test.html";
        final String htmlPath = "." + File.separator + "html" + File.separator + htmlName;



        // html string 사용할 시
        boolean result = PdfUtils.makePdf(htmlString, createdPath);

        // html path 사용할 시
        // boolean result2 = PdfUtils.makePdfFromFile(htmlPath, createdPath);

        System.out.println(result);
    }
}





