package com.otp.OnlineTestPortal.Repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.otp.OnlineTestPortal.model.StudentInfo;

public interface StudentInfoRepo extends JpaRepository<StudentInfo, String>{
	List<StudentInfo> findAllByStatus(@Param("status") String string);

}
