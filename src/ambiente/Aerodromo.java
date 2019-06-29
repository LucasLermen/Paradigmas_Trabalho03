package ambiente;

import jade.content.Concept;
import jade.util.leap.Serializable;

public class Aerodromo implements Serializable {
		private Long _dim_aerodromo = 1000l;
		private Long _diametro_aerovia= 1200l; 
		private Long _lim_max_pista =1200l ;
		private Long _lim_area_escape= 600l ;
		private Long _lim_max_fl= 60l;


		

		public Long get_dim_aerodromo() {
			return _dim_aerodromo;
		}

		public void set_dim_aerodromo(Long _dim_aerodromo) {
			this._dim_aerodromo = _dim_aerodromo;
		}

		public Long get_diametro_aerovia() {
			return _diametro_aerovia;
		}

		public void set_diametro_aerovia(Long _diametro_aerovia) {
			this._diametro_aerovia = _diametro_aerovia;
		}

		public Long get_lim_max_pista() {
			return _lim_max_pista;
		}

		public void set_lim_max_pista(Long _lim_max_pista) {
			this._lim_max_pista = _lim_max_pista;
		}

		public Long get_lim_area_escape() {
			return _lim_area_escape;
		}

		public void set_lim_area_escape(Long _lim_area_escape) {
			this._lim_area_escape = _lim_area_escape;
		}

		public Long get_lim_max_fl() {
			return _lim_max_fl;
		}

		public void set_lim_max_fl(Long _lim_max_fl) {
			this._lim_max_fl = _lim_max_fl;
		}
		}
		
	
	