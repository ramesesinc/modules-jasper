package com.rameses.jasper.models;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.rameses.email.*;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.*;
import com.rameses.osiris2.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.rcp.framework.*;


public class EmailTemplateModel extends CrudFormModel {
    
    public String getConnection() {
        return caller.getConnection();
    }
    
}