package healthcare.doctors;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalAuth implements ContainerRequestFilter,ContainerResponseFilter   {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		System.out.println("calling "+requestContext.getUriInfo().getBaseUri());
		System.out.println("Headers " + requestContext.getHeaders());
		
		

//		List<String> authHeader =  requestContext.getHeaders().get("allow");
//		
//		if(authHeader !=null && authHeader.size() > 0) {
//			String allowString = authHeader.get(0);
//			System.out.println(allowString);
//			if(allowString.equals("http://192.168.1.100:8080/gateway/webapi")) {
//				return;
//			}
//		}
//		
//
//		Response unauthResponse  = Response.status(Response.Status.UNAUTHORIZED)
//											.entity("User Cannot Access the resource")
//											.build();
//		
//		requestContext.abortWith(unauthResponse);//not go through this filter 
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		System.out.println("calling "+requestContext.getUriInfo().getBaseUri());
		System.out.println("Headers " + requestContext.getHeaders());
		
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers","origin,X-Custom-Header,content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		
	}
	
}
