package com.mtlogic;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
public class BlockchainManager {
	final Logger logger = LoggerFactory.getLogger(BlockchainManager.class);

	public BlockchainManager() {
		
	}
	
	@Path("/blockchain/invokeRPC")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response invokeRPC() throws JSONException 
	{	
		logger.info(">>>ENTERED invokeRPC()");
		
		Response response = null;
		BlockchainManagerService blockchainManagerService = null;
		int responseCode = 200;
		String responseJsonString = null;
		
		try {
			String id = null; 
			String method = null;
			List<Object> params = null;
			blockchainManagerService = new BlockchainManagerService();
			
			//hard-code test params for now until we have connectivity
			id = "1234567890";
			method = "liststreams";
			responseJsonString = blockchainManagerService.invokeRPCWithCloseableHttpClient(id, method, params);
		} catch (Exception e) {
			logger.error("Message could not be processed: " + e.getMessage());
			response = Response.status(422).entity("Message could not be processed: " + e.getMessage()).build();
		}
		
		if (response == null) {
			response = Response.status(responseCode).entity(responseJsonString).build();
		}
		
		logger.info("<<<EXITED invokeRPC()");
		return response;
	}

}
