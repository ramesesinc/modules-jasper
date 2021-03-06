package com.rameses.jasper.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.osiris2.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.util.*;
import com.rameses.common.*;
import com.rameses.rcp.constant.*;

class JasperFormReportModel extends FormReportModel {
    
    @Service("JasperEmailQueueService")
    def emailSvc;
    
    def entity;
    
    boolean allowSave = true;
    boolean allowPrint = true;
    
    public String getReportPath() {
        String rPath = invoker.properties.reportPath;
        if( rPath == null ) {
            return "";
        }
        return rPath;
    }
    
    public String getReportName() {
        String rptName = invoker.properties.reportName;
        if( rptName !=null ) {
            rptName = invoker.properties.reportName;
        }
        else if(reportData.template != null) {
            rptName = reportData.template;
        }
        else {
            throw new Exception("report template not specified");
        }
        if(!rptName.contains('/')) rptName = reportPath + rptName;
        if(!rptName.endsWith(".jasper")) rptName += ".jasper";
        return rptName;
    }    
    
    def getQuery() {
        return [objid: entity.objid];
    }
    
    def preview(def inv) {
        if( !entity ) entity = caller.entity;        
        if( inv.properties.allowPrint ) {
            def a = ExpressionResolver.getInstance().evalBoolean(inv.properties.allowPrint,[entity:entity, caller:caller]);
            allowPrint = a;
        }
        return super.preview();
    }    
    
    def defaultEmail;
    void sendEmail() {
        def m = [:];
        m.reportid = reportId;
        m.refid = query.objid;
        boolean pass = false;
        def h = { o->
            m.to = o.email;
            pass = true;
        }
        Modal.show("sys_specify_email", [email:defaultEmail, handler:h] );
        if(!pass) return null;
        emailSvc.send( m );
        MsgBox.alert("message queued for sending");
    }
    
    def headerid;
    public void afterReportData( Object data ) {
        headerid = data.header;
    }
    
    public Map getParameters() {
        //get the base path of the report
        String rname = getReportName();
        String basePath = rname.substring( 0, rname.lastIndexOf("/") );
        String headPath = basePath + "/headerinfo";
        if( headerid ) {
            if(! headerid.startsWith("/")) headerid = "/" + headerid;
            headPath = basePath + headerid;
        }
        def props = new Properties();
        def is = getClass().getClassLoader().getResourceAsStream( headPath );
        if(is!=null) {
            props.load( is );
            is.close();            
        }
        return props;
    }
    
}