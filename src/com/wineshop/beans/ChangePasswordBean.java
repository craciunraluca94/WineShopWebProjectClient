package com.wineshop.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.wineshop.dao.PlatformUserDAORemote;
import com.wineshop.dto.ChangePasswordDTO;
import com.wineshop.dto.PlatformUserDTO;
import com.wineshop.exceptions.ChangePasswordException;

@ManagedBean
@RequestScoped
public class ChangePasswordBean {

	static final Logger LOGGER = Logger.getLogger(ChangePasswordBean.class.getName());

	ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();

	@EJB
	PlatformUserDAORemote userDAORemote;

	PlatformUserDTO userDTO;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext.getExternalContext().getFlash().get("user") != null) {
			userDTO = (PlatformUserDTO) facesContext.getExternalContext().getFlash().get("user");
		}
	}

	public String changePassword() {

		LOGGER.log(Level.INFO, this.getClass().getName() + " Changing password: " + changePasswordDTO.toString());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			userDAORemote.updatePassword(changePasswordDTO);
			facesContext.addMessage("changePassForm",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Password changed successfully!", null));
			return "success";
		} catch (ChangePasswordException e) {
			facesContext.addMessage("changePassForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}
	}

	public ChangePasswordDTO getChangePasswordDTO() {
		return changePasswordDTO;
	}

	public void setChangePasswordDTO(ChangePasswordDTO changePasswordDTO) {
		this.changePasswordDTO = changePasswordDTO;
	}

	public PlatformUserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(PlatformUserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
