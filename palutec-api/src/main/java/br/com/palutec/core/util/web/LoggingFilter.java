package br.com.palutec.core.util.web;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.net.InetAddress;
import java.net.URL;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(LOWEST_PRECEDENCE + 50)
public class LoggingFilter extends OncePerRequestFilter {

	private static final String FORWARDED_HEADER = "X-Forwarded-For";

    public enum RequestInfo{
    	REQUEST_ID("requestID", false),
    	REMOTE_REQUEST_IP("requestIP", false),
    	REQUESTED_URL("requestedURL", false),
    	LOCAL_HOSTNAME("hostname", true),
    	LOCAL_USER("username", true),
    	LOCAL_IP("localIP", true);
    	
    	public final String id;
    	public final boolean immutable;
    	RequestInfo(String id, boolean immutable){
    		this.id = id;
    		this.immutable = immutable;
    	}
    	
    }

    static {
    	try {
    		MDC.put(RequestInfo.LOCAL_HOSTNAME.id, InetAddress.getLocalHost().getHostName());
    		MDC.put(RequestInfo.LOCAL_IP.id, InetAddress.getLocalHost().getHostAddress());
    		MDC.put(RequestInfo.LOCAL_USER.id, System.getProperty("user.name"));
    	}catch(Exception e) {
    		System.err.println("Error on get host network information: "+e.getMessage());
    	}
    }


    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        try {
            final String requestId = request.getHeader(RequestIdentifierFilter.REQUEST_ID_HEADER);
            final String clientIP = extractClientIP(request);
            final URL requestedUrl = new URL(request.getRequestURL().toString());
            
            MDC.put(RequestInfo.REMOTE_REQUEST_IP.id, clientIP);
            RequestThreadContextInfo.getContext().setCallerIP(clientIP);

            MDC.put(RequestInfo.REQUEST_ID.id, requestId);
            RequestThreadContextInfo.getContext().setRequestId(requestId);
            
            MDC.put(RequestInfo.REQUESTED_URL.id, requestedUrl.toString());
            RequestThreadContextInfo.getContext().setRequestedUrl(requestedUrl);
            

            chain.doFilter(request, response);
        } finally {
        	for(RequestInfo info : RequestInfo.values()) {
        		if(!info.immutable) {
        			MDC.remove(info.id);
        		}
        	}
        }
    }

    public String extractClientIP(final HttpServletRequest request) {
        final String clientIP;
        if (request.getHeader(FORWARDED_HEADER) != null) {
            clientIP = request.getHeader(FORWARDED_HEADER).split(",")[0];
        } else {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}
