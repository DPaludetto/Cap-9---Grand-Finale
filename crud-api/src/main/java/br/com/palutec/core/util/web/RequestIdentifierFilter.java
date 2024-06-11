package br.com.palutec.core.util.web;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(LOWEST_PRECEDENCE + 5)
public class RequestIdentifierFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdentifierFilter.class);

    public static final String REQUEST_ID_HEADER = "X-Request-Id";
    public static final String TRANSACTION_ID_HEADER = "X-Transaction-Id";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {

        final MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

        String id = extractIdentifier(request, TRANSACTION_ID_HEADER);
        if (id.isEmpty()) {
            id = extractIdentifier(request, REQUEST_ID_HEADER);
            if (id.isEmpty()) {
                id = UUID.randomUUID().toString();
                LOGGER.debug("no request id was found, generating a random id {}", id);
            }
        }

        mutableRequest.putHeader(REQUEST_ID_HEADER, id);

        LOGGER.debug("add a new entry in the mutable header {}={}", REQUEST_ID_HEADER, id);

        filterChain.doFilter(mutableRequest, response);

    }

    private String extractIdentifier(final HttpServletRequest request, final String key) {
        final String id = request.getHeader(key);
        if (id != null && !id.isEmpty()) {
            LOGGER.info("the {} was found successfully {}={}", key, key, id);
            return id;
        }

        return "";
    }

}
