package ambiente;
import jade.util.leap.Serializable;

public class SistemaAeroportuario implements Serializable {
	public static class SistemaAeroportuarioData {
		private long DIM_AERODROMO;
		private long DIAMENTRO_AEROVIA;
		private long LIM_MAX_PISTA;
		private long LIM_MAX_AREA_ESCAPE;
		private long LIM_MAX_FL;

		public SistemaAeroportuarioData() {
		}

		public long getDIM_AERODROMO() {
			return DIM_AERODROMO;
		}

		public void setDIM_AERODROMO(long dIM_AERODROMO) {
			DIM_AERODROMO = dIM_AERODROMO;
		}

		public long getDIAMENTRO_AEROVIA() {
			return DIAMENTRO_AEROVIA;
		}

		public void setDIAMENTRO_AEROVIA(long dIAMENTRO_AEROVIA) {
			DIAMENTRO_AEROVIA = dIAMENTRO_AEROVIA;
		}

		public long getLIM_MAX_PISTA() {
			return LIM_MAX_PISTA;
		}

		public void setLIM_MAX_PISTA(long lIM_MAX_PISTA) {
			LIM_MAX_PISTA = lIM_MAX_PISTA;
		}

		public long getLIM_MAX_AREA_ESCAPE() {
			return LIM_MAX_AREA_ESCAPE;
		}

		public void setLIM_MAX_AREA_ESCAPE(long lIM_MAX_AREA_ESCAPE) {
			LIM_MAX_AREA_ESCAPE = lIM_MAX_AREA_ESCAPE;
		}

		public long getLIM_MAX_FL() {
			return LIM_MAX_FL;
		}

		public void setLIM_MAX_FL(long lIM_MAX_FL) {
			LIM_MAX_FL = lIM_MAX_FL;	}
	}
	
	SistemaAeroportuarioData data = new SistemaAeroportuarioData();

public SistemaAeroportuario(long dim_aerodromo, long diametro_aerovia, 
			long lim_max_pista, long lim_area_escape, long lim_max_fl) {
		this.data.setDIM_AERODROMO(dim_aerodromo);
		this.data.setDIAMENTRO_AEROVIA(diametro_aerovia);
		this.data.setLIM_MAX_PISTA(lim_max_pista);
		this.data.setLIM_MAX_AREA_ESCAPE(lim_area_escape);
		this.data.setLIM_MAX_FL(lim_max_fl);
		
	}

}
