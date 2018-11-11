/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.oauth;

import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Stateless
public class GitHubOAuthBean {
    
    private final String CLIENT_ID = "cdd8aec2c2cd38c3a795"; 
    private final String CLIENTSECRET = "4ee936f12367e7016c32400c25f1fdfbc7544c83"; 
    
    public String getToken(String code){
        String url = String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", CLIENT_ID,CLIENTSECRET,code);
        
        Client client = ClientBuilder.newClient();
        String result = client.target(url).request().post(null, String.class);
        //access_token=<TOKEN>&scope=%token_type=bearer
        //det mellan access_token och &.. som vi vill ha ut
        return result.substring(13,result.indexOf("&")); 
    }
    
    public JsonObject githubAuth(String token){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.github.com/user?access_token="+token);
        return target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
    }
    
}
