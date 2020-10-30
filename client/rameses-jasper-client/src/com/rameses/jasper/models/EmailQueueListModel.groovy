package com.rameses.jasper.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.seti2.models.*;
import java.text.*;
 
/*******************************************************************************
* This class is used for Rental, Other Fees and Utilities
********************************************************************************/
public class EmailQueueListModel extends CrudListModel  {

    @Service("JasperEmailQueueService")
    def eorEmailSvc;
    
    void reactivate() {
        if(!selectedItem) throw new Exception("Please select an item");
        eorEmailSvc.reactivate( [objid: selectedItem.objid ]);
        listHandler.reload();
    }
    
    
}