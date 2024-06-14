package br.com.squada.core.util.web;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;



public final class MutableHttpServletRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(final HttpServletRequest request) {
        super(request);
        customHeaders = new HashMap<>();
    }

    public void putHeader(final String name, final String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(final String name) {
        final String headerValue = customHeaders.get(name);

        if (headerValue != null) {
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        final Set<String> set = new HashSet<>(customHeaders.keySet());

        final Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            final String n = e.nextElement();
            set.add(n);
        }

        return Collections.enumeration(set);
    }
}
