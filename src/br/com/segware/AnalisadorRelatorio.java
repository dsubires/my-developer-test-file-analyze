package br.com.segware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.io.*;

public class AnalisadorRelatorio implements IAnalisadorRelatorio {

	private ArrayList<Evento> eventos = new ArrayList<Evento>();

	public AnalisadorRelatorio() {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File("/home/subires/workspaces/segware/test-segware/test/br/com/segware/relatorio.csv");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] dados = linea.split(",");
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

				eventos.add(new Evento(Integer.parseInt(dados[0]), dados[1], dados[2], Tipo.valueOf(dados[3]),
						formatter.parseDateTime(dados[4]), formatter.parseDateTime(dados[5]), dados[6]));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		HashMap<String, Integer> cliEve = new HashMap<String, Integer>();
		for (Evento evento : eventos) {
			if (cliEve.get(evento.getCodCli()) == null)
				cliEve.put(evento.getCodCli(), 1);
			else
				cliEve.put(evento.getCodCli(), cliEve.get(evento.getCodCli()) + 1);

		}
		return cliEve;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		HashMap<String, Long> atTem = new HashMap<String, Long>();
		HashMap<String, Integer> atTemCount = new HashMap<String, Integer>();
		for (Evento evento : eventos) {
			if (atTem.get(evento.getCodAt()) == null) {
				atTem.put(evento.getCodAt(),
						(long) Seconds.secondsBetween(evento.getFechaInicio(), evento.getFechaFin()).getSeconds());
				atTemCount.put(evento.getCodAt(), 1);
			} else {
				atTem.put(evento.getCodAt(), atTem.get(evento.getCodAt())
						+ Seconds.secondsBetween(evento.getFechaInicio(), evento.getFechaFin()).getSeconds());
				atTemCount.put(evento.getCodAt(), atTemCount.get(evento.getCodAt()) + 1);
			}
		}

		Iterator<String> it = atTem.keySet().iterator();
		while (it.hasNext()) {
			String at = it.next();
			atTem.put(at, atTem.get(at) / atTemCount.get(at));
		}

		return atTem;

	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		HashMap<Tipo, Integer> eveCount = new HashMap<Tipo, Integer>();
		TreeMap<Integer, Tipo> countEve = new TreeMap<Integer, Tipo>(Collections.reverseOrder());

		for (Evento evento : eventos) {
			if (eveCount.get(evento.getTipoEve()) == null) {
				eveCount.put(evento.getTipoEve(), 1);
			} else {
				eveCount.put(evento.getTipoEve(), eveCount.get(evento.getTipoEve()) + 1);
			}
		}

		for (Map.Entry<Tipo, Integer> entry : eveCount.entrySet())
			countEve.put(entry.getValue(), entry.getKey());

		return countEve.values().stream().collect(Collectors.toList());

	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (Evento evento1 : eventos) {

			if (evento1.getTipoEve().toString().equals("ALARME")) {

				for (Evento evento2 : eventos) {
					if (evento1.getCodSec() < evento2.getCodSec() && evento1.getCodCli().equals(evento2.getCodCli())
							&& evento2.getTipoEve().toString().equals("DESARME")
							&& Seconds.secondsBetween(evento1.getFechaInicio(), evento2.getFechaInicio())
									.getSeconds() < 300)
						output.add(evento2.getCodSec());
					if (evento1.getCodSec() < evento2.getCodSec() && Seconds
							.secondsBetween(evento1.getFechaInicio(), evento2.getFechaInicio()).getSeconds() > 300)
						break;

				}

			}

		}

		return output;
	}
}
