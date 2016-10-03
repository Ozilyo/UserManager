package com.interfac.usermanager.util;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interfac.usermanager.user.model.User;

@Service
public class AuditRetiever {
	
//	int userId;
//	
//	@Autowired
//	EntityManager entityManager;
//	
//	AuditReader auditReader = AuditReaderFactory.get(entityManager);
//	
//	List<Number> revisions = auditReader.getRevisions(User.class, userId);
//	
//	public void retrieveAudits(int userId) {
//		for (Number n : revisions) {
//			User user = auditReader.find(User.class, userId, n);
//			System.out.printf("\t[Rev #%1$s] > %2$s\n", n, user);
//			
//		}
//	}
}
