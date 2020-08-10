package com.restapi.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.*;
import org.springframework.stereotype.Repository;


import com.restapi.entity.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDAO {

	private EntityManager entityManager;
	
	/*
	 * @Autowired private SessionFactory sessionFactory;
	 */

	@Autowired
	public EmployeeDaoImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Employee> findAll() {
		
		/*
		 * Session session = sessionFactory.getCurrentSession(); Criteria criteria =
		 * session.createCriteria(Employee.class);
		 */
		
		Session currSession = entityManager.unwrap(Session.class);

		Query theQry = currSession.createQuery("from Employee");

		List<Employee> employees = theQry.getResultList();

		return employees;
	}

	@Override
	public Employee findById(int theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// get the employee
		Employee theEmployee = currentSession.get(Employee.class, theId);

//		Resource<Employee> Resource = new Resource<Employee>(theEmployee);
		
		// return the employee
		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// save employee
		currentSession.saveOrUpdate(theEmployee);
	}
	
	@Override
	public void deleteById(int theId) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery(
						"delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", theId);
		
		theQuery.executeUpdate();
	}

}