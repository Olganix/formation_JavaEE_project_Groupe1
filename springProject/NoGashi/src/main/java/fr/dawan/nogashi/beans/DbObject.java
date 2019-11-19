package fr.dawan.nogashi.beans;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Version;

@MappedSuperclass
public abstract class DbObject implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Version
	private int version;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DbObject [id=" + id + "]";
	}
	
	//Todo clean
	/*
	@PrePersist
	public void prePersist()
	{
		System.out.println("prePersist");
	}
	
	@PostPersist
	public void postPersist()
	{
		System.out.println("postPersist");
	}
	
	@PostLoad
	public void postLoad()
	{
		System.out.println("postLoad");
		
		//ex faire un calcul sur une variable non sauvegarder (transient)
	}
	*/
}
