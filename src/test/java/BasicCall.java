import com.mashape.unirest.http.exceptions.UnirestException;

public class BasicCall {
    public static void main(String[] args) throws UnirestException, InterruptedException {
        CallScenarios cs = new CallScenarios();
        cs.placeCall("10.221.26.173", "10.221.33.14");
        Thread.sleep(5000);
        cs.answerCall("10.221.33.14");
        Thread.sleep(5000);
        Thread.sleep(5000);
        cs.holdCall("10.221.33.14");
        Thread.sleep(5000);
        cs.resumeCall("10.221.33.14");
        Thread.sleep(5000);
        cs.endCall("10.221.33.14");
        System.out.println("Test Passed");
    }
}
