package com.mtlogic;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockchainManagerService {
	final Logger logger = LoggerFactory.getLogger(BlockchainManagerService.class);
	public static final String BLOCKCHAIN_NAME = "chain1";

	public BlockchainManagerService() {
		
	}

	public String invokeRPCWithCloseableHttpClient(String id, String method, List<Object> params) throws Exception {
		//JSONObject responseJsonObj = null;
		JSONObject json = new JSONObject();
		String responseJsonString = null;
        json.put("id", id);
        json.put("chain_name", BLOCKCHAIN_NAME);
        json.put("method", method);
        if (null != params) {
            json.put("params", params);
        }
		
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                //new AuthScope("httpbin.org", 80),
        		new AuthScope("54.226.23.249", 5762),
                new UsernamePasswordCredentials("multichainrpc", "DPcChDf57z8UL4aFJYPh7Fi6xWSgRWmzWGE57uxna18Z"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            //HttpGet httpget = new HttpGet("http://httpbin.org/basic-auth/user/passwd");
            HttpPost httppost = new HttpPost("http://"+"54.226.23.249"+":"+"5762");
        	//HttpPost httppost = new HttpPost("http://"+"172.31.6.202"+":"+"5762");
            StringEntity myEntity = new StringEntity(json.toString());
            httppost.setEntity(myEntity);
            
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
                //JSONParser parser = new JSONParser();
                //responseJsonObj = (JSONObject) parser.parse(EntityUtils.toString(entity));
                responseJsonString = EntityUtils.toString(entity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
        	logger.error("Message could not be processed: " + e.getMessage());
		} finally {
            httpclient.close();
        }
        
        return responseJsonString;
    }
	
//	public JSONObject invokeRPC(String id, String method, List<Object> params) {
//        //DefaultHttpClient httpclient = new DefaultHttpClient(); -- replace with line below
//        HttpClient httpclient = HttpClientBuilder.create().build();
//        JSONObject json = new JSONObject();
//        json.put("id", id);
//        json.put("chain_name", chain_name);
//        json.put("method", method);
//        if (null != params) {
//            json.put("params", params);
//        }
//        JSONObject responseJsonObj = null;
//        try {
//            httpclient.getCredentialsProvider().setCredentials(new AuthScope(ip, port),
//                    new UsernamePasswordCredentials(uName, pwd));
//            StringEntity myEntity = new StringEntity(json.toJSONString());
//
//            HttpPost httppost = new HttpPost("http://"+ip+":"+port);
//            httppost.setEntity(myEntity);
//            
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//
//            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
//            if (entity != null) {
//                System.out.println("Response content length: " + entity.getContentLength());
//            }
//            JSONParser parser = new JSONParser();
//            responseJsonObj = (JSONObject) parser.parse(EntityUtils.toString(entity));
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (org.json.simple.parser.ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            // When HttpClient instance is no longer needed,
//            // shut down the connection manager to ensure
//            // immediate deallocation of all system resources
//            httpclient.getConnectionManager().shutdown();
//        }
//        return responseJsonObj;
//    }
	
}
