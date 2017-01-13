package com.duoec.cn.core.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by ycoe on 16/5/13.
 */
public class BufferedWrapperResponse implements HttpServletResponse {
    private static final Logger logger = LoggerFactory.getLogger(BufferedWrapperResponse.class);
    HttpServletResponse original;

    PrintWriterProxy proxyWriter;

    public BufferedWrapperResponse(HttpServletResponse response) {
        this.original = response;
        try {
            proxyWriter = new PrintWriterProxy(this.original.getWriter());
        } catch (IOException e) {
            logger.error("设置ProxyWriter失败!", e);
        }

        //getWriter()时,会变更write状态,所以此处需要做特殊处理
        hackResponseOutputState();
    }

    public void setEmptyResponse(){
        this.original = new EmptyResponse();
        setStatus(this.original.getStatus());
        proxyWriter.reset();
        try {
            proxyWriter = new PrintWriterProxy(this.original.getWriter());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void hackResponseOutputState() {
        try {
            Field field = original.getClass().getDeclaredField("_outputState");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, original, 0);
        } catch (NoSuchFieldException e) {
            logger.error("设置Response._outputState=0失败!!", e);
        }
    }

    public String getContent() {
        if(proxyWriter == null){
            return null;
        }
        return proxyWriter.getContent();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (proxyWriter != null) {
            return proxyWriter;
        }
        return this.original.getWriter();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.original.getOutputStream();
    }

    @Override
    public String getCharacterEncoding() {
        return this.original.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return this.original.getContentType();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        this.original.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength(int len) {
        this.original.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long len) {
        this.original.setContentLengthLong(len);
    }

    @Override
    public void setContentType(String type) {
        this.original.setContentType(type);
    }

    @Override
    public void setBufferSize(int size) {
        this.original.setBufferSize(size);
    }

    @Override
    public int getBufferSize() {
        return this.original.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        this.original.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        this.original.resetBuffer();
        if (proxyWriter != null) {
            proxyWriter.reset();
        }
    }

    @Override
    public boolean isCommitted() {
        return this.original.isCommitted();
    }

    @Override
    public void reset() {
        this.original.reset();
        if (proxyWriter != null) {
            proxyWriter.reset();
        }
    }

    @Override
    public void setLocale(Locale loc) {
        this.original.setLocale(loc);
    }

    @Override
    public Locale getLocale() {
        return this.original.getLocale();
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.original.addCookie(cookie);
    }

    @Override
    public boolean containsHeader(String name) {
        return this.original.containsHeader(name);
    }

    @Override
    public String encodeURL(String url) {
        return this.original.encodeURL(url);
    }

    @Override
    public String encodeRedirectURL(String url) {
        return this.original.encodeRedirectURL(url);
    }

    @Override
    public String encodeUrl(String url) {
        return this.original.encodeUrl(url);
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return this.original.encodeRedirectUrl(url);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.original.sendError(sc, msg);
    }

    @Override
    public void sendError(int sc) throws IOException {
        this.original.sendError(sc);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        this.original.sendRedirect(location);
    }

    @Override
    public void setDateHeader(String name, long date) {
        this.original.setDateHeader(name, date);
    }

    @Override
    public void addDateHeader(String name, long date) {
        this.original.addDateHeader(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
        this.original.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        this.original.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        this.original.setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        this.original.addIntHeader(name, value);
    }

    @Override
    public void setStatus(int sc) {
        this.original.setStatus(sc);
    }

    @Override
    public void setStatus(int sc, String sm) {
        this.original.setStatus(sc, sm);
    }

    @Override
    public int getStatus() {
        return this.original.getStatus();
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }
}
