package test.model.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import model.bean.Autore;
import model.bean.Evento;
import model.manager.EventoManagement;
import storage.DriverManagerConnectionPool;

public class EventoManagementTest 
{
	Autore autoreEsistente=new Autore("lauro.francesco1@gmail.com","Alpaca20#","Mario","Baldi","Font0");
	Evento eventoEsistente=new Evento("Fontata","Pagani","Parliamo di ricorsione","ricorsione e robe varie",LocalDate.parse("2020-10-12"),1,autoreEsistente);
	Evento eventoEsistente2=new Evento("Umberto","Londra","Parliamo di cos","cose e robe varie",LocalDate.parse("2020-10-25"),2,autoreEsistente);
	Evento eventoNuovo=new Evento("Umberto","Nocera","Parliamo di algoritmi","algoritmi e robe varie",LocalDate.parse("2020-10-13"),2,autoreEsistente);
	EventoManagement DAOEvento=new EventoManagement(new DriverManagerConnectionPool());
	
	
	@Test
	public void doRetrieveByKeySuccess() throws SQLException
	{
		ArrayList<String> paramentri=new ArrayList<String>();
		paramentri.add(String.valueOf(eventoEsistente.getData()));
		paramentri.add(eventoEsistente.getVia());
		paramentri.add(eventoEsistente.getCittà());
		assertNotNull(DAOEvento.doRetrieveByKey(paramentri));
	}
	@Test
	public void doRetrieveByKeyNoEvent() throws SQLException
	{
		ArrayList<String> paramentri=new ArrayList<String>();
		paramentri.add("2018-12-20");
		paramentri.add("Novia");
		paramentri.add("nonCisiamo");
		assertNull(DAOEvento.doRetrieveByKey(paramentri));
	}
	@Test
	public void doRetrieveByAllEvent() throws SQLException
	{
		assertTrue(DAOEvento.doRetrieveAll("").size()>0);
	}
	@Test
	public void doRetrieveByAllEventAutore() throws SQLException
	{
		assertTrue(DAOEvento.doRetrieveAll(autoreEsistente.getEmail()).size()>0);
	}
	@Test
	public void doRetrieveByAllNoEventAutore() throws SQLException
	{
		assertTrue(DAOEvento.doRetrieveAll("mailNonEsistente").size()==0);
	}
	@Test
	public void doSaveSucces() throws SQLException
	{
		DAOEvento.doSave(eventoNuovo);
		ArrayList<String> paramentri=new ArrayList<String>();
		paramentri.add(String.valueOf(eventoNuovo.getData()));
		paramentri.add(eventoNuovo.getVia());
		paramentri.add(eventoNuovo.getCittà());
		assertNotNull(DAOEvento.doRetrieveByKey(paramentri));
	}
	@Test
	public void doUpdateSuccess() throws SQLException
	{
		eventoEsistente.setArgomento("provaArgomento");
		DAOEvento.doUpdate(eventoEsistente);
		ArrayList<String> paramentri=new ArrayList<String>();
		paramentri.add(String.valueOf(eventoEsistente.getData()));
		paramentri.add(eventoEsistente.getVia());
		paramentri.add(eventoEsistente.getCittà());
		assertEquals(eventoEsistente.getArgomento(),DAOEvento.doRetrieveByKey(paramentri).getArgomento());
	}
	@Test
	public void doDelete() throws SQLException
	{
		assertTrue(DAOEvento.doDelete(eventoEsistente2));
	}
	@Test
	public void doDeleteFalse() throws SQLException
	{	
		Evento eventoNuovo=new Evento("Umberto","Nocera","Parliamo di algoritmi","algoritmi e robe varie",LocalDate.parse("2020-10-13"),45,autoreEsistente);
		assertFalse(DAOEvento.doDelete(eventoNuovo));
	}
}
