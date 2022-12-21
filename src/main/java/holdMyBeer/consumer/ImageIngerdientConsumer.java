//package holdMyBeer.consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//
//public class ImageIngerdientConsumer {
//    @RabbitListener(queues = "ImageQueue")
//    public String queryImageIngerdient(String name){
//        try{
//            URL url = new URL("https://api.example.com/endpoint?param1=value1&param2=value2");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//
//            // Read the response
//            String response = "";
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response += line;
//            }
//            reader.close();
//
//            // Print the response
//            System.out.println(response);
//        }
//        catch (Exception e){
//            //return default image
//        }
//        return "test";
//    }
//}
