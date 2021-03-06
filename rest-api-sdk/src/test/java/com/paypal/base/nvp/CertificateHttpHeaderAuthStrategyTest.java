package com.paypal.base.nvp;

import java.util.Map;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.paypal.base.credential.CertificateCredential;
import com.paypal.base.credential.TokenAuthorization;

public class CertificateHttpHeaderAuthStrategyTest {
	@Test
	public void generateHeaderStrategyForTokenTest() throws Exception{
		CertificateHttpHeaderAuthStrategy certificateHttpHeaderAuthStrategy= new CertificateHttpHeaderAuthStrategy("https://svcs.sandbox.paypal.com/");
		CertificateCredential certCredential = new CertificateCredential("testusername","testpassword","certkey","certpath");
		TokenAuthorization tokenAuthorization = new TokenAuthorization("accessToken","tokenSecret");
		certCredential.setThirdPartyAuthorization(tokenAuthorization);
		Map<String,String> header = certificateHttpHeaderAuthStrategy.generateHeaderStrategy(certCredential);
		
		String authHeader = (String)header.get("X-PAYPAL-AUTHORIZATION");
		String[] headers=authHeader.split(",");
		Assert.assertEquals("token=accessToken", headers[0]);
	}
	
	@Test
	public void generateHeaderStrategyWithoutTokenTest() throws Exception{
		CertificateHttpHeaderAuthStrategy certificateHttpHeaderAuthStrategy= new CertificateHttpHeaderAuthStrategy("https://svcs.sandbox.paypal.com/");
		CertificateCredential certCredential = new CertificateCredential("testusername","testpassword","certkey","certpath");
		Map<String,String> header = certificateHttpHeaderAuthStrategy.generateHeaderStrategy(certCredential);
		String username = (String)header.get("X-PAYPAL-SECURITY-USERID");
		String psw = (String)header.get("X-PAYPAL-SECURITY-PASSWORD");
		Assert.assertEquals("testusername", username);
		Assert.assertEquals("testpassword", psw);
	}
}
