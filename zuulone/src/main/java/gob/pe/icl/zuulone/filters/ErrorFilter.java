/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.zuulone.filters;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import gob.pe.icl.zuulone.util.EnumError;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 *
 * @author jonathan.torres
 */
@Component
@Slf4j
public class ErrorFilter extends ZuulFilter {

    private static final String SERVICEUNAVAILABLE = "Service Unavailable";
    private static final String FORMAT_RESPONSE_BODY_ERR = "{\"timestamp\":\"" + Instant.now().atZone(ZoneId.of("America/Lima")).toLocalDateTime() + "\",\"status\": %d,\"error\": \"%s\" ,\"message\": \"%s\"}";

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        Transaction transaction = ElasticApm.startTransaction();
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        transaction.captureException(throwable);
        String strStackTraceID = ctx.getResponse().getHeader("STACKTRACEID");
        ctx.getResponse().addHeader("MMERR0R", throwable.getMessage());
        StringBuffer strBuffer = msgLog(ctx, strStackTraceID);
        strBuffer.append(msgBodyLog(ctx));
        String stackTrace = printStackTrace((Exception) throwable, strStackTraceID, false);
        if (throwable instanceof ZuulException) {
            ZuulException zuulException = (ZuulException) throwable;
            //if (stackTrace.contains(ERRCONNECTREFUSA)) {
            if (EnumError.contains(stackTrace)) {
                String msgError = "Error al conectar al microservicio " + (ctx.get("serviceId") != null ? ctx.get("serviceId") : "");
                strBuffer.append(String.format("\nMsgError: %s\n", msgError));
                setCustomResponseError(FORMAT_RESPONSE_BODY_ERR, ctx, SERVICEUNAVAILABLE, msgError, 503);
            }
        }
        strBuffer.append(stackTrace);
        log.error(strBuffer.toString());
        transaction.end();
        return null;
    }    

    private void setCustomResponseError(String format, RequestContext ctx, String error, String msgError, int statusCode) {
        ctx.remove("throwable");
        ctx.setResponseBody(String.format(format, statusCode, error, msgError));
        ctx.getResponse().setContentType("application/json");
        ctx.setResponseStatusCode(statusCode);
    }

    private String printStackTrace(Exception zuulException, String strStackTraceID, boolean print) {
        StringWriter stack = new StringWriter();
        zuulException.printStackTrace(new PrintWriter(stack));
        if (print) {
            log.error(stack.toString());
        }
        return String.format("STACKTRACE: %s\n", stack.toString());
    }

    private StringBuffer msgLog(RequestContext ctx, String strStackTraceID) {
        StringBuffer strLog = new StringBuffer();
        strLog.append("\n------ ").append("ERROR FILTER: PETICION STACKTRACEID ").append(ctx.getResponse().getHeader("STACKTRACEID")).append(" ------\n");
        strLog.append(String.format("StackTraceId: %s \n", strStackTraceID));
        strLog.append(String.format("ServiceId: %s \n", ctx.get("serviceId") != null ? ctx.get("serviceId") : ""));
        strLog.append(String.format("Proxy: %s \n", ctx.get("proxy") != null ? ctx.get("proxy") : ""));
        strLog.append(String.format("Server: %s Metodo: %s \nPath: %s \n", ctx.getRequest().getServerName(), ctx.getRequest().getMethod(),
                ctx.getRequest().getRequestURI()));
        Enumeration< String> enume = ctx.getRequest().getHeaderNames();
        String header;
        while (enume.hasMoreElements()) {
            header = enume.nextElement();
            strLog.append(String.format("Headers: %s = %s \n", header, ctx.getRequest().getHeader(header)));
        };
        return strLog;
    }

    private String msgBodyLog(RequestContext ctx) {
        String body = "Body:\n";
        try {
            if (ctx.getRequest().getContentLength() > 0) {
                body = body + CharStreams.toString(ctx.getRequest().getReader());
            }
        } catch (Exception ex) {
            log.error("Error al imprimir el body:{}", ex);
        }
        return body;
    }

}
