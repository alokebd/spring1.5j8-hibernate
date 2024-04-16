package com.vision.hibernate.api.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vision.hibernate.api.model.Person;

@Repository
@Transactional
public class PersonDao {

	@Autowired
	private SessionFactory factory;

	public void savePerson(Person person) {
		getSession().save(person);
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPersons() {
		return getSession().createCriteria(Person.class).list();
	}
	
	public Person getPersonById(int id) {
		Session session = getSession();
		Query query = session.createQuery("from Person where id= :id");
		query.setInteger("id", id);
		Person person = (Person)query.uniqueResult();
		//System.out.println(" name:"+person.getName());
		return person;
	}
	
	public int updatePerson(Person person) {
		// getSession().update(person);
		Session session = getSession();
		Query query = session.createQuery("update Person set name= :name where id= :id");
		
		query.setParameter("name", person.getName());
		query.setInteger("id", person.getId());
		int result = query.executeUpdate();
		System.out.println("Person Update Status="+result);
		return result;
	}
	
	public int deletePerson(int id) {
		Session session = getSession();
		Query query = session.createQuery("delete Person where id= :id");
		query.setInteger("id", id);
		int result = query.executeUpdate();
		System.out.println("Person Delete Status="+result);
		return result;
	}

	private Session getSession() {
		Session session = factory.getCurrentSession();
		if (session == null) {
			session = factory.openSession();
		}
		return session;
	}

}
