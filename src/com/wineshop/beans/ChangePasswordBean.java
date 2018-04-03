package com.wineshop.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.wineshop.dao.PlatformUserDAORemote;
import com.wineshop.dto.ChangePasswordDTO;
import com.wineshop.exceptions.ChangePasswordException;

@ManagedBean
@RequestScoped
public class ChangePasswordBean {

	ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();

	@EJB
	PlatformUserDAORemote userDAORemote;

	public ChangePasswordDTO getChangePasswordDTO() {
		return changePasswordDTO;
	}

	public void setChangePasswordDTO(ChangePasswordDTO changePasswordDTO) {
		this.changePasswordDTO = changePasswordDTO;
	}

	public String changePassword() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			userDAORemote.updatePassword(changePasswordDTO);
		} catch (ChangePasswordException e) {
			facesContext.addMessage("changePassForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}
		facesContext.addMessage("changePassForm",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", null));
		return "success";
	}
}
