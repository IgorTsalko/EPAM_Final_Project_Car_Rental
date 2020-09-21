package by.epamtc.tsalko.controller;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(DEFAULT_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
