package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bean.Allegato;

public class AllegatoTest 
{
	@Test
	public void getPercorsoFile() 
	{
		Allegato allegato=new Allegato("file.pdf",1);
		assertEquals("file.pdf",allegato.getPercorsoFile());
	}
	@Test
	public void getId()
	{
		Allegato allegato=new Allegato("file.pdf",1);
		assertEquals(1,allegato.getId());
	}
	@Test
	public void setPercorsoFile()
	{
		Allegato allegato=new Allegato("file.pdf",1);
		allegato.setPercorsoFile("nuovoFile.pdf");
		assertEquals("nuovoFile.pdf",allegato.getPercorsoFile());
	}

	@Test
	public void setId() 
	{
		Allegato allegato=new Allegato("file.pdf",1);
		allegato.setId(2);
		assertEquals(2,allegato.getId());
	}
}
