package com.otp.OnlineTestPortal.Repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otp.OnlineTestPortal.model.Response;

public interface ResponseRepo extends JpaRepository<Response, Integer> {



	List<Response> findResponseByResponsetype(String string);

}
