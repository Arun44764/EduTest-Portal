package com.otp.OnlineTestPortal.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otp.OnlineTestPortal.model.AdminInfo;

public interface AdminInfoRepo extends JpaRepository<AdminInfo, String> {

}
