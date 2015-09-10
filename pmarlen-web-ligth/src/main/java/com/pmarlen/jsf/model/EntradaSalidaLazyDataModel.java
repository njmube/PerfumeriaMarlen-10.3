/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.jsf.model;

import com.pmarlen.backend.dao.DAOException;
import com.pmarlen.backend.dao.EntradaSalidaDAO;
import com.pmarlen.backend.model.quickviews.EntradaSalidaQuickView;
import com.pmarlen.backend.model.quickviews.PagerInfo;
import com.pmarlen.model.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author alfredo
 */
public class EntradaSalidaLazyDataModel extends LazyDataModel<EntradaSalidaQuickView>{
	List<EntradaSalidaQuickView> pageData;
	PagerInfo pagerInfo;
	
	private int tipoMov;
	private int sucursalId;
	boolean active;

	public EntradaSalidaLazyDataModel(int tipoMov, int sucursalId, boolean active) {
		this.tipoMov = tipoMov;
		this.sucursalId = sucursalId;
		this.active = active;
		this.pagerInfo = new PagerInfo(0, 25, "ES.ID", PagerInfo.DESCENDING, null, 0);
		try {
			this.pageData = EntradaSalidaDAO.getInstance().findAllActiveByPage(this.tipoMov,this.sucursalId, this.active,pagerInfo);
			super.setRowCount(pagerInfo.getTotalRowCount());
		}catch(DAOException de){
			this.pageData = null;
		}
    }
     
    @Override
    public EntradaSalidaQuickView getRowData(String rowKey) {
        for(EntradaSalidaQuickView es : pageData) {
            if(es.getId().equals(rowKey)) {
                return es;
			}
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(EntradaSalidaQuickView es) {
        return es.getId();
    }
		
 
    @Override
    public List<EntradaSalidaQuickView> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        try {
			this.pagerInfo.setFilters(filters);
			this.pagerInfo.setFirst(first);
			this.pagerInfo.setPageSize(pageSize);
			if(sortField!=null){
				this.pagerInfo.setSortField(sortField);
				this.pagerInfo.setSortOrder(sortOrder.equals(SortOrder.ASCENDING )?PagerInfo.ASCENDIG:
											sortOrder.equals(SortOrder.DESCENDING)?PagerInfo.DESCENDING:
																			0);
			} else {
				this.pagerInfo.setSortField("ES.ID");
				this.pagerInfo.setSortOrder(PagerInfo.DESCENDING);
			}
			super.setPageSize(pageSize);
			this.pageData = EntradaSalidaDAO.getInstance().findAllActiveByPage(this.tipoMov,this.sucursalId, this.active,pagerInfo);
			super.setRowCount(pagerInfo.getTotalRowCount());
		}catch(DAOException de){
			this.pageData = null;
		}
		return this.pageData;
	}
 	
}
