package froop.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SAMPLE database table.
 * 
 */
@Entity
@NamedQuery(name="Sample.findAll", query="SELECT s FROM Sample s")
public class Sample implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	public Sample() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}