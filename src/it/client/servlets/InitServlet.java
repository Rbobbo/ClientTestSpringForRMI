package it.client.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.client.beans.FeedEncryptedClientRequestDTO;
import it.client.beans.FeedEncryptedClientResponseDTO;
import it.client.beans.FeedLoadClientResponseDTO;
import it.client.interfaces.prometeia.FeedExec;


public class InitServlet extends HttpServlet
{

	private static final long serialVersionUID = -4996134962432400502L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter out = resp.getWriter();
		try
		{
			Registry registry = LocateRegistry.getRegistry("localhost",9345); 
			FeedExec serv = (FeedExec) registry.lookup(FeedExec.nameRemote);

//			FeedLoadClientBean loadFeed = serv.execLoadFeedZip("C:\\opt\\Budget_App\\SFC\\exportFile\\budgetEngineAnag_20170918.zip");
			FeedLoadClientResponseDTO loadFeed = serv.execLoadFeedFile("C:\\opt\\Budget_App\\SFC\\exportFile\\riskEngine.dat");
			FeedLoadClientResponseDTO deserOb = serv.execDeserializeFeedLoaded();
			FeedLoadClientResponseDTO serOb = serv.execSerializeFeedLoaded();
			FeedEncryptedClientResponseDTO encryptOb = serv.execEncrypt();
			
			FeedEncryptedClientRequestDTO requestDecrypt = new FeedEncryptedClientRequestDTO();
			requestDecrypt.setPathFileToDecrypt(encryptOb.getPathFileCrypted());
			
			FeedEncryptedClientResponseDTO decryptOb = serv.execDecrypt(requestDecrypt);
			FeedLoadClientResponseDTO loadFeedDecrypted = serv.execLoadFeedFile(decryptOb.getPathFileDecrypted());
			FeedLoadClientResponseDTO deserDecritOb = serv.execDeserializeFeedLoaded();
			
			resp.setContentType("text/html");
			out.write("<br> <b>Eseguito Load del feed in:</b> "+loadFeed.getElapsedTyme()+"ms <br>"+
							"<b>Deserializzazione del feed in: </b>"+deserOb.getElapsedTyme()+"ms"+"<br>"+
							"<b>Serializzazione+Scrittura effettuata in :</b> "+serOb.getElapsedTyme()+"ms <br>"
									+ "<br>Aggiunta della criptazione<br>"+
							"<b>Serializzazione+Criptazione+Scrittura del file </b>"+encryptOb.getPathFileCrypted()+"<b> effettuato in : </b>"+encryptOb.getElapsedTyme()+"ms <br>"+
							"<b>Decrittazione nel file: </b>"+decryptOb.getPathFileCrypted()+"<b> effettuato in : </b>"+decryptOb.getElapsedTyme()+"ms  Bytes length->"+decryptOb.getByteDecripted().length+"<br>"+
							"<b>Eseguito Load del feed: </b>"+decryptOb.getPathFileDecrypted()+"<b> in:</b> "+loadFeedDecrypted.getElapsedTyme()+"ms <br>"+
							"<b>Deserializzazione del file:</b>"+decryptOb.getPathFileDecrypted()+"<b> effettuato in : </b>"+deserDecritOb.getElapsedTyme()+"ms <br>");
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
			out.write("<b>Errore </b>"+e.getMessage());
		}
		catch (NotBoundException e)
		{
			e.printStackTrace();
			out.write("<b>Errore </b>"+e.getMessage());
		}

	}

}
