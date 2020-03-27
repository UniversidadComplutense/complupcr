package es.ucm.pcr.modelo.orm;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "centro")
public class PlacaLaboratorioCentro implements java.io.Serializable {

	// TODO PARA QUE ARRANQUE EL PROYECTO HASTA QUE EXISTA LA TABLA CORRESPONDIENTE

		private Integer id;

		@Id
		@GeneratedValue(strategy = IDENTITY)
		@Column(name = "id", unique = true, nullable = false)
		public Integer getId() {
			return this.id;
		}

		public void setId(Integer id) {
			this.id = id;
		}


	}

