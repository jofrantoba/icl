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
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 *
 * @author jonathan.torres
 */
@Component
@Slf4j
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();     
        String strStackTraceID=UUID.randomUUID().toString();
        ctx.getResponse().addHeader("STACKTRACEID", strStackTraceID);   
        ctx.getZuulRequestHeaders().put("STACKTRACEID", strStackTraceID);
        ctx.getZuulRequestHeaders().put("ORIGREQ", ctx.getRequest().getHeader("origin"));
        StringBuffer strLog=msgHeaderLog(ctx,strStackTraceID);
        //strLog.append(msgBodyLog(ctx));
        log.info(strLog.toString());
        return null;
    }
    
    private StringBuffer msgHeaderLog(RequestContext ctx,String strStackTraceID){
        StringBuffer strLog = new StringBuffer();
        strLog.append("\n------ ").append("PRE FILTER: INICIO DE PETICION STACKTRACEID ").append(ctx.getResponse().getHeader("STACKTRACEID")).append(" ------\n");
        strLog.append(String.format("StackTraceId: %s \n",strStackTraceID));
        strLog.append(String.format("ServiceId: %s \n",ctx.get("serviceId")!=null?ctx.get("serviceId"):""));
        strLog.append(String.format("Proxy: %s \n",ctx.get("proxy")!=null?ctx.get("proxy"):""));
        strLog.append(String.format("Server: %s\nMetodo: %s\nPath: %s\n", ctx.getRequest().getServerName(), ctx.getRequest().getMethod(),
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
