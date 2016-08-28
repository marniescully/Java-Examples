
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class EchoString  {
	public String echoHandler(String str, Context context){
		LambdaLogger logger = context.getLogger();
		logger.log("received : " + str);
		return str;
	}
}
