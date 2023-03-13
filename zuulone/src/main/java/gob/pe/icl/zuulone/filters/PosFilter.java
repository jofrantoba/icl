/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.zuulone.filters;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
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
public class PosFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String strStackTraceID = ctx.getResponse().getHeader("STACKTRACEID");
        StringBuffer msgLog = msgLog(ctx, strStackTraceID);
        msgLog.append(String.format("Http code status: %d\n", ctx.getResponseStatusCode()));
        if (ctx.getResponse().getHeader("MMERR0R") != null) {
            msgLog.append(String.format("MsgError: %s\n", ctx.getResponse().getHeader("MMERR0R")));
            msgLog.append(String.format("Status: %s\n", "FAILURE"));
            msgLog.append(String.format("Response: %s\n", ctx.getResponseBody()));
            log.error(msgLog.toString());
        } else {
            msgLog.append(String.format("Status: %s\n", "SUCCESS"));
            log.info(msgLog.toString());
        }
        return null;
    }

    private StringBuffer msgLog(RequestContext ctx, String strStackTraceID) {
        StringBuffer strLog = new StringBuffer();
        strLog.append("\n------ ").append("POST FILTER: FIN DE PETICION STACKTRACEID ").append(ctx.getResponse().getHeader("STACKTRACEID")).append(" ------\n");
        strLog.append(String.format("StackTraceId: %s \n", strStackTraceID));
        strLog.append(String.format("ServiceId: %s \n", ctx.get("serviceId") != null ? ctx.get("serviceId") : ""));
        strLog.append(String.format("Proxy: %s \n", ctx.get("proxy") != null ? ctx.get("proxy") : ""));
        strLog.append(String.format("Server: %s \nMetodo: %s\nPath: %s \n", ctx.getRequest().getServerName(), ctx.getRequest().getMethod(),
                ctx.getRequest().getRequestURI()));
        Enumeration< String> enume = ctx.getRequest().getHeaderNames();
        String header;
        while (enume.hasMoreElements()) {
            header = enume.nextElement();
            strLog.append(String.format("Headers: %s = %s \n", header, ctx.getRequest().getHeader(header)));
        };
        return strLog;
    }
    
    private String msgBodyLog(RequestContext ctx){
        String body="Body:\n";
        try{
        if(ctx.getRequest().getContentLength()>0){
            body=body+CharStreams.toString(ctx.getRequest().getReader());            
        }
        }catch(Exception ex){
            log.error("Error al imprimir el body:{}",ex);
        }
        return body;
    }

}
