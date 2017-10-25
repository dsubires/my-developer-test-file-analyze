package br.com.segware;

import org.joda.time.DateTime;

public class Evento {
	
	int codSec;
	String codCli;
	String codEve;
	Tipo tipoEve;
	DateTime fechaInicio;
	DateTime fechaFin;
	String codAt;
	
	public Evento() {
		codSec = -1;
		codCli = null;
		codEve = null;
		tipoEve = null;
		fechaInicio = null;
		fechaFin = null;
		codAt = null;
	}
	
	public Evento(int codsec, String codcli, String codeve, Tipo tipoeve, DateTime fechaini, DateTime fechafin, String codat) {
		codSec = codsec;
		codCli = codcli;
		codEve = codeve;
		tipoEve = tipoeve;
		fechaInicio = fechaini;
		fechaFin = fechafin;
		codAt = codat;
		
	}

	public int getCodSec() {
		return codSec;
	}

	public void setCodSec(int codSec) {
		this.codSec = codSec;
	}

	public String getCodCli() {
		return codCli;
	}

	public void setCodCli(String codCli) {
		this.codCli = codCli;
	}

	public String getCodEve() {
		return codEve;
	}

	public void setCodEve(String codEve) {
		this.codEve = codEve;
	}

	public Tipo getTipoEve() {
		return tipoEve;
	}

	public void setTipoEve(Tipo tipoEve) {
		this.tipoEve = tipoEve;
	}

	public DateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(DateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public DateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(DateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCodAt() {
		return codAt;
	}

	public void setCodAt(String codAt) {
		this.codAt = codAt;
	}
	
	
	
	

}
