import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class CallScenarios {

     String callRef = null;
     String data = null;
     HttpResponse<String> response = null;

     public void placeCall(String dutIP, String destIP) throws UnirestException {
         //Unirest.setTimeouts(0, 0);
         response = Unirest.post("http://10.221.26.173/api/v1/callctrl/dial")
                .header("Authorization", "Basic UG9seWNvbTo3Nzc=")
                .header("Content-Type", "application/json")
                .body("{\"data\":{\"Dest\":\"" +
                        destIP +
                        "\",\"Line\":\"1\",\"Type\":\"SIP\"}}")
                .asString();
         System.out.println("In place call");
    }

    public void answerCall(String ipAddress) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        response = Unirest.get("http://" +
                ipAddress +
                "/api/v2/webCallControl/callStatus")
                .header("Authorization", "Basic UG9seWNvbTo3Nzc=").asString();

        JSONObject obj = new JSONObject(response.getBody());
        try {
            JSONArray arr = obj.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                callRef = arr.getJSONObject(i).getString("CallHandle");
                System.out.println(callRef);
            }
            data = "{\"data\":{\"Ref\": \"" +callRef +"\"}}";
            response = Unirest.post("http://" + ipAddress + "/api/v1/callctrl/answerCall")
                    .header("Authorization", "Basic UG9seWNvbTo3Nzc=")
                    .header("Content-Type", "application/json")
                    .body(data).asString();
            if(getUIXml(ipAddress,"End Call")) {
                System.out.println("Call is answered successfully");
            }
            else {
                System.out.println("Call is not answered successfully");
                System.exit(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void holdCall(String ipAddress) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        response = Unirest.post("http://" + ipAddress + "/api/v1/callctrl/holdCall")
                .header("Authorization", "Basic UG9seWNvbTo3Nzc=")
                .header("Content-Type", "application/json")
                .body(data).asString();
        if(getUIXml(ipAddress,"Resume")) {
            System.out.println("Call is put on Hold successfully");
        }
        else {
            System.out.println("Call is not put on Hold successfully");
            System.exit(400);
        }
    }

    public void resumeCall(String ipAddress) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        response = Unirest.post("http://" + ipAddress + "/api/v1/callctrl/resumeCall")
                .header("Authorization", "Basic UG9seWNvbTo3Nzc=")
                .header("Content-Type", "application/json")
                .body(data).asString();
        if(getUIXml(ipAddress,"End Call")) {
            System.out.println("Call is Resumed successfully");
        }
        else {
            System.out.println("Call is not Resumed successfully");
            System.exit(400);
        }
    }

    public void endCall(String ipAddress) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        response = Unirest.post("http://" + ipAddress + "/api/v1/callctrl/endCall")
                .header("Authorization", "Basic UG9seWNvbTo3Nzc=")
                .header("Content-Type", "application/json")
                .body(data).asString();
        if(getUIXml(ipAddress,"New Call")) {
            System.out.println("Call is Ended successfully");
        }
        else {
            System.out.println("Call is not Ended successfully");
            System.exit(400);
        }
    }

    public boolean getUIXml(String ipAddress, String text) throws UnirestException {
        //Unirest.setTimeouts(0, 0);
        HttpResponse<String> response  = Unirest.get("http://" +
                ipAddress +
                "/api/v1/mgmt/uixml")
                .header("Authorization", "Basic UG9seWNvbTo3Nzc=").asString();
        return response.getBody().contains(text);
    }

 }
