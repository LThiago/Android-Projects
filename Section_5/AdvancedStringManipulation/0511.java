import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HelloWorld {
  public static void main(String[] args) {

    String html = "<div class=\"image\"><img src=\"http://cdn.posh24.se/images/:profile/0fddc23e241e86e1a30c49bb334d76f8a\" alt=\"Taylor Swift\"/></div>";

    Pattern p = Pattern.compile("img src=\"(.*?)\"");
    Matcher m = p.matcher(html);

    while (m.find()) {
    	System.out.println(m.group(1));
    }

    p = Pattern.compile("alt=\"(.*?)\"");
    m = p.matcher(html);

    while (m.find()) {
    	System.out.println(m.group(1));
    }


  }
}
